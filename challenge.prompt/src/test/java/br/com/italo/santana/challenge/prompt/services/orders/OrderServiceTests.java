package br.com.italo.santana.challenge.prompt.services.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.repositories.OrderRepositoryImpl;
import br.com.italo.santana.challenge.prompt.util.GenericBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

    private List<Order> orders;

    @MockBean
    private OrderRepositoryImpl repository;

    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private AppProperties appProperties;

    @BeforeEach
    public void setup() {

        Order bananaSplitOrder = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Banana Split")
                .with(Order::setTemp, "frozen")
                .with(Order::setDecayRate, 0.63)
                .with(Order::setShelfLife, 20)
                .build();

        Order mcFlurryOrder = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("58e9b5fe-3fde-4a27-8e98-682e58a4a65d"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "McFlury")
                .with(Order::setTemp, "frozen")
                .with(Order::setDecayRate, 0.4)
                .with(Order::setShelfLife, 375)
                .build();

        this.orders = Arrays.asList(bananaSplitOrder, mcFlurryOrder);
    }

    @Test
    public void shouldReturnListOfOrders() throws IOException {

        Mockito.when(this.repository.getAllOrders()).thenReturn(this.orders);

        assertEquals(2,  this.service.getAllOrders().size(), "");
    }
}
