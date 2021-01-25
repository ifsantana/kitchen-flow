package br.com.italo.santana.challenge.prompt.util;

/**
 *  Utility class for generating random Integers.
 *
 * @author italosantana
 */
public final class RandomUtil {

    public static int getRandomNumberUsingNextInt(int min, int max) {
        int sleepTime = (int)(Math.random() * (max - min + 1) + min);
        return sleepTime;
    }
}
