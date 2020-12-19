package org.jcc.java.painter.output;

import java.io.PrintStream;
import java.util.List;
import org.jcc.items.ParsedTextItem;

class ColoredConsolePrinter implements ParsedTextItemsPrinter {

    private enum Color {
        BLANK("30"),
        COMMENT("30"),
        STRING("36"),
        CHAR("35"),
        KEYWORD("33"),
        DELIMITER("37"),
        NUMBER("32"),
        META("34"),
        IDENTIFIER("39");
        private final String index;

        private Color(String index) {
            this.index = index;
        }

        private static String getIndex(ParsedTextItem item) {
            return Color.valueOf(item.getType().name()).index;
        }
    }

    private PrintStream ps;

    @Override
    public void print(List<ParsedTextItem> items, PrintStream ps) {
        this.ps = ps;
        items.forEach(item -> printItem(item));
    }

    private void printItem(ParsedTextItem item) {
        ps.printf("\u001b[%sm%s\u001b[38m", Color.getIndex(item), item.getText());
    }

}
