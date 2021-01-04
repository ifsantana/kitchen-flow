package br.com.italo.santana.challenge.prompt.service.orders;

import br.com.italo.santana.challenge.prompt.config.AppProperties;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.factories.NamedForkJoinWorkerThreadFactory;
import br.com.italo.santana.challenge.prompt.interfaces.kitchens.KitchenService;
import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderRepository;
import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderService;
import br.com.italo.santana.challenge.prompt.service.kitchen.KitchenServiceImpl;
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
    private AppProperties appProperties;
    private OrderRepository ordersRepository;
    private KitchenService kitchenService;
    private ForkJoinPool customThreadPool;

    @Autowired
    public OrderServiceImpl(AppProperties appProperties, OrderRepository ordersRepository,
                            KitchenServiceImpl kitchenService) {
        this.appProperties = appProperties;
        this.ordersRepository = ordersRepository;
        this.kitchenService = kitchenService;
        this.customThreadPool = new ForkJoinPool(this.appProperties.getParallelism(),
                new NamedForkJoinWorkerThreadFactory(this.appProperties.getThreadPoolName(),false), null, false);
    }

    public List<Order> getAllOrders() throws IOException {
        return ordersRepository.getAllOrders();
    }

    public void processOrders() throws IOException, InterruptedException, ExecutionException {

        List<Order> orders = this.getAllOrders();

        try {
            this.customThreadPool.submit(() ->
                    orders.parallelStream().forEach(order ->  {
                        try {
                            this.kitchenService.cook(order);
                            TimeUnit.SECONDS.sleep(this.appProperties.getThrottle());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    })).get();
        } finally {
            this.customThreadPool.shutdownNow();
        }
    }
}
