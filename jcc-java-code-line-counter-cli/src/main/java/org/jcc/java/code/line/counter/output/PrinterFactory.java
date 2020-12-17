package org.jcc.java.code.line.counter.output;

public class PrinterFactory {

    public static CountedLinesPrinter build(String[] args) {
        if (contains(args, "txt")) {
            return new SimplePrinter();
        } else if (contains(args, "xml")) {
            return new XmlPrinter();
        }
        return new SimplePrinter();
    }

    private static boolean contains(String[] args, String match) {
        return args.length>1 && args[1].equals(match);
    }

}
