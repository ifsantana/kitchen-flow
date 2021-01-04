package br.com.italo.santana.challenge.prompt.consumers;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.enums.EventType;
import br.com.italo.santana.challenge.prompt.util.PrintUtil;
import br.com.italo.santana.challenge.prompt.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class CourierConsumer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CourierConsumer.class.getSimpleName());
    private final Integer REGULAR_SHELF_DECAY_MODIFIER = 1;
    private final Integer OVERFLOW_SHELF_DECAY_MODIFIER = 2;
    private BlockingQueue<Order> coldShelf;
    private BlockingQueue<Order> hotShelf;
    private BlockingQueue<Order> frozenShelf;
    private BlockingQueue<Order> overflowShelf;


    public CourierConsumer(BlockingQueue<Order> hotShelf, BlockingQueue<Order> coldShelf,
                           BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf) {
        this.coldShelf = coldShelf;
        this.hotShelf = hotShelf;
        this.frozenShelf = frozenShelf;
        this.overflowShelf = overflowShelf;
    }

    @Override
    public void run() {
        try {
            prickUpOrderFromRegularShelf(frozenShelf.poll());
            prickUpOrderFromRegularShelf(hotShelf.poll());
            prickUpOrderFromRegularShelf(coldShelf.poll());
            prickUpOrderFromROverflowShelf(overflowShelf.poll());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     *
     * @param order
     * @throws InterruptedException
     */
    private void prickUpOrderFromRegularShelf(Order order) throws InterruptedException {
        if (Objects.nonNull(order)) {
            PrintUtil.PrintShelvesContent(LOG, EventType.A_COURIER_WAS_NOTIFIED.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
            waitForACourier();
            if(order.isValidValidForDelivery(REGULAR_SHELF_DECAY_MODIFIER)) {
                PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            } else {
                PrintUtil.PrintShelvesContent(LOG,  EventType.ORDER_WAS_DISCARDED.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            }
        }
    }

    /**
     *
     * @param order
     * @throws InterruptedException
     */
    private void prickUpOrderFromROverflowShelf(Order order) throws InterruptedException {
        if (Objects.nonNull(order)) {
            PrintUtil.PrintShelvesContent(LOG, EventType.A_COURIER_WAS_NOTIFIED.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);

            waitForACourier();

            if(order.isValidValidForDelivery(OVERFLOW_SHELF_DECAY_MODIFIER)) {

                PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            } else {
                PrintUtil.PrintShelvesContent(LOG, EventType.ORDER_WAS_DISCARDED.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            }
        }
    }

    /**
     * Waits for an courier.
     * @throws InterruptedException
     */
    private void waitForACourier() throws InterruptedException {
        Thread.sleep(RandomUtil.getRandomNumberUsingNextInt(2000, 6000));
    }
}
