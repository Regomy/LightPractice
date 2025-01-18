package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtil {
    public float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException exception) {
            Logger.warn("Error when parsing " + value + " to float.");
            Logger.warn("Value -1 will be returned.");
        }

        return -1;
    }
}
