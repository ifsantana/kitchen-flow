package br.com.italo.santana.challenge.prompt.services.orders;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.consumers.CourierConsumer;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.factories.NamedForkJoinWorkerThreadFactory;
import br.com.italo.santana.challenge.prompt.interfaces.delivery.CourierService;
import br.com.italo.santana.challenge.prompt.interfaces.kitchens.KitchenService;
import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderRepository;
import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderService;
import br.com.italo.santana.challenge.prompt.interfaces.shelves.ShelfService;
import br.com.italo.santana.challenge.prompt.services.kitchen.KitchenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * Service responsible for process the orders, controlling the flow according to the application settings.
 *
 * @author italosantana
 */
@Service
public class OrderServiceImpl implements OrderService {
    private AppProperties appProperties;
    private OrderRepository ordersRepository;
    private KitchenService kitchenService;
    private ShelfService shelfService;
    private CourierService courierService;
    private ForkJoinPool customThreadPool;

    @Autowired
    public OrderServiceImpl(AppProperties appProperties, OrderRepository ordersRepository,
                            KitchenServiceImpl kitchenService, ShelfService shelfService,
                            CourierService courierService) {
        this.appProperties = appProperties;
        this.ordersRepository = ordersRepository;
        this.kitchenService = kitchenService;
        this.shelfService = shelfService;
        this.courierService = courierService;
        this.setCustomThreadPool(this.appProperties.getParallelism());
    }

    private void setCustomThreadPool(int parallelism) {

        if(parallelism == 0) {
            this.customThreadPool = new ForkJoinPool(getIdealNumberOfThreads(),
                    new NamedForkJoinWorkerThreadFactory(this.appProperties.getThreadPoolName(),false),
                    null, false);
        } else {
            this.customThreadPool = new ForkJoinPool(parallelism,
                    new NamedForkJoinWorkerThreadFactory(this.appProperties.getThreadPoolName(),false),
                    null, false);
        }
    }

    public List<Order> getAllOrders() throws IOException {
        return ordersRepository.getAllOrders();
    }

    public void processOrders() throws IOException, InterruptedException, ExecutionException {

        List<Order> orders = this.getAllOrders();

        try {
            this.customThreadPool.submit(() ->
                    orders.parallelStream().forEachOrdered(order ->  {
                        try {
                            this.courierService.sendCourier(this.shelfService.getColdShelf(), this.shelfService.getHotShelf(),
                                    this.shelfService.getFrozenShelf(), this.shelfService.getOverflowShelf(), order);
                            this.kitchenService.cook(order);
                            this.shelfService.allocateOrderInAppropriateShelf(order);
                            TimeUnit.MILLISECONDS.sleep(this.appProperties.getThrottle());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    })).get();
        } finally {
            this.customThreadPool.shutdownNow();
        }
    }

    private Integer getIdealNumberOfThreads() {

        int cores = Runtime.getRuntime().availableProcessors();

        return cores * (1 + 50 / 5);
    }
}
