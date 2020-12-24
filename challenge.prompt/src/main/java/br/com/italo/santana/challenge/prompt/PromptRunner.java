package br.com.italo.santana.challenge.prompt;

import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PromptRunner  implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(PromptRunner.class);

    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        this.orderService.processOrders();
    }
}
