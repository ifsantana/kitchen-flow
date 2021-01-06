package br.com.italo.santana.challenge.prompt.util;

import static  org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtilTests {

    @Test
    public void shouldReturnsSixtySecondsAge() {

        LocalTime baseTime = LocalTime.now();

        LocalDateTime start =  LocalDateTime.of(LocalDate.now(), baseTime);

        LocalDateTime end =  LocalDateTime.of(LocalDate.now(), baseTime.plusSeconds(60));

        assertEquals(60, DateTimeUtil.calculateAgeInSeconds(start, end), "the result must be 60s");
    }
}
