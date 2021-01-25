package br.com.italo.santana.challenge.prompt.interfaces.delivery;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;
import br.com.italo.santana.challenge.prompt.services.delivery.CourierServiceImpl;

/**
 * Contract for {@link CourierServiceImpl}
 *
 * @author italosantana
 */
@Component
public interface CourierService {

    /**
     * Sends a courier to pickup an order {@link Order} to delivery
     * @param coldShelve
     * @param hotShelve
     * @param frozenShelve
     * @param overflowShelve
     */
    void sendCourier(BlockingQueue<Order> coldShelve, BlockingQueue<Order> hotShelve,
                     BlockingQueue<Order> frozenShelve, BlockingQueue<Order> overflowShelve, Order order);
}
