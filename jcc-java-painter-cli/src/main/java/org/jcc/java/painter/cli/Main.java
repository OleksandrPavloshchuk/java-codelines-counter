package org.jcc.java.painter.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcc.java.painter.output.HtmlPrinter;
import org.jcc.parsers.CharsProcessor;
import org.jcc.parsers.impl.java.JavaParsedTextItemsFactory;
import org.jcc.parsers.impl.java.JavaParserState;

public class Main {

    public static void main(String[] args) throws IOException {
        final File file = new FileFactory(args).buildFile();
        if (file != null) {
            final JavaParsedTextItemsFactory parsedTextItemFactory = new JavaParsedTextItemsFactory();
            try (final InputStream inputStream = new FileInputStream(file)) {
                new CharsProcessor(JavaParserState.CODE_BLANK, parsedTextItemFactory).loopByChars(inputStream);
            } catch( IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "error reading file " + file.getPath(), ex);
            }
            new HtmlPrinter().print(parsedTextItemFactory.getItems(), System.out);
        }
    }

}
