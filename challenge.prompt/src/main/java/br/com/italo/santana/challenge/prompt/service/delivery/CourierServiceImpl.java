package br.com.italo.santana.challenge.prompt.service.delivery;

import br.com.italo.santana.challenge.prompt.consumers.CourierConsumer;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.interfaces.delivery.CourierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
@Service
public class CourierServiceImpl implements CourierService {

    private static Logger LOG = LoggerFactory.getLogger(CourierServiceImpl.class);

    @Autowired
    public CourierServiceImpl() { }

    /**
     *
     * @param coldShelve
     * @param hotShelve
     * @param frozenShelve
     * @param overflowShelve
     * @throws InterruptedException
     */
    public void sendCourier(BlockingQueue<Order> coldShelve, BlockingQueue<Order> hotShelve,
                            BlockingQueue<Order> frozenShelve, BlockingQueue<Order> overflowShelve) throws InterruptedException {

        new Thread(new CourierConsumer(coldShelve, hotShelve, frozenShelve, overflowShelve)).start();
    }
}
