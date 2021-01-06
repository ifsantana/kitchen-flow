package br.com.italo.santana.challenge.prompt.services.shelves;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.util.GenericBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ShelfServiceTests {

    Order coldTempOrder, hotTempOrder, frozenTempOrder;

    @MockBean
    private ShelfServiceImpl service;

    @Mock
    private AppProperties appProperties;

    @BeforeEach
    public void setup() {

        this.frozenTempOrder = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("4f304b59-6634-4558-a128-a8ce12b1f818"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Pistachio Ice Cream")
                .with(Order::setTemp, "frozen")
                .with(Order::setShelfLife, 175)
                .with(Order::setDecayRate, 0.4)
                .build();

        this.coldTempOrder = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("2a23c77f-3ee3-4a52-8810-93e73df4e887"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Grilled Corn Salad")
                .with(Order::setTemp, "cold")
                .with(Order::setShelfLife, 305)
                .with(Order::setDecayRate, 0.1)
                .build();

        this.hotTempOrder = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("7f89b973-d4fd-4d6e-a279-afc2db6b3304"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Corn Dog")
                .with(Order::setTemp, "hot")
                .with(Order::setShelfLife, 203)
                .with(Order::setDecayRate, 0.3)
                .build();
    }

    @Test
    public void shouldAllocateOrderInColdShelf() {

    }

    @Test
    public void shouldAllocateOrderInHotShelf() {

    }

    @Test
    public void shouldAllocateOrderInFrozenShelf() {

    }

    @Test
    public void shouldAllocateOrderInOverflowShelf() {

    }
}
