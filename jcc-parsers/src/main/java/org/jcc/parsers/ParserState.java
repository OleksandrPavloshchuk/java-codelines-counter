package org.jcc.parsers;

import java.util.Collection;
import java.util.function.Consumer;

public interface ParserState {

    ParserState next(int c);

    default ParserState next(int c, Collection<Consumer<NextParserStateEvent>> eventListerens) {
        final ParserState newState = next(c);
        EventListenerUtility.fireEvent(new NextParserStateEvent(c, this, newState), eventListerens);
        return newState;
    }

    default ParserState nextOnNewLine(Collection<Consumer<NextParserStateEvent>> eventListerens) {
        final ParserState newState = nextOnNewLine();
        EventListenerUtility.fireEvent(new NextParserStateEvent('\n', this, newState), eventListerens);
        return newState;
    }

    default ParserState nextOnNewLine() {
        return this;
    }
}
