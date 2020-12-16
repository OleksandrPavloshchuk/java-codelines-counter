package org.jcc.java.code.line.counter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import org.jcc.java.code.line.counter.model.CountedLines;
import org.jcc.java.code.line.counter.model.FileCountedLines;
import org.jcc.parsers.CharsProcessor;
import org.jcc.parsers.NextParserStateEvent;
import org.jcc.parsers.ParserState;
import org.jcc.parsers.impl.java.JavaParserState;

/**
 * Counter implementation for file.
 *
 * Read lines of file and counts java code lines.
 */
class FileCounter extends CounterBase {

    // Is current line contains code?
    private boolean lineContainsCode = false;
    // Counter
    private int count = 0;
    // Parse state change listeners
    private final Collection<Consumer<NextParserStateEvent>> listeners
            = Arrays.asList(getListener());

    FileCounter(File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    public CountedLines count() throws IOException {
        try (final InputStream inputStream = new FileInputStream(file)) {
            new CharsProcessor(JavaParserState.CODE_BLANK, listeners).loopByChars(inputStream);
        }
        final FileCountedLines result = new FileCountedLines(file.getName());
        result.setCount(count);
        return result;
    }

    private Consumer<NextParserStateEvent> getListener() {
        return event -> {
            final int chr = event.getChr();
            if (isEndOfLine(chr)) {
                if (lineContainsCode) {
                    count++;
                }
                // new line
                lineContainsCode = false;
                return;
            }

            final ParserState oldState = event.getOldParserState();
            if (oldState == JavaParserState.CODE_BLANK) {
                switch (chr) {
                    case '/':
                    case ' ':
                    case '\t':
                        return;
                    default:
                        lineContainsCode = true;
                }
            } else if (oldState == JavaParserState.COMMENT_START) {
                switch (chr) {
                    case '/':
                    case '*':
                        return;
                    default:
                        lineContainsCode = true;
                }
            }

        };
    }

    private static boolean isEndOfLine(int c) {
        return '\n' == c || -1 == c;
    }
}
