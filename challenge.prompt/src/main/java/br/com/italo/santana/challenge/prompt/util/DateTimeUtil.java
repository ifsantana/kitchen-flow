package br.com.italo.santana.challenge.prompt.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeUtil {
    static final int SECONDS_PER_MINUTE = 60;

    public static long calculateAgeInSeconds(LocalDateTime dob, LocalDateTime now) {

        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long secs = (seconds % SECONDS_PER_MINUTE);

        return secs;
    }
}
