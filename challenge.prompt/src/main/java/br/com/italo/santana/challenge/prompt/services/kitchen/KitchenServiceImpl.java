package br.com.italo.santana.challenge.prompt.services.kitchen;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.enums.EventType;
import br.com.italo.santana.challenge.prompt.interfaces.kitchens.KitchenService;
import br.com.italo.santana.challenge.prompt.interfaces.shelves.ShelfService;
import br.com.italo.santana.challenge.prompt.util.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class that receives orders to cook and to notify the couriers.
 *
 * @author italosantana
 */
@Service
public class KitchenServiceImpl implements KitchenService {

    private final static Logger LOG = LoggerFactory.getLogger(KitchenService.class.getSimpleName());
    private ShelfService shelvesService;

    @Autowired
    public KitchenServiceImpl(ShelfService shelvesService) { }

    public void cook(Order order) throws InterruptedException {

        PrintUtil.PrintShelvesContent(LOG, EventType.ORDER_RECEIVED_BY_THE_KITCHEN.label, order,
                this.shelvesService.getHotShelf(), this.shelvesService.getColdShelf(), this.shelvesService.getFrozenShelf(), this.shelvesService.getOverflowShelf());
    }
}
