package ashlified.util;

import java.util.Random;

/**
 * Created by karkoon on 13.05.17.
 */
public class RandomNumber {

    private static Random random = new Random();

    private RandomNumber() {}

    public static long nextLong() {
        return random.nextLong();
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    public static float nextFloat() {
        return random.nextFloat();
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static int nextInt() {
        return random.nextInt();
    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
