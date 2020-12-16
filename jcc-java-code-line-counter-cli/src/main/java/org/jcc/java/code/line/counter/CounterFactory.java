package org.jcc.java.code.line.counter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Builds counters for files or directories
 */
public class CounterFactory {

    /**
     * Open filePath and return the counter. If file is not java source, then
     * return null.
     *
     * @param filePath
     * @return
     * @throws java.io.IOException
     */
    public static Counter build(String filePath) throws IOException {
        final File file = new File(Objects.requireNonNull(filePath, "file path can not be null"));
        final Function<File, Counter> builder = file.isDirectory()
                ? CounterFactory::buildDirCounter
                : CounterFactory::buildFileCounter;
        return builder.apply(file);
    }

    private static Counter buildDirCounter(File file) {
        return new DirCounter(file);
    }

    private static Counter buildFileCounter(File file) {
        try {
            return isJavaSource(file) ? new FileCounter(file) : null;
        } catch (IOException ex) {
            Logger.getLogger(CounterFactory.class.getName()).log(Level.SEVERE, "can't build file counter", ex);
            return null;
        }
    }

    private static boolean isJavaSource(File file) throws IOException {
        return file.getPath().endsWith(".java");
    }

}
