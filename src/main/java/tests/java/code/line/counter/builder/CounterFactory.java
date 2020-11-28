package tests.java.code.line.counter.builder;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Builds counters for files or directories
 */
public class CounterFactory {

    /**
     * Open filePath and return the counter. 
     * If file is not java source, then return null.
     *
     * @param filePath
     * @return
     * @throws java.io.IOException
     */
    public static Counter build(String filePath) throws IOException {
        final File file = new File(Objects.requireNonNull(filePath, "file path can not be null"));
        if (file.isDirectory()) {
            return buildDirCounter(file);
        } else {
            return buildFileCounter(file);
        }
    }

    private static Counter buildDirCounter(File file) {
        return new DirCounter(file);
    }

    private static Counter buildFileCounter(File file) throws IOException {
        return isJavaSource(file) ? new FileCounter(file) : null;
    }

    private static boolean isJavaSource(File file) throws IOException {
        return file.getPath().endsWith(".java");
    }

}
