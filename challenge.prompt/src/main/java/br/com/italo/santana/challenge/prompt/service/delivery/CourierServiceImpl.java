package br.com.italo.santana.challenge.prompt.service.delivery;

import br.com.italo.santana.challenge.prompt.consumers.CourierConsumer;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.interfaces.delivery.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    public CourierServiceImpl() { }

    /**
     *
     * @param coldShelve
     * @param hotShelve
     * @param frozenShelve
     * @param overflowShelve
     */
    public void sendCourier(BlockingQueue<Order> coldShelve, BlockingQueue<Order> hotShelve,
                            BlockingQueue<Order> frozenShelve, BlockingQueue<Order> overflowShelve) {

        new Thread(new CourierConsumer(coldShelve, hotShelve, frozenShelve, overflowShelve)).start();
    }
}
