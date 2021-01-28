package br.com.italo.santana.challenge.prompt.util;

import static  org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtilTests {

    @Test
    public void shouldReturnsSixtySecondsAge() {

        LocalDateTime start =  LocalDateTime.of(LocalDate.of(2021, 01, 27), LocalTime.of(8, 0, 0));

        LocalDateTime end =  LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0, 0));

        assertEquals(86400, DateTimeUtil.calculateAgeInSeconds(start, end), "the result must be 86400s");
    }
}
