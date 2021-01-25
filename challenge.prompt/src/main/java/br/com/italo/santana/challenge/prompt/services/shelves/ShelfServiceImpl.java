package br.com.italo.santana.challenge.prompt.services.shelves;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.enums.EventType;
import br.com.italo.santana.challenge.prompt.producers.Producer;
import br.com.italo.santana.challenge.prompt.interfaces.shelves.ShelfService;
import br.com.italo.santana.challenge.prompt.util.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Service responsible for shelves management.
 *
 * @author italosantana
 */
@Service
public class ShelfServiceImpl implements ShelfService {
    private static final Logger LOG = LoggerFactory.getLogger(ShelfServiceImpl.class.getSimpleName());
    private AppProperties appProperties;
    private BlockingQueue<Order> coldShelf, hotShelf, frozenShelf, overflowShelf;
    private Producer producer;

    @Autowired
    public ShelfServiceImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.setColdShelf(this.appProperties.getColdShelfCapacity());
        this.setHotShelf(this.appProperties.getHotShelfCapacity());
        this.setFrozenShelf(this.appProperties.getFrozenShelfCapacity());
        this.setOverflowShelf(this.appProperties.getOverflowShelfCapacity());
        this.setProducer(new Producer(this.coldShelf, this.hotShelf, this.frozenShelf, this.overflowShelf));
    }

    public void allocateOrderInAppropriateShelf(Order order) throws InterruptedException {

        if(!tryToAllocateInRegularShelf(order)) {
            if(!tryToAllocateInOverflowShelf(order)) {

                Order movedOrder = overflowShelf.take();

                if(!tryToAllocateInRegularShelf(movedOrder)) {
                    PrintUtil.PrintShelvesContent(LOG,  EventType.ORDER_WAS_DISCARDED.label, movedOrder,
                            hotShelf, coldShelf, frozenShelf, overflowShelf);
                    tryToAllocateInOverflowShelf(order);
                }
            }
        }
    }

    public boolean tryToAllocateInRegularShelf(Order order) {

        if(order.getTemp().equalsIgnoreCase("hot") && hotShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnHotShelf(order);
        } else if(order.getTemp().equalsIgnoreCase("cold") && coldShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnColdShelf(order);
        } else if(order.getTemp().equalsIgnoreCase("frozen") && frozenShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnFrozenShelf(order);
        } else {
            return false;
        }
    }

    public boolean tryToAllocateInOverflowShelf(Order order) {

        if(overflowShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnOverflowShelf(order);
        }

        return false;
    }

    public BlockingQueue<Order> getColdShelf() {
        return this.coldShelf;
    }

    public void setColdShelf(Integer shelfCapacity) {
        if(Objects.isNull(shelfCapacity) || shelfCapacity == 0) {
            this.coldShelf = new LinkedBlockingQueue<>(1);
        } else {
            this.coldShelf = new LinkedBlockingQueue<>(shelfCapacity);
        }
    }

    public BlockingQueue<Order> getHotShelf() {
        return this.hotShelf;
    }

    public void setHotShelf(Integer shelfCapacity) {
        if(Objects.isNull(shelfCapacity) || shelfCapacity == 0) {
            this.hotShelf = new LinkedBlockingQueue<>(1);
        } else {
            this.hotShelf = new LinkedBlockingQueue<>(shelfCapacity);
        }
    }

    public BlockingQueue<Order> getFrozenShelf() {
        return this.frozenShelf;
    }

    public void setFrozenShelf(Integer shelfCapacity) {
        if(Objects.isNull(shelfCapacity) || shelfCapacity == 0) {
            this.frozenShelf = new LinkedBlockingQueue<>(1);
        } else {
            this.frozenShelf = new LinkedBlockingQueue<>(shelfCapacity);
        }
    }

    public BlockingQueue<Order> getOverflowShelf() {
        return this.overflowShelf;
    }

    public void setOverflowShelf(Integer shelfCapacity) {
        if(Objects.isNull(shelfCapacity) || shelfCapacity == 0) {
            this.overflowShelf = new LinkedBlockingQueue<>(1);
        } else {
            this.overflowShelf = new LinkedBlockingQueue<>(shelfCapacity);
        }
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
