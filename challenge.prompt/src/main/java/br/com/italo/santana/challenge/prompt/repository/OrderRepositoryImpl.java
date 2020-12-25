package br.com.italo.santana.challenge.prompt.repository;

import br.com.italo.santana.challenge.prompt.domain.Order;
import br.com.italo.santana.challenge.prompt.interfaces.orders.OrderRepository;
import br.com.italo.santana.challenge.prompt.util.JsonParserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class OrderRepositoryImpl implements OrderRepository {
    private final String FILE_BASE_PATH = "/challenge.prompt/src/main/resources/orders/";
    private final String FILE_NAME = "orders.json";
    private Gson gson;

    @Autowired
    public OrderRepositoryImpl() {
        this.gson = new Gson();
    }

    @Override
    public List<Order> getAllOrders() throws IOException {

        String userDirectory = new File("").getAbsolutePath();

        Reader reader = Files.newBufferedReader(Paths.get(userDirectory + FILE_BASE_PATH + FILE_NAME));

        return JsonParserUtil.fromReaderToEntityList(reader, new TypeToken<List<Order>>(){}.getType());
    }
}
