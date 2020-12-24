package br.com.italo.santana.challenge.prompt.interfaces.orders;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Class responsible to access the file repository and return the orders.
 */
@Component
public interface OrderRepository {

    /**
     * Access the file repository and return all orders
     * @return
     * @throws IOException
     */
    List<Order> getAllOrders() throws IOException;
}
