package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;
import me.rejomy.lightpractice.PracticeAPI;

@UtilityClass
public class Logger {
    private final java.util.logging.Logger LOGGER = PracticeAPI.INSTANCE.getPlugin().getLogger();

    public void info(String text) {
        LOGGER.info(text);
    }

    public void warn(String text) {
        LOGGER.warning(text);
    }

    public void error(String text) {
        LOGGER.severe(text);
    }
}
