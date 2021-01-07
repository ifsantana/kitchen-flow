package br.com.italo.santana.challenge.prompt.services.shelves;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.producers.Producer;
import br.com.italo.santana.challenge.prompt.util.GenericBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ShelfServiceTests {
    private BlockingQueue<Order> coldShelf, hotShelf, frozenShelf, overflowShelf;
    private Order coldTempOrder, hotTempOrder, frozenTempOrder;
    private Producer producer;

    private ShelfServiceImpl service;

    @BeforeEach
    public void setup() {
        this.service = new ShelfServiceImpl();
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

        this.coldShelf = new LinkedBlockingQueue<>(1);
        this.hotShelf = new LinkedBlockingQueue<>(1);
        this.frozenShelf = new LinkedBlockingQueue<>(1);
        this.overflowShelf = new LinkedBlockingQueue<>(1);
        this.producer = new Producer(this.coldShelf, this.hotShelf, this.frozenShelf, this.overflowShelf);
        this.service.setColdShelf(this.coldShelf);
        this.service.setHotShelf(this.hotShelf);
        this.service.setFrozenShelf(this.frozenShelf);
        this.service.setOverflowShelf(this.overflowShelf);
        this.service.setProducer(this.producer);
    }

    @Test
    public void shouldAllocateOrderInColdShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.coldTempOrder);

        assertEquals(1, this.service.getColdShelf().size(), "");
    }

    @Test
    public void shouldAllocateOrderInHotShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder);

        assertEquals(1, this.service.getHotShelf().size(), "");
    }

    @Test
    public void shouldAllocateOrderInFrozenShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.frozenTempOrder);

        assertEquals(1, this.service.getFrozenShelf().size(), "");
    }

    @Test
    public void shouldAllocateOrderInOverflowShelf() throws InterruptedException {

        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.frozenTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.coldTempOrder);
        this.service.allocateOrderInAppropriateShelf(this.hotTempOrder);

        assertEquals(1, this.service.getOverflowShelf().size(), "");
    }
}
