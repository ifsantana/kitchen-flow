package br.com.italo.santana.challenge.prompt.interfaces.delivery;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;

@Component
public interface CourierService {

    /**
     * Sends a courier to pickup an order {@link Order} to delivery
     * @param coldShelve
     * @param hotShelve
     * @param frozenShelve
     * @param overflowShelve
     * @throws InterruptedException
     */
    void sendCourier(BlockingQueue<Order> coldShelve, BlockingQueue<Order> hotShelve,
                     BlockingQueue<Order> frozenShelve, BlockingQueue<Order> overflowShelve) throws InterruptedException;
}
