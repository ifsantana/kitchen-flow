package br.com.italo.santana.challenge.prompt.interfaces.shelves;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;
import br.com.italo.santana.challenge.prompt.services.shelves.ShelfServiceImpl;

/**
 * Contract for {@link ShelfServiceImpl}
 *
 * @author italosantana
 */
@Component
public interface ShelfService {

    /**
     * Allocates orders in appropriate shelf.
     *
     * Each order should be placed on a shelf that matches the order’s temperature.
     * If that shelf is full, an order can be placed on the overflow shelf.
     * If the overflow shelf is full, an existing order of your choosing on the overflow should be moved to an allowable shelf with room.
     * If no such move is possible, a random order from the overflow shelf should be discarded as waste (will not be available for a courier pickup)
     * and the current order must be placed on the overflow shelf replacing the previously discarded order.
     * @param order
     * @throws InterruptedException
     */
    void allocateOrderInAppropriateShelf(Order order) throws InterruptedException;

    /**
     * Tries to allocate the order on the ideal shelf according to its temperature.
     * @param order
     * @return
     * @throws InterruptedException
     */
    boolean tryToAllocateInRegularShelf(Order order);

    /**
     * Tries to allocate order in overflow shelf.
     * @param order
     * @return
     * @throws InterruptedException
     */
    boolean tryToAllocateInOverflowShelf(Order order);

    /**
     * Returns the coldShelf current state
     * @return
     */
    BlockingQueue<Order> getColdShelf();

    /**
     * Returns the hotShelf current state
     * @return
     */
    BlockingQueue<Order> getHotShelf();

    /**
     * Returns the frozenShelf current state
     * @return
     */
    BlockingQueue<Order> getFrozenShelf();

    /**
     * Returns the overflowShelf current state
     * @return
     */
    BlockingQueue<Order> getOverflowShelf();
}
