package br.com.italo.santana.challenge.prompt.services.shelves;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.producers.Producer;
import br.com.italo.santana.challenge.prompt.interfaces.shelves.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Service responsible for shelves management.
 */
@Service
public class ShelfServiceImpl implements ShelfService {

    private BlockingQueue<Order> coldShelf;
    private BlockingQueue<Order> hotShelf;
    private BlockingQueue<Order> frozenShelf;
    private BlockingQueue<Order> overflowShelf;
    private Producer producer;

    @Autowired
    public ShelfServiceImpl(AppProperties appProperties) {
        this.coldShelf = new LinkedBlockingQueue<>(appProperties.getColdShelfCapacity());
        this.hotShelf = new LinkedBlockingQueue<>(appProperties.getHotShelfCapacity());
        this.frozenShelf = new LinkedBlockingQueue<>(appProperties.getFrozenShelfCapacity());
        this.overflowShelf = new LinkedBlockingQueue<>(appProperties.getOverflowShelfCapacity());
        this.producer = new Producer(this.coldShelf, this.hotShelf, this.frozenShelf, this.overflowShelf);
    }

    public void allocateOrderInAppropriateShelf(Order order) throws InterruptedException {

        if(!tryToAllocateInRegularShelf(order)) {
            if(!tryToAllocateInOverflowShelf(order)) {

                Order movedOrder = overflowShelf.take();

                if(!tryToAllocateInRegularShelf(movedOrder)) {
                    tryToAllocateInOverflowShelf(movedOrder);
                }
            }
        }
    }

    public boolean tryToAllocateInRegularShelf(Order order) {

        if(order.getTemp().equalsIgnoreCase("hot") && hotShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnHotShelve(order);
        } else if(order.getTemp().equalsIgnoreCase("cold") && coldShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnColdShelve(order);
        } else if(order.getTemp().equalsIgnoreCase("frozen") && frozenShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnFrozenShelve(order);
        } else {
            return false;
        }
    }

    public boolean tryToAllocateInOverflowShelf(Order order) {

        if(overflowShelf.remainingCapacity() > 0) {
            return this.producer.putOrderOnOverflowShelve(order);
        }

        return false;
    }

    public BlockingQueue<Order> getColdShelf() {
        return this.coldShelf;
    }

    public BlockingQueue<Order> getHotShelf() {
        return this.hotShelf;
    }

    public BlockingQueue<Order> getFrozenShelf() {
        return this.frozenShelf;
    }

    public BlockingQueue<Order> getOverflowShelf() {
        return this.overflowShelf;
    }
}
