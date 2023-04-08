package core.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicLogger {
    private Logger logger;
    private static BasicLogger instance;

    public static BasicLogger getInstance() {
        if (instance == null) {
            instance = new BasicLogger();
        }
        return instance;
    }

    private BasicLogger() {
        logger = Logger.getLogger("MainLogger");
        logger.setLevel(Level.ALL);
    }

    public Logger getLogger() {
        return logger;
    }

    public void printStackTrace(Exception e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
    }

}
