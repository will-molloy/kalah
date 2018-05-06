package kalah.setting;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class GameSetting {
	public static int houseNum;
	public static int beginingSeeds;
	public static int beginingPlayer;
	private static String DEFAULT_HOUSES_NUMBER = "6";
	private static String DEFAULT_BEGINING_SEADS = "4";
	private static String DEFAULT_BEGINGING_PLAYER = "1";
	public static void loadConfig() {
		Properties properties = new Properties();
        InputStream input;
        try {
            input = new FileInputStream("config.properties");
            properties.load(input);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameSetting.beginingSeeds = Integer.parseInt(properties.getProperty("beginingSeads", DEFAULT_BEGINING_SEADS));
        GameSetting.houseNum = Integer.parseInt(properties.getProperty("houseNumber", DEFAULT_HOUSES_NUMBER));
        GameSetting.beginingPlayer = Integer.parseInt(properties.getProperty("beginingPlayer", DEFAULT_BEGINGING_PLAYER));
	}
}
