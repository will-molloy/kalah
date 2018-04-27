package kalah.util;

/**
 * Contains utility methods for mathematical functions.
 */
public class MathUtil {

    public static int getDigitLength(int value) {
        return (int) Math.ceil(Math.log10(value));
    }

    public static boolean isSamePolarity(int a, int b) {
        return a % 2 == 0 && b % 2 == 0 || a % 2 == 1 && b % 2 == 1;
    }
}
