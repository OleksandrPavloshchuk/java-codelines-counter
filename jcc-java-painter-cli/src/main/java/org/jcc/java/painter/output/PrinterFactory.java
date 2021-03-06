package org.jcc.java.painter.output;

public class PrinterFactory {

    public static ParsedTextItemsPrinter build(String[] args) {
        if (contains(args, "--html")) {
            return new HtmlPrinter();
        } else {
            return new ColoredConsolePrinter();
        }
    }

    private static boolean contains(String[] args, String match) {
        return args.length>1 && args[1].equals(match);
    }

}
