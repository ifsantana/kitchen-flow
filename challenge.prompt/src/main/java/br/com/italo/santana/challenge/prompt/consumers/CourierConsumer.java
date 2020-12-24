package br.com.italo.santana.challenge.prompt.consumers;

import br.com.italo.santana.challenge.prompt.domain.Order;
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
            while(true){
                prickUpOrderFromRegularShelf(frozenShelf.poll(RandomUtil.getRandomNumberUsingNextInt(2, 6), TimeUnit.SECONDS));
                prickUpOrderFromRegularShelf(hotShelf.poll(RandomUtil.getRandomNumberUsingNextInt(2, 6), TimeUnit.SECONDS));
                prickUpOrderFromRegularShelf(coldShelf.poll(RandomUtil.getRandomNumberUsingNextInt(2, 6), TimeUnit.SECONDS));
                prickUpOrderFromROverflowShelf(overflowShelf.poll(RandomUtil.getRandomNumberUsingNextInt(2, 6), TimeUnit.SECONDS));
            }
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
            PrintUtil.PrintShelvesContent(LOG, "COURIER_HAS_BEEN_NOTIFIED", order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
            waitForACourier();
            if(order.isValidValidForDelivery(REGULAR_SHELF_DECAY_MODIFIER)) {
                PrintUtil.PrintShelvesContent(LOG, "COURIER_HAS_PICKEDUP_ORDER", order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            } else {
                PrintUtil.PrintShelvesContent(LOG, "ORDER_HAS_DISCARDED", order,
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
            PrintUtil.PrintShelvesContent(LOG, "COURIER_HAS_BEEN_NOTIFIED", order,
                    hotShelf, coldShelf, frozenShelf, overflowShelf);
            waitForACourier();
            if(order.isValidValidForDelivery(OVERFLOW_SHELF_DECAY_MODIFIER)) {
                PrintUtil.PrintShelvesContent(LOG, "COURIER_HAS_PICKEDUP_ORDER", order,
                        hotShelf, coldShelf, frozenShelf, overflowShelf);
            } else {
                PrintUtil.PrintShelvesContent(LOG, "ORDER_HAS_DISCARDED", order,
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
