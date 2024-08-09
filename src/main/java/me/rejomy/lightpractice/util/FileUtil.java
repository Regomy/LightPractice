package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;
import me.rejomy.lightpractice.PracticeAPI;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileUtil {
    public void createFile(File file) {
        Logger.info("Creating " + file.getName() + " to " + file.getParentFile().getName() + " folder.");

        try {
            // Create a parent directory if it is not exists.
            file.getParentFile().mkdirs();

            file.createNewFile();
        } catch (IOException e) {
            ProblemHandler.handleException(e);
        }
    }

    public File createFile(String name) {
        File file = new File(PracticeAPI.INSTANCE.getPlugin().getDataFolder(), name);
        createFile(file);

        return file;
    }

    public void createFileFromJar(String name) {
        PracticeAPI.INSTANCE.getPlugin().saveResource(name, false);
    }
}
