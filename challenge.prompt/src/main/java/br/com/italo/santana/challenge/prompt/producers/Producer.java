package br.com.italo.santana.challenge.prompt.producers;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.util.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;

/**
 * Class responsible for producing messages for consumers when a new order is placed in the queue.
 */
public class Producer  {
    private static final Logger LOG = LoggerFactory.getLogger(Producer.class.getSimpleName());
    private BlockingQueue<Order> coldShelf;
    private BlockingQueue<Order> hotShelf;
    private BlockingQueue<Order> frozenShelf;
    private BlockingQueue<Order> overflowShelf;

    public Producer(BlockingQueue<Order> coldShelf, BlockingQueue<Order> hotShelf,
                   BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf) {
        this.coldShelf = coldShelf;
        this.hotShelf = hotShelf;
        this.frozenShelf = frozenShelf;
        this.overflowShelf = overflowShelf;
    }

    /**
     *
     * @param order
     * @return
     */
    public boolean putOrderOnColdShelve(Order order) {
        PrintUtil.PrintShelvesContent(LOG,"ORDER_HAS_INSERTED", order,
                hotShelf, coldShelf, frozenShelf, overflowShelf);
        //LOG.info("Order: " + order.getId() + " inserted in ColdShelve! name: " + order.getName() + " temp: " + order.getTemp() + " selfLife: " + order.getShelfLife() + " [HOTSHELF] " + hotShelf + " [COLDSHELF] " + coldShelf + " [FROZENSHELF] "+ frozenShelf + " [OVERFLOWSHELF] "+ overflowShelf);
        return coldShelf.offer(order);
    }

    /**
     *
     * @param order
     * @return
     */
    public boolean putOrderOnHotShelve(Order order) {
        PrintUtil.PrintShelvesContent(LOG, "ORDER_HAS_INSERTED", order,
                hotShelf, coldShelf, frozenShelf, overflowShelf);
        //LOG.info("Order: " + order.getId() + " inserted in HotShelve! name: " + order.getName() + " temp: " + order.getTemp() + " selfLife: " + order.getShelfLife() + " [HOTSHELF] " + hotShelf + " [COLDSHELF] " + coldShelf + " [FROZENSHELF] "+ frozenShelf + " [OVERFLOWSHELF] "+ overflowShelf);
        return hotShelf.offer(order);
    }

    /**
     *
     * @param order
     * @return
     */
    public boolean putOrderOnFrozenShelve(Order order) {
        PrintUtil.PrintShelvesContent(LOG, "ORDER_HAS_INSERTED", order,
                hotShelf, coldShelf, frozenShelf, overflowShelf);
        //LOG.info("Order: " + order.getId() + " inserted in FrozenShelve! name: " + order.getName() + " temp: " + order.getTemp() + " selfLife: " + order.getShelfLife() + " [HOTSHELF] " + hotShelf + " [COLDSHELF] " + coldShelf + " [FROZENSHELF] "+ frozenShelf + " [OVERFLOWSHELF] "+ overflowShelf);
        return frozenShelf.offer(order);
    }

    /***
     *
     * @param order
     * @return
     */
    public boolean putOrderOnOverflowShelve(Order order) {
        PrintUtil.PrintShelvesContent(LOG, "ORDER_HAS_INSERTED", order,
                hotShelf, coldShelf, frozenShelf, overflowShelf);
        //LOG.info("Order: " + order.getId() + " inserted in OverflowShelve! name: " + order.getName() + " temp: " + order.getTemp() + " selfLife: " + order.getShelfLife() + " [HOTSHELF] " + hotShelf + " [COLDSHELF] " + coldShelf + " [FROZENSHELF] "+ frozenShelf + " [OVERFLOWSHELF] "+ overflowShelf);
        return overflowShelf.offer(order);
    }
}
