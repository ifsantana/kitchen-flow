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

        Duration duration = Duration.between(startDatetime, now);

        return duration.getSeconds();
    }
}
