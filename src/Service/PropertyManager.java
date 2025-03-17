package Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class PropertyManager {
    private static final Properties prop = new Properties();

    public static void loadProps() {
        try {
            prop.load(new FileInputStream("src/config.properties"));
        } catch (IOException e) {
            System.err.println("Error opening properties file: " + e.getMessage());
            System.err.println("Current working dir: " + System.getProperty("user.dir"));
        }
    }

    public static int getProp(String key) {
        return Integer.parseInt(prop.getProperty(key));
    }
}
