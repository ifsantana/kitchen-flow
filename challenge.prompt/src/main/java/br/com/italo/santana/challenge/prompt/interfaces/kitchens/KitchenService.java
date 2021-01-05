package br.com.italo.santana.challenge.prompt.interfaces.kitchens;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.services.kitchen.KitchenServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Contract for {@link KitchenServiceImpl}
 *
 * @author italosantana
 */
@Component
public interface KitchenService {

    /**
     * Kitchen receives an order, notify couriers, cook and sent to appropriate shelf.
     * @param order
     * @throws InterruptedException
     */
    void cook(Order order) throws InterruptedException;
}
