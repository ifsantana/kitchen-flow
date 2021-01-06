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
import br.com.italo.santana.challenge.prompt.producers.Producer;

/**
 * This class listen order entry notifications on shelves emitted by {@link Producer} and
 * waits a courier to pick up the order {@link Order}.
 *
 * @author italosantana
 */
public class CourierConsumer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CourierConsumer.class.getSimpleName());
    private Integer regularShelfDecayModifier, overflowShelfDecayModifier;
    private Integer courierMinArriveTime, courierMaxArriveTime;
    private BlockingQueue<Order> coldShelf, hotShelf, frozenShelf, overflowShelf;

    public CourierConsumer(Integer courierMinArriveTime, Integer courierMaxArriveTime,
                           Integer regularShelfDecayModifier, Integer overflowShelfDecayModifier,
                           BlockingQueue<Order> hotShelf, BlockingQueue<Order> coldShelf,
                           BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf) {
        this.regularShelfDecayModifier = regularShelfDecayModifier;
        this.overflowShelfDecayModifier = overflowShelfDecayModifier;
        this.courierMinArriveTime = courierMinArriveTime;
        this.courierMaxArriveTime = courierMaxArriveTime;
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

            if(order.isValidValidForDelivery(regularShelfDecayModifier)) {

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

            if(order.isValidValidForDelivery(overflowShelfDecayModifier)) {

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
        TimeUnit.SECONDS.sleep(RandomUtil.getRandomNumberUsingNextInt(this.courierMinArriveTime,
                                                                      this.courierMaxArriveTime));
    }
}
