package br.com.italo.santana.challenge.prompt.consumers;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.enums.EventType;
import br.com.italo.santana.challenge.prompt.util.PrintUtil;
import br.com.italo.santana.challenge.prompt.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import br.com.italo.santana.challenge.prompt.producers.Producer;

/**
 * This class listen order entry notifications on shelves emitted by {@link Producer} and
 * waits a courier to pick up the order {@link Order}.
 */
public class CourierConsumer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CourierConsumer.class.getSimpleName());
    /**
     * TODO - Get decay modifiers from application.properties.
     */
    private Integer REGULAR_SHELF_DECAY_MODIFIER;
    private Integer OVERFLOW_SHELF_DECAY_MODIFIER;
    private BlockingQueue<Order> coldShelf;
    private BlockingQueue<Order> hotShelf;
    private BlockingQueue<Order> frozenShelf;
    private BlockingQueue<Order> overflowShelf;

    public CourierConsumer(Integer regularShelfDecayModifier, Integer overflowShelfDecayModifier,
                           BlockingQueue<Order> hotShelf, BlockingQueue<Order> coldShelf,
                           BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf) {
        this.REGULAR_SHELF_DECAY_MODIFIER = regularShelfDecayModifier;
        this.OVERFLOW_SHELF_DECAY_MODIFIER = overflowShelfDecayModifier;
        this.coldShelf = coldShelf;
        this.hotShelf = hotShelf;
        this.frozenShelf = frozenShelf;
        this.overflowShelf = overflowShelf;
    }

    @Override
    public void run() {
        try {
            pickUpOrderFromRegularShelf(frozenShelf.poll());
            pickUpOrderFromRegularShelf(hotShelf.poll());
            pickUpOrderFromRegularShelf(coldShelf.poll());
            pickUpOrderFromOverflowShelf(overflowShelf.poll());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method waits a courier to pick up an order from regular shelf (hot, cold or frozen shelf).
     * @param order
     * @throws InterruptedException
     */
    private void pickUpOrderFromRegularShelf(Order order) throws InterruptedException {
        if (Objects.nonNull(order)) {

            if(order.isValidValidForDelivery(REGULAR_SHELF_DECAY_MODIFIER)) {

                PrintUtil.PrintShelvesContent(LOG, EventType.A_COURIER_WAS_NOTIFIED.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);

                waitForACourier();

                PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            } else {
                PrintUtil.PrintShelvesContent(LOG,  EventType.ORDER_WAS_DISCARDED.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            }
        }
    }

    /**
     * This method waits a courier to pick up an order from overflow shelf.
     * @param order
     * @throws InterruptedException
     */
    private void pickUpOrderFromOverflowShelf(Order order) throws InterruptedException {
        if (Objects.nonNull(order)) {

            if(order.isValidValidForDelivery(OVERFLOW_SHELF_DECAY_MODIFIER)) {

                PrintUtil.PrintShelvesContent(LOG, EventType.A_COURIER_WAS_NOTIFIED.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);

                waitForACourier();

                PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            } else {
                PrintUtil.PrintShelvesContent(LOG, EventType.ORDER_WAS_DISCARDED.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            }
        }
    }

    /**
     * Waits for a courier.
     * @throws InterruptedException
     */
    private void waitForACourier() throws InterruptedException {
        Thread.sleep(RandomUtil.getRandomNumberUsingNextInt(2000, 6000));
    }
}
