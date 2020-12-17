package org.jcc.java.painter.output;

import java.io.PrintStream;
import java.util.List;
import org.jcc.items.ParsedTextItem;

public class HtmlPrinter {

    private enum HtmlClass {
        BLANK("black"),
        COMMENT("gray"),
        STRING("#330099"),
        CHAR("#990099"),
        KEYWORD("#009900"),
        DELIMITER("black"),
        IDENTIFIER("#006699");
        private final String color;

        private HtmlClass(String color) {
            this.color = color;
        }
    }

    private final PrintStream ps;

    public HtmlPrinter(PrintStream ps) {
        this.ps = ps;
    }

    public void print(List<ParsedTextItem> items) {
        printHeader();
        items.forEach(item -> printItem(item));
        printFooter();
    }

    private void printHeader() {
        ps.print("<html><head><style type=\"text/css\">");
        for( final HtmlClass htmlClass : HtmlClass.values()) {
            ps.print( ".");
            ps.print(htmlClass.name());
            ps.print( " { color:");
            ps.print( htmlClass.color);
            ps.println( ";}");
        }
        ps.println("</style></head><body>");
    }

    private void printItem(ParsedTextItem item) {
        ps.print("<span class=\"");
        ps.print( item.getType().name());
        ps.print( "\">");
        ps.print( prepare(item.getText()));
        ps.print( "</span>");
    }

    private void printFooter() {
        ps.print("</body></html>");
    }
    
    private static String prepare(String src) {
        return src.replace("<", "&lt;")
                .replace("\n", "<br/>")
                .replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
                .replace(" ", "&nbsp;");
    }
}
