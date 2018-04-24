package kalah.util;

public class MathUtil {

    public static int getDigitLength(int value) {
        return (int) Math.ceil(Math.log10(value));
    }
}
