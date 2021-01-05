package br.com.italo.santana.challenge.prompt.repositories;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
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

/**
 * Repository for manager the {@link Order} entity.
 *
 * @author italosantana
 */
@Service
public class OrderRepositoryImpl implements OrderRepository {
    private AppProperties appProperties;
    private Gson gson;

    @Autowired
    public OrderRepositoryImpl(AppProperties appProperties) {
        this.gson = new Gson();
        this.appProperties = appProperties;
    }

    @Override
    public List<Order> getAllOrders() throws IOException {

        String userDirectory = new File("").getAbsolutePath();

        Reader reader = Files.newBufferedReader(Paths.get(new StringBuilder(userDirectory)
                .append(this.appProperties.getFileBasePath())
                .append(this.appProperties.getFilename()).toString()));

        return JsonParserUtil.fromReaderToEntityList(reader, new TypeToken<List<Order>>(){}.getType());
    }
}
