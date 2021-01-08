package br.com.italo.santana.challenge.prompt.domain;


import br.com.italo.santana.challenge.prompt.util.GenericBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTests {

    private Order exampleOrderValidToDelivery, exampleOrderNotValidToDelivery;
    private final Integer REGULAR_SHELF_DECAY_MODIFIER = 1;
    private final Integer OVERFLOW_SHELF_DECAY_MODIFIER = 2;

    @BeforeEach
    public void setup() {
        this.exampleOrderValidToDelivery = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Banana Split")
                .with(Order::setTemp, "frozen")
                .with(Order::setShelfLife, 20)
                .with(Order::setDecayRate, 0.63)
                .build();

        this.exampleOrderValidToDelivery.isValidValidForDelivery(REGULAR_SHELF_DECAY_MODIFIER);

        this.exampleOrderNotValidToDelivery = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("4f304b59-6634-4558-a128-a8ce12b1f818"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Pistachio Ice Cream")
                .with(Order::setTemp, "frozen")
                .with(Order::setShelfLife, 0)
                .with(Order::setDecayRate, 0.4)
                .build();
    }

    @Test
    public void shouldReturnOrderEqualsSetupOrder() {
        Order order = new Order(exampleOrderValidToDelivery.getId(), exampleOrderValidToDelivery.getName(),
                                exampleOrderValidToDelivery.getTemp(), exampleOrderValidToDelivery.getShelfLife(),
                                exampleOrderValidToDelivery.getDecayRate());

        order.isValidValidForDelivery(1);

        assertNotNull(order.getCreateDate());
        assertEquals(order.getId(), exampleOrderValidToDelivery.getId(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getId()).toString());
        assertEquals(order.getName(), exampleOrderValidToDelivery.getName(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getName()).toString());
        assertEquals(order.getTemp(), exampleOrderValidToDelivery.getTemp(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getTemp()).toString());
        assertEquals(order.getShelfLife(), exampleOrderValidToDelivery.getShelfLife(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getShelfLife()).toString());
        assertEquals(order.getDecayRate(), exampleOrderValidToDelivery.getDecayRate(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getDecayRate()).toString());
        assertEquals(order.getShelfLifeValue(), exampleOrderValidToDelivery.getShelfLifeValue(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getShelfLifeValue()).toString());
    }

    @Test
    public void shouldReturnAOrderWithCreateDate() {
        Order order = new Order();

        assertNotNull(order.getCreateDate());
    }

    @Test
    public void shouldReturnThatOrderIsValidToDelivery() {
        assertTrue(exampleOrderValidToDelivery.isValidValidForDelivery(REGULAR_SHELF_DECAY_MODIFIER));
    }

    @Test
    public void shouldReturnThatOrderIsNotValidToDelivery() {
        assertFalse(exampleOrderNotValidToDelivery.isValidValidForDelivery(OVERFLOW_SHELF_DECAY_MODIFIER));
    }
}
