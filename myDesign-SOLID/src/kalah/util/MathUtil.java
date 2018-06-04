package kalah.util;

import static java.lang.Math.*;

public class MathUtil {

    public static int digitLength(int value) {
        return value == 0 ? 1 : (int) log10(abs(value)) + 1;
    }

    public static boolean isSameParity(int a, int b) {
        return (a & 1) == (b & 1);
    }

    public static int incrementAndWrap(int value, int max) {
        return value % max + 1;
    }

}
