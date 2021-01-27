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
            waitForACourier(this.orderToPickUp);

            if(this.orderToPickUp.getTemp().equalsIgnoreCase("hot")) {
                verifyIfOrderWillBeDeliveryOrDiscard(this.orderToPickUp, this.hotShelf, this.overflowShelf);
            } else if (this.orderToPickUp.getTemp().equalsIgnoreCase("cold")) {
                verifyIfOrderWillBeDeliveryOrDiscard(this.orderToPickUp, this.coldShelf, this.overflowShelf);
            } else {
                verifyIfOrderWillBeDeliveryOrDiscard(this.orderToPickUp, this.frozenShelf, this.overflowShelf);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Verify if current order is valid based on shelf decay modifier.
     * if the order is valid, the courier will pick up the order from shelf to delivery it,
     * else the order will be removed from shelf and discarded.
     * @param order
     * @param regularShelf
     * @param overflowShelf
     */
    private void verifyIfOrderWillBeDeliveryOrDiscard(Order order, BlockingQueue<Order> regularShelf, BlockingQueue<Order> overflowShelf) {

        if(regularShelf.contains(order)){
            if(order.isValidToDelivery(this.regularShelfDecayModifier)) {
                pickUpOrder(order, regularShelf);
            } else {
                discardOrder(order, regularShelf);
            }
        } else {
            if (order.isValidToDelivery(this.overflowShelfDecayModifier)) {
                pickUpOrder(order, overflowShelf);
            } else {
                discardOrder(order, overflowShelf);
            }
        }
    }

    /**
     * Pick up aq valid order from shelf to delivery.
     * @param order
     * @param shelf
     */
    private void pickUpOrder(Order order, BlockingQueue<Order> shelf) {

        shelf.remove(order);

        PrintUtil.PrintShelvesContent(LOG, EventType.COURIER_HAS_PICKED_UP_THE_ORDER.label, order,
                this.hotShelf, this.coldShelf, this.frozenShelf, this.overflowShelf);
    }

    /**
     * Remove and discard a wasted order from shelf.
     * @param order
     * @param shelf
     */
    private void discardOrder(Order order, BlockingQueue<Order> shelf) {

        shelf.remove(order);

        PrintUtil.PrintShelvesContent(LOG,  EventType.ORDER_WAS_DISCARDED.label, order,
                this.hotShelf, this.coldShelf, this.frozenShelf, this.overflowShelf);
    }

    /**
     * Waits for a courier.
     * @throws InterruptedException
     */
    private void waitForACourier(Order order) throws InterruptedException {
        PrintUtil.PrintShelvesContent(LOG, EventType.A_COURIER_WAS_NOTIFIED.label, order,
                this.hotShelf, this.coldShelf, this.frozenShelf, this.overflowShelf);
        TimeUnit.SECONDS.sleep(RandomUtil.getRandomNumberUsingNextInt(this.courierMinArriveTime,this.courierMaxArriveTime));
    }
}
