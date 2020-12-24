package br.com.italo.santana.challenge.prompt.interfaces.shelves;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;

@Component
public interface ShelfService {

    /**
     * Allocates orders in appropriate shelf.
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
    BlockingQueue getColdShelf();

    /**
     * Returns the hotShelf current state
     * @return
     */
    BlockingQueue getHotShelf();

    /**
     * Returns the frozenShelf current state
     * @return
     */
    BlockingQueue getFrozenShelf();

    /**
     * Returns the overflowShelf current state
     * @return
     */
    BlockingQueue getOverflowShelf();
}
