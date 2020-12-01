package org.jcc.java.code.line.counter.cli;

import java.io.IOException;
import org.jcc.java.code.line.counter.Counter;
import org.jcc.java.code.line.counter.CounterFactory;
import org.jcc.java.code.line.counter.output.PrinterFactory;

public class Main {
  
    public static void main(String[] args) throws IOException {

        // By default print from the current directory:
        final String path = args.length>=1 ? args[0] : ".";

        // Build counter for file or directory:
        final Counter counter = CounterFactory.build(path);
        if (counter == null) {
            // If we work with non-java file, counter is null
            System.out.println("File '" + path + "' is not java source file");
        } else {
            // Count lines of code in the tree and print it
            PrinterFactory.build(args).print(counter.count(), System.out);            
        }
    }
}