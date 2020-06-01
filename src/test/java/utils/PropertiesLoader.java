package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties getProperties() {
        return prop;
    }

    public static void setProp(Properties prop) {
        PropertiesLoader.prop = prop;
    }

    public static void setProperty(String key, String value) throws IOException {
        if (getProperty(key) != null) {
            getProperties().setProperty(key, value);
        }
    }

    static Properties prop, prop2, merged;

    public static String getProperty(String key) {
        if (getProperties() == null) {
            loadPropertyFile();
        }

        return merged.getProperty(key);
    }

    private static void loadPropertyFile() {
        prop = new Properties();
        prop2 = new Properties();
        merged = new Properties();
        InputStream input = null;

        try {
            input = PropertiesLoader.class.getClassLoader().getResourceAsStream("framework.properties");
            prop2.load(input);
            merged.putAll(prop2);
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (System.getProperty("testRunEnvironment")) {
            case "test":
                input = PropertiesLoader.class.getClassLoader().getResourceAsStream("test.properties");
                break;
            case "qa":
                input = PropertiesLoader.class.getClassLoader().getResourceAsStream("qa.properties");
                break;
        }
        try {
            prop.load(input);
            merged.putAll(prop);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
