package tests.java.code.line.counter.builder;

import java.io.IOException;
import tests.java.code.line.counter.model.CountedLines;

/**
 * Count number of lines of java code in file or folder
 */
public interface Counter {
    CountedLines count() throws IOException;
}
