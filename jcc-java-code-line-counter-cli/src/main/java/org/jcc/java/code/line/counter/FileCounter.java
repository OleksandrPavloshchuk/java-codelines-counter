package org.jcc.java.code.line.counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import org.jcc.java.code.line.counter.model.CountedLines;
import org.jcc.java.code.line.counter.model.FileCountedLines;
import org.jcc.lines.CharsProcessor;
import org.jcc.lines.LinesProcessor;
import org.jcc.parsers.NextParserStateEvent;
import org.jcc.parsers.ParserState;
import org.jcc.parsers.impl.java.JavaParserState;

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

    FileCounter(File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    public CountedLines count() throws IOException {
        LinesProcessor.loopByLines(file, this::parseLine);
        final FileCountedLines result = new FileCountedLines(file.getName());
        result.setCount(count);
        return result;
    }

    /**
     * Parse line of source and detect java code
     */
    private void parseLine(String line) {
        lineContainsCode = false;
        parserState = new CharsProcessor(parserState, listeners).loopByChars(line);        
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
                    case '\n':
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
