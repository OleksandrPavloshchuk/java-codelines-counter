package org.jcc.parsers;

import java.util.Collection;
import java.util.function.Consumer;

public interface ParserState {

    ParserState next(int c);

    default ParserState next(int c, Collection<Consumer<NextParserStateEvent>> eventListerens) {
        final ParserState newState = next(c);
        final NextParserStateEvent event = new NextParserStateEvent(c, this, newState);
        eventListerens.forEach(l -> l.accept(event));
        return newState;
    }  

}
