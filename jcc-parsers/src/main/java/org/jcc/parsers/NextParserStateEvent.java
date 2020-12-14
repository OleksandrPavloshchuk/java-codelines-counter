package org.jcc.parsers;

public class NextParserStateEvent {

    private final int chr;
    private final ParserState oldParserState;

    public NextParserStateEvent(int chr, ParserState oldParserState) {
        this.chr = chr;
        this.oldParserState = oldParserState;
    }

    public int getChr() {
        return chr;
    }

    public ParserState getOldParserState() {
        return oldParserState;
    }

}
