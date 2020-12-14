package org.jcc.lines;

import java.util.Collection;
import java.util.function.Consumer;
import org.jcc.parsers.NextParserStateEvent;
import org.jcc.parsers.ParserState;

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
    
    public ParserState loopByChars(String line) {
        parserState = parserState.nextOnNewLine(listeners);
        line.chars().forEach(c -> {            
            parserState = parserState.next(c, listeners);
        });
        return parserState;
    }
}
