package org.jcc.java.code.line.counter.output;

import java.io.PrintStream;
import org.jcc.java.code.line.counter.model.CountedLines;

public interface Printer {
    void print(CountedLines countedLines, PrintStream ps);
}
