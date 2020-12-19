package org.jcc.parsers;

public class NextParserStateEvent {

    private final int chr;
    private final ParserState oldParserState;
    private final ParserState newParserState;

    public NextParserStateEvent(int chr, ParserState oldParserState, ParserState newParserState) {
        this.chr = chr;
        this.oldParserState = oldParserState;
        this.newParserState = newParserState;
    }

    public int getChr() {
        return chr;
    }

    public ParserState getOldParserState() {
        return oldParserState;
    }

    public ParserState getNewParserState() {
        return newParserState;
    }
}
