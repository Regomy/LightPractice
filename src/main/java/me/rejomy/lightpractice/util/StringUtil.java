package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class StringUtil {
    public String replace(String string, Object... replacers) {
        for (int i = 1; i < replacers.length; i+=2) {
            string = string.replaceAll("\\$" + replacers[i - 1], String.valueOf(replacers[i]));
        }

        return string;
    }

    public List<String> replace(List<String> list, Object... replacers) {
        list.replaceAll(string -> replace(string, replacers));

        return list;
    }

    public String apply(String string, Object... replacers) {
        return ColorUtil.toColor(
                replace(string, replacers));
    }

    public List<String> apply(List<String> list, Object... replacers) {
        list = replace(list, replacers);
        list.replaceAll(ColorUtil::toColor);

        return list;
    }
}
