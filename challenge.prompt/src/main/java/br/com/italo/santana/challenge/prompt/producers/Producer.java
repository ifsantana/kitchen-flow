package br.com.italo.santana.challenge.prompt.producers;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.enums.EventType;
import br.com.italo.santana.challenge.prompt.util.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;
import br.com.italo.santana.challenge.prompt.consumers.CourierConsumer;

/**
 * Class responsible for producing messages for consumers when a new order is placed in the queue.
 *
 * @author italosantana
 */
public class Producer  {
    private static final Logger LOG = LoggerFactory.getLogger(Producer.class.getSimpleName());
    private BlockingQueue<Order> coldShelf, hotShelf, frozenShelf, overflowShelf;

    public Producer(BlockingQueue<Order> coldShelf, BlockingQueue<Order> hotShelf,
                    BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf) {
        this.coldShelf = coldShelf;
        this.hotShelf = hotShelf;
        this.frozenShelf = frozenShelf;
        this.overflowShelf = overflowShelf;
    }

    /**
     * This method put and order in cold shelf and notifies an courier {@link CourierConsumer} to pick up the order.
     * @param order
     * @return
     */
    public boolean putOrderOnColdShelf(Order order) {

        boolean wasTheOrderInserted = coldShelf.offer(order);

        if(wasTheOrderInserted) {
            PrintUtil.PrintShelvesContent(LOG, EventType.ORDER_ALLOCATED_ON_THE_COLD_SHELF.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
        }

        return wasTheOrderInserted;
    }

    /**
     * This method put and order in hot shelf and notifies an courier {@link CourierConsumer} to pick up the order.
     * @param order
     * @return
     */
    public boolean putOrderOnHotShelf(Order order) {

        boolean wasTheOrderInserted = hotShelf.offer(order);

        if(wasTheOrderInserted) {
            PrintUtil.PrintShelvesContent(LOG, EventType.ORDER_ALLOCATED_ON_THE_HOT_SHELF.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
        }

        return wasTheOrderInserted;
    }

    /**
     * This method put and order in frozen shelf and notifies an courier {@link CourierConsumer} to pick up the order.
     * @param order
     * @return
     */
    public boolean putOrderOnFrozenShelf(Order order) {

        boolean wasTheOrderInserted = frozenShelf.offer(order);

        if(wasTheOrderInserted) {
            PrintUtil.PrintShelvesContent(LOG, EventType.ORDER_ALLOCATED_ON_THE_FROZEN_SHELF.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
        }

        return wasTheOrderInserted;
    }

    /***
     * This method put and order in cold overflow and notifies an courier {@link CourierConsumer} to pick up the order.
     * @param order
     * @return
     */
    public boolean putOrderOnOverflowShelf(Order order) {

        boolean wasTheOrderInserted = overflowShelf.offer(order);

        if(wasTheOrderInserted) {
            PrintUtil.PrintShelvesContent(LOG, EventType.ORDER_ALLOCATED_ON_THE_OVERFLOW_SHELF.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
        }

        return wasTheOrderInserted;
    }
}
