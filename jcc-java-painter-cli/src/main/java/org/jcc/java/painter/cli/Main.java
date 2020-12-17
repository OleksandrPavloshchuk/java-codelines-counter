package org.jcc.java.painter.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.jcc.items.ParsedTextItem;
import org.jcc.java.painter.output.HtmlPrinter;
import org.jcc.parsers.CharsProcessor;
import org.jcc.parsers.impl.java.JavaParsedTextItemsFactory;
import org.jcc.parsers.impl.java.JavaParserState;

// Here is comment:
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("no input file");
        } else {
            final File file = new File(args[0]);
            if (!isJavaSource(file)) {
                System.err.println( "'" + file.getCanonicalPath() + "' is not Java source");
            } else if ( file.isDirectory() ) {
                System.err.println( "'" + file.getCanonicalPath() + "' is directory");                
            } else {
                final JavaParsedTextItemsFactory parsedTextItemFactory = new JavaParsedTextItemsFactory();
                try (final InputStream inputStream = new FileInputStream(file)) {
                    new CharsProcessor(JavaParserState.CODE_BLANK, Arrays.asList(parsedTextItemFactory)).loopByChars(inputStream);
                }
                final List<ParsedTextItem> items = parsedTextItemFactory.getItems();
                
                new HtmlPrinter(System.out).print(items);
            }
        }
    }

    private static boolean isJavaSource(File file) throws IOException {
        return file.getCanonicalPath().endsWith(".java");
    }
}
