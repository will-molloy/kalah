package kalah.util;

public class StringFormatter {

    public static String repeatString(String value, int times) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        while (count++ < times) {
            builder.append(value);
        }
        return builder.toString();
    }

    public static String rightAlignInteger(int value, int numCharacters) {
        StringBuilder builder = new StringBuilder(value + "");
        while (builder.length() < numCharacters) {
            builder.insert(0, " ");
        }
        return builder.toString();
    }
}
