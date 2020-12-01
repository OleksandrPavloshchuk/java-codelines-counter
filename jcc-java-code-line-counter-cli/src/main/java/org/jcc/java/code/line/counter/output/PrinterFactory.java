package org.jcc.java.code.line.counter.output;

import java.util.stream.Stream;

public class PrinterFactory {

    public static Printer build(String[] args) {
        final Stream<String> argsStream = Stream.of(args);
        if( contains(argsStream, "txt")) {
            return new SimplePrinter();
        }
        return new SimplePrinter();
    }

    private static boolean contains(Stream<String> ss, String match) {
        return ss.anyMatch(s -> match.equalsIgnoreCase(s));
    }

}
