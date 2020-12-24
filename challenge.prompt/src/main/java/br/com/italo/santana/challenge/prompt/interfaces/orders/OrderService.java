package br.com.italo.santana.challenge.prompt.interfaces.orders;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.springframework.stereotype.Component;
import br.com.italo.santana.challenge.prompt.config.AppProperties;
import java.io.IOException;
import br.com.italo.santana.challenge.prompt.service.orders.OrderServiceImpl;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Contract for {@link OrderServiceImpl}
 */
@Component
public interface OrderService {

    /**
     * Encapsulates the repository method {@link #getAllOrders()} from repository layer {@link OrderRepository}
     * @return
     * @throws IOException
     */
    List<Order> getAllOrders() throws IOException;

    /**
     * Process orders  {@link Order} controlling the flow according to the application settings {@link AppProperties}
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    void processOrders() throws IOException, ExecutionException, InterruptedException, TimeoutException;
}
