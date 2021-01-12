package br.com.italo.santana.challenge.prompt.domain;


import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.util.GenericBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = AppProperties.class)
@TestPropertySource("classpath:application.properties")
public class OrderTests {

    @Autowired
    private AppProperties appProperties;
    private Order exampleOrderValidToDelivery, exampleOrderNotValidToDelivery;

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

        this.exampleOrderValidToDelivery.isValidToDelivery(this.appProperties.getRegularShelfDecayModifier());

        this.exampleOrderNotValidToDelivery = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("4f304b59-6634-4558-a128-a8ce12b1f818"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Pistachio Ice Cream")
                .with(Order::setTemp, "frozen")
                .with(Order::setShelfLife, 0)
                .with(Order::setDecayRate, 0.4)
                .build();
    }

    /**
     * This method tests the constructor with parameters
     */
    @Test
    public void shouldReturnOrderEqualsSetupOrder() {
        Order order = new Order(exampleOrderValidToDelivery.getId(), exampleOrderValidToDelivery.getName(),
                                exampleOrderValidToDelivery.getTemp(), exampleOrderValidToDelivery.getShelfLife(),
                                exampleOrderValidToDelivery.getDecayRate());

        order.isValidToDelivery(1);

        assertNotNull(order.getCreateDate());
        assertEquals(order.getId(), exampleOrderValidToDelivery.getId(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getId()).toString());
        assertEquals(order.getName(), exampleOrderValidToDelivery.getName(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getName()).toString());
        assertEquals(order.getTemp(), exampleOrderValidToDelivery.getTemp(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getTemp()).toString());
        assertEquals(order.getShelfLife(), exampleOrderValidToDelivery.getShelfLife(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getShelfLife()).toString());
        assertEquals(order.getDecayRate(), exampleOrderValidToDelivery.getDecayRate(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getDecayRate()).toString());
        assertEquals(order.getShelfLifeValue(), exampleOrderValidToDelivery.getShelfLifeValue(), new StringBuilder("Must be ").append(exampleOrderValidToDelivery.getShelfLifeValue()).toString());
    }

    /**
     * This method tests the default constructor parameters
     */
    @Test
    public void shouldReturnAOrderWithCreateDate() {
        Order order = new Order();

        assertNotNull(order.getCreateDate());
    }

    /**
     *
     */
    @Test
    public void shouldReturnThatOrderIsValidToDelivery() {
        assertTrue(exampleOrderValidToDelivery.isValidToDelivery(this.appProperties.getRegularShelfDecayModifier()));
    }

    @Test
    public void shouldReturnThatOrderIsNotValidToDelivery() {
        assertFalse(exampleOrderNotValidToDelivery.isValidToDelivery(this.appProperties.getOverflowShelfDecayModifier()));
    }
}
