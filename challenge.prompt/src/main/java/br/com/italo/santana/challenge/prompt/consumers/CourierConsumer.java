package br.com.italo.santana.challenge.prompt.consumers;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.enums.EventType;
import br.com.italo.santana.challenge.prompt.util.PrintUtil;
import br.com.italo.santana.challenge.prompt.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import br.com.italo.santana.challenge.prompt.producers.Producer;

/**
 * This class represents an hub that listen/consume shelves order entry notifications emitted by {@link Producer} and
 * waits a courier to pick up the order {@link Order}.
 *
 * @author italosantana
 */
public class CourierConsumer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CourierConsumer.class.getSimpleName());
    private Integer regularShelfDecayModifier, overflowShelfDecayModifier;
    private Integer courierMinArriveTime, courierMaxArriveTime;
    private BlockingQueue<Order> coldShelf, hotShelf, frozenShelf, overflowShelf;
    private Order orderToPickUp;

    public CourierConsumer(Integer courierMinArriveTime, Integer courierMaxArriveTime,
                           Integer regularShelfDecayModifier, Integer overflowShelfDecayModifier,
                           BlockingQueue<Order> hotShelf, BlockingQueue<Order> coldShelf,
                           BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf, Order orderToPickUp) {
        this.regularShelfDecayModifier = regularShelfDecayModifier;
        this.overflowShelfDecayModifier = overflowShelfDecayModifier;
        this.courierMinArriveTime = courierMinArriveTime;
        this.courierMaxArriveTime = courierMaxArriveTime;
        this.coldShelf = coldShelf;
        this.hotShelf = hotShelf;
        this.frozenShelf = frozenShelf;
        this.overflowShelf = overflowShelf;
        this.orderToPickUp = orderToPickUp;
    }

    @Override
    public void run() {
        try {
            if(this.orderToPickUp.getTemp().equalsIgnoreCase("hot")) {
                this.pickUpHotOrder(orderToPickUp);
            } else if (this.orderToPickUp.getTemp().equalsIgnoreCase("cold")) {
                this.pickUpColdOrder(orderToPickUp);
            } else {
                this.pickUpFrozenOrder(orderToPickUp);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void pickUpHotOrder(Order order) throws InterruptedException {
            if(order.isValidToDelivery(regularShelfDecayModifier)) {

                waitForACourier(order);

                findAndRemoveItemFromShelf(order, this.hotShelf, this.overflowShelf);

                PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);

            } else {

                findAndRemoveItemFromShelf(order, this.hotShelf, this.overflowShelf);

                PrintUtil.PrintShelvesContent(LOG,  EventType.ORDER_WAS_DISCARDED.label, order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            }
    }

    private void pickUpColdOrder(Order order) throws InterruptedException {
        if(order.isValidToDelivery(regularShelfDecayModifier)) {

            waitForACourier(order);

            findAndRemoveItemFromShelf(order, this.coldShelf, this.overflowShelf);

            PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);

        } else {
            findAndRemoveItemFromShelf(order, this.coldShelf, this.overflowShelf);

            PrintUtil.PrintShelvesContent(LOG,  EventType.ORDER_WAS_DISCARDED.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
        }
    }

    private void pickUpFrozenOrder(Order order) throws InterruptedException {
        if(order.isValidToDelivery(regularShelfDecayModifier)) {

            waitForACourier(order);

            findAndRemoveItemFromShelf(order, this.frozenShelf, this.overflowShelf);

            PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);

        } else {
            findAndRemoveItemFromShelf(order, this.frozenShelf, this.overflowShelf);

            PrintUtil.PrintShelvesContent(LOG,  EventType.ORDER_WAS_DISCARDED.label, order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
        }
    }

    private void findAndRemoveItemFromShelf(Order order, BlockingQueue<Order> regularShelf, BlockingQueue<Order> overflowShelf) {
        if(!regularShelf.remove(order)) {
            overflowShelf.remove(order);
        }
    }

    /**
     * Waits for a courier.
     * @throws InterruptedException
     */
    private void waitForACourier(Order order) throws InterruptedException {
        PrintUtil.PrintShelvesContent(LOG, EventType.A_COURIER_WAS_NOTIFIED.label, order,
                hotShelf, coldShelf, frozenShelf, overflowShelf);
        TimeUnit.SECONDS.sleep(RandomUtil.getRandomNumberUsingNextInt(this.courierMinArriveTime,this.courierMaxArriveTime));
    }
}
