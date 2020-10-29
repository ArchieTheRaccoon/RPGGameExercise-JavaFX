package engine;

import java.util.Random;

public class RandomNumberGenerator {
    private static Random generator = new Random();

    public static int numberBetween(int minimumValue, int maximumValue) {
        return generator.nextInt(maximumValue + 1 - minimumValue) + minimumValue;
    }
}
