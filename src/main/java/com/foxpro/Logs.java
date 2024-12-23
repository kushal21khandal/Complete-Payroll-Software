package com.foxpro;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logs {

    public static final Logger logger = Logger.getLogger("autoWork");
    private FileHandler logFileHandler = null;

    Logs() {
        try {
            logFileHandler = new FileHandler(Config.getPathLogFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        logFileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(logFileHandler);

    }

    public void closeLogFileHandler() {
        if (logFileHandler != null) {
            logFileHandler.close();
        }
    }
}
