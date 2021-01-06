package br.com.italo.santana.challenge.prompt.services.delivery;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.consumers.CourierConsumer;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.interfaces.delivery.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingQueue;

/**
 * Service responsible for manage the couriers to pick up the orders when it's available.
 *
 * @author italosantana
 */
@Service
public class CourierServiceImpl implements CourierService {
    private AppProperties appProperties;

    @Autowired
    public CourierServiceImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * this method sends a new courier to pick up an order.
     * @param coldShelve
     * @param hotShelve
     * @param frozenShelve
     * @param overflowShelve
     */
    public void sendCourier(BlockingQueue<Order> coldShelve, BlockingQueue<Order> hotShelve,
                            BlockingQueue<Order> frozenShelve, BlockingQueue<Order> overflowShelve) {

        new Thread(new CourierConsumer(this.appProperties.getCourierMinArriveTime(),
                                        this.appProperties.getCourierMaxArriveTime(),
                                        this.appProperties.getRegularShelfDecayModifier(),
                                        this.appProperties.getOverflowShelfDecayModifier(),
                                        coldShelve, hotShelve, frozenShelve, overflowShelve)).start();
    }
}
