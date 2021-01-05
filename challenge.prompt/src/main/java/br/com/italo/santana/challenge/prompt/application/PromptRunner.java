package br.com.italo.santana.challenge.prompt.application;

import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PromptRunner  implements CommandLineRunner {

    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        this.orderService.processOrders();
    }
}
