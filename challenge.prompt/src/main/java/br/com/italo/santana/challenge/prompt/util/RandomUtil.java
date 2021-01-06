package br.com.italo.santana.challenge.prompt.util;

import java.util.Random;


/**
 *  Utility class for generating random Integers.
 *
 * @author italosantana
 */
public final class RandomUtil {

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        int sleepTime = random.nextInt(max - min) + min;
        return sleepTime;
    }
}
