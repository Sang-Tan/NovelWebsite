package core.config;

import java.util.Properties;

public class ApplicationPropertiesReader {

    private static final class InstanceHolder {
        private static final ApplicationPropertiesReader instance = new ApplicationPropertiesReader();
    }

    public static ApplicationPropertiesReader getInstance() {
        return InstanceHolder.instance;
    }

    private final Properties properties;

    private ApplicationPropertiesReader() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            this.properties = properties;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }
}
