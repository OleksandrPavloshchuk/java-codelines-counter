package tests.java.code.line.counter;

import java.io.IOException;
import tests.java.code.line.counter.builder.CounterFactory;
import tests.java.code.line.counter.builder.Counter;

public class Main {
  
    public static void main(String[] args) throws IOException {
        // By default print from the current directory:
        final String path = args.length>=1 ? args[0] : ".";
        
        // String path = "/home/oleksandr/workspaces/java/tests/java-codelines-counter/src/test/resources/samples/Calculator.java";
        
        final Counter counter = CounterFactory.build(path);
        if (counter == null) {
            System.out.println("File '" + path + "' is not java source file");
        } else {
            new Printer().print(counter.count(), System.out);            
        }
    }
}
