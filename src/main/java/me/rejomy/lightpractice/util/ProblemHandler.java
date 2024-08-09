package me.rejomy.lightpractice.util;

import lombok.experimental.UtilityClass;
import me.rejomy.lightpractice.PracticeAPI;

import java.io.*;

@UtilityClass
public class ProblemHandler {
    public void handleException(Exception exception) {
        Logger.error("An error occurred during operation.");
        Logger.error("See the file errors.txt in the " +
                PracticeAPI.INSTANCE.getPlugin().getDataFolder().getName() + " folder.");

        File file = FileUtil.createFile("errors.txt");

        writeStackTraceToFile(file, exception);
    }

    private void writeStackTraceToFile(File file, Exception exception) {
        // Creating a line from stacktrace.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String stackTrace = sw.toString();

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            // Write exception stack trace to file.
            fos.write(stackTrace.getBytes());

            // Add an empty line for split errors.
            fos.write(System.lineSeparator().getBytes());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
