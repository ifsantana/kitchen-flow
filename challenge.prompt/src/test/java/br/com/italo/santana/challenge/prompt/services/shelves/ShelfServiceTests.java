package br.com.italo.santana.challenge.prompt.services.shelves;

import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.util.GenericBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ShelfServiceTests {

    private Order coldTempOrder, hotTempOrder,hotTempOrder2, hotTempOrder3, frozenTempOrder;

    @InjectMocks
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

        this.hotTempOrder2 = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("69ad8b1c-3218-4c41-aa42-5aca43ca4173"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Brown Rice")
                .with(Order::setTemp, "hot")
                .with(Order::setShelfLife, 224)
                .with(Order::setDecayRate, 0.64)
                .build();

        this.hotTempOrder3 = GenericBuilderUtil.of(Order::new)
                .with(Order::setId, UUID.fromString("68037649-56cd-4f0f-90fd-6e6ccbf61b37"))
                .with(Order::setCreateDate, LocalDateTime.now())
                .with(Order::setName, "Cheese Pizza")
                .with(Order::setTemp, "hot")
                .with(Order::setShelfLife, 200)
                .with(Order::setDecayRate, 0.76)
                .build();
    }

    @Test
    public void shouldAllocateOrderInColdShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.coldTempOrder);

        assertTrue(Objects.equals(coldTempOrder, this.service.getColdShelf().take()));
    }

    @Test
    public void shouldAllocateOrderInHotShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder);

        assertTrue(Objects.equals(hotTempOrder, this.service.getHotShelf().take()));
    }

    @Test
    public void shouldAllocateOrderInFrozenShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.frozenTempOrder);

        assertTrue(Objects.equals(frozenTempOrder, this.service.getFrozenShelf().take()));
    }

    @Test
    public void shouldAllocateOrderInOverflowShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.frozenTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.coldTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder2);

        assertTrue(Objects.equals(hotTempOrder2, this.service.getOverflowShelf().take()));
    }

    @Test
    public void shouldReAllocateOrderInRegularShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.frozenTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.coldTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder2);
        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder3);

        assertTrue(Objects.equals(coldTempOrder, this.service.getColdShelf().take()));
        assertTrue(Objects.equals(frozenTempOrder, this.service.getFrozenShelf().take()));
        assertTrue(Objects.equals(hotTempOrder, this.service.getHotShelf().take()));
        assertTrue(Objects.equals(hotTempOrder3, this.service.getOverflowShelf().take()));
    }
}
