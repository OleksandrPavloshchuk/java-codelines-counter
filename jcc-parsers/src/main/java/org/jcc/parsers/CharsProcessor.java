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

    public void loopByBytes(InputStream inputStream) throws IOException {
        while (true) {
            int c = inputStream.read();
            if (-1 == c) {
                parserState.next(c, listeners);
                return;
            }
            parserState = parserState.next(c, listeners);
        }
    }

}
