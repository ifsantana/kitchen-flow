package br.com.italo.santana.challenge.prompt.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.italo.santana.challenge.prompt.configs.AppProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = AppProperties.class)
@TestPropertySource("classpath:application.properties")
public class RandomUtilTests {

    @Autowired
    private AppProperties appProperties;

    @Test
    public void shouldReturnsIntegerBetweenTwoAndSix() {
        assertTrue(RandomUtil.getRandomNumberUsingNextInt(this.appProperties.getCourierMinArriveTime(),
                this.appProperties.getCourierMaxArriveTime()) >= this.appProperties.getCourierMinArriveTime());

        assertTrue(RandomUtil.getRandomNumberUsingNextInt(this.appProperties.getCourierMinArriveTime(),
                this.appProperties.getCourierMaxArriveTime()) <= this.appProperties.getCourierMaxArriveTime());
    }
}
