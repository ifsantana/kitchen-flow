package br.com.italo.santana.challenge.prompt.util;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.slf4j.Logger;
import java.util.concurrent.BlockingQueue;

public class PrintUtil {

    public static void PrintShelvesContent(Logger logger, String eventDescription,Object order, BlockingQueue<Order> hotShelf, BlockingQueue<Order> coldShelf,
                                           BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf) {

        String teste = "{ event_description: " + eventDescription +
                ", current_order:" + order.toString() +
                ", hotShelf: " + hotShelf +
                ", coldShelf: " + coldShelf +
                ", frozenShelf: "+ frozenShelf +
                ", overflowShelf: "+ overflowShelf + " }";

        logger.info("{}",
               JsonParserUtil.prettyJson(teste));
    }
}
