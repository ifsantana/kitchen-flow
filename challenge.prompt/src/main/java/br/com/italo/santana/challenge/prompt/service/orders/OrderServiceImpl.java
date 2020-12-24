package br.com.italo.santana.challenge.prompt.service.orders;

import br.com.italo.santana.challenge.prompt.config.AppProperties;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.factories.NamedForkJoinWorkerThreadFactory;
import br.com.italo.santana.challenge.prompt.interfaces.kitchens.KitchenService;
import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderRepository;
import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderService;
import br.com.italo.santana.challenge.prompt.interfaces.shelves.ShelfService;
import br.com.italo.santana.challenge.prompt.service.kitchen.KitchenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * Service responsible for process the orders, controlling the flow according to the application settings.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
    private AppProperties appProperties;
    private OrderRepository ordersRepository;
    private KitchenService kitchenService;
    private ForkJoinPool customThreadPool;
    private final String THREAD_POOL_NAME = "KitchenPool";


    @Autowired
    public OrderServiceImpl(AppProperties appProperties, OrderRepository ordersRepository,
                            KitchenServiceImpl kitchenService) {
        this.appProperties = appProperties;
        this.ordersRepository = ordersRepository;
        this.kitchenService = kitchenService;
        this.customThreadPool = new ForkJoinPool(this.appProperties.getParallelism(),
                new NamedForkJoinWorkerThreadFactory(THREAD_POOL_NAME,true), null, false);
    }

    public List<Order> getAllOrders() throws IOException {
        return ordersRepository.getAllOrders();
    }

    public void processOrders() throws IOException, ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(this.appProperties.getParallelism());

        List<Order> orders = this.getAllOrders();

        try {
            this.customThreadPool.submit(() ->
                    orders.parallelStream().forEach(order ->  {
                                try {
                                    executorService.execute(() -> {
                                        try {
                                            this.kitchenService.cook(order);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    TimeUnit.SECONDS.sleep(this.appProperties.getThrottle());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    customThreadPool.shutdown();
                                }
                            }
                    )).get();
        } finally {
            this.customThreadPool.shutdownNow();
            executorService.awaitTermination(0, TimeUnit.SECONDS);
            executorService.shutdownNow();
        }
    }
}
