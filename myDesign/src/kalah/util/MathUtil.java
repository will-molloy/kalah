package kalah.util;

import static java.lang.Math.*;

/**
 * Contains utility methods for mathematical functions.
 */
public class MathUtil {

    public static int getDigitLength(int value) {
        return value == 0 ? 1 : (int) log10(abs(value)) + 1;
    }

    public static boolean isSameParity(int a, int b) {
        return (a & 1) == (b & 1);
    }

}
