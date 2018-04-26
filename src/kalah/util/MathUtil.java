package kalah.util;

/**
 * Contains utility methods for mathematical functions.
 */
public class MathUtil {

    public static int getDigitLength(int value) {
        return (int) Math.ceil(Math.log10(value));
    }
}
