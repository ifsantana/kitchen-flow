package br.com.italo.santana.challenge.prompt.util;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Utility class for date and time functions.
 *
 * @author italosantana
 */
public class DateTimeUtil {

    public static long calculateAgeInSeconds(LocalDateTime startDatetime, LocalDateTime now) {

        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), startDatetime.getHour(), startDatetime.getMinute(), startDatetime.getSecond());

        Duration duration = Duration.between(today, now);

        return duration.getSeconds();
    }
}
