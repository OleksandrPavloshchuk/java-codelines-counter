package org.jcc.java.code.line.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import org.jcc.java.code.line.counter.model.CountedLines;
import org.jcc.java.code.line.counter.model.FileCountedLines;
import org.jcc.parsers.NextParserStateEvent;
import org.jcc.parsers.ParserState;
import org.jcc.parsers.impl.JavaParserState;

/**
 * Counter implementation for file.
 *
 * Read lines of file and counts java code lines.
 */
class FileCounter extends CounterBase {

    // State of current string parser:
    private ParserState parserState = JavaParserState.CODE_BLANK;
    // Is current line contains code?
    private boolean lineContainsCode;
    // Counter
    private int count = 0;
    // Parse state change listeners
    private final Collection<Consumer<NextParserStateEvent>> listeners
            = Arrays.asList(getListener());

    public FileCounter(File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    public CountedLines count() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines()
                    .forEach(line -> parseLine(line.trim()));
        }
        final FileCountedLines result = new FileCountedLines(file.getName());
        result.setCount(count);
        return result;
    }

    /**
     * Parse line of source and detect java code
     */
    private void parseLine(String line) {

        // Set parser state for start of line
        parserState = parserState.nextOnNewLine(listeners);
        lineContainsCode = false;
        line.chars().forEach(c -> {
            // Switch parser state and conditionally set 'contains code' indicator
            parserState = parserState.next(c, listeners);
        });
        if (lineContainsCode) {
            // Code is detected in this line
            count++;
        }
    }

    private Consumer<NextParserStateEvent> getListener() {
        return event -> {
            final ParserState oldState = event.getOldParserState();
            final int chr = event.getChr();
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
}
