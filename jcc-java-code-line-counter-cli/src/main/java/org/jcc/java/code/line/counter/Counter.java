package org.jcc.java.code.line.counter;

import java.io.IOException;
import org.jcc.java.code.line.counter.model.CountedLines;

/**
 * Count number of lines of java code in file or folder
 */
public interface Counter {
    CountedLines count() throws IOException;
}