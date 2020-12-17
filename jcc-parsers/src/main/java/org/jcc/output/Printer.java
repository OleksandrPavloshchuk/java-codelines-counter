package org.jcc.output;

import java.io.PrintStream;

public interface Printer<T> {
    void print(T data, PrintStream ps);
}
