package org.jcc.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Char by char processor
 */
public class CharsProcessor {

    private ParserState parserState;
    private final Collection<Consumer<NextParserStateEvent>> listeners;

    public CharsProcessor(ParserState parserState, Collection<Consumer<NextParserStateEvent>> listeners) {
        this.parserState = parserState;
        this.listeners = listeners;
    }

    public void loopByChars(InputStream inputStream) throws IOException {
        int c;
        do {
            c = inputStream.read();
            parserState = parserState.next(c, listeners);
        } while (c != -1);
    }

}
