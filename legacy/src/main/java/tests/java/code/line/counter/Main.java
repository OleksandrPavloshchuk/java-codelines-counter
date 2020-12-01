package tests.java.code.line.counter;

import java.io.IOException;
import tests.java.code.line.counter.builder.CounterFactory;
import tests.java.code.line.counter.builder.Counter;

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
            new Printer().print(counter.count(), System.out);            
        }
    }
}
