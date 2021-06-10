package by.du.util;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public final class Props {

    public static final Properties PROPERTIES;

    static {
        final Properties properties = new Properties();
        try {
            properties.load(Props.class.getClassLoader().getResourceAsStream("app.properties"));
            PROPERTIES = properties;
        } catch (IOException e) {
            throw new IllegalStateException("error loading app.properties");
        }
    }

    public static Optional<String> getValue(String key) {
        return Optional.ofNullable(PROPERTIES.getProperty(key));
    }
}
