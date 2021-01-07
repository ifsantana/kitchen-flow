package br.com.italo.santana.challenge.prompt.util;

import br.com.italo.santana.challenge.prompt.domain.Order;
import org.slf4j.Logger;
import java.util.concurrent.BlockingQueue;

/**
 * Utility class to log application events and states.
 *
 * @author italosantana
 */
public class PrintUtil {

    public static void PrintShelvesContent(Logger logger, String eventDescription,Object order,
                                           BlockingQueue<Order> hotShelf, BlockingQueue<Order> coldShelf,
                                           BlockingQueue<Order> frozenShelf, BlockingQueue<Order> overflowShelf) {

        StringBuilder event = new StringBuilder("{ event_type: ")
                .append(eventDescription)
                .append(", current_order: ")
                .append(order.toString())
                .append(", hotShelf: " )
                .append(hotShelf)
                .append(", coldShelf: ")
                .append(coldShelf)
                .append(", frozenShelf: ")
                .append(frozenShelf)
                .append(", overflowShelf: ")
                .append(overflowShelf)
                .append(" }");

        logger.info("{}", JsonParserUtil.prettyJson(event.toString()));
    }
}
