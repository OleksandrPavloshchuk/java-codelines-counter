package org.jcc.items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.jcc.parsers.LanguageInfo;
import org.jcc.parsers.NextParserStateEvent;
import org.jcc.parsers.ParserState;

/**
 * Consumes lines and generates stream of parsed text items
 * @param <T> parser state implementation
 */
public abstract class ParsedTextItemsFactory<T extends ParserState> implements Consumer<NextParserStateEvent> {

    private final LanguageInfo languageInfo;
    protected final T parserState;

    private final List<ParsedTextItem> items = new ArrayList<>();
    private final StringBuilder accumulator = new StringBuilder(16);

    public ParsedTextItemsFactory(LanguageInfo languageInfo, T parserState) {
        this.languageInfo = languageInfo;
        this.parserState = parserState;
    }

    public List<ParsedTextItem> getItems() {
        return items;
    }
    
    protected StringBuilder getAccumulator() {
        return accumulator;
    }
    
    protected LanguageInfo getLanguageInfo() {
        return languageInfo;
    }
    
    protected void addBlank() {
        addNewItem(ParsedTextItem.Type.BLANK);
    }
        
    protected void addComment() {
        addNewItem(ParsedTextItem.Type.COMMENT);
    }
    
    protected void addChar() {
        addNewItem(ParsedTextItem.Type.CHAR);
    }
    
    protected void addDelimiter() {
        addNewItem(ParsedTextItem.Type.DELIMITER);
    }
    
    protected void addIdentifier() {
        addNewItem(ParsedTextItem.Type.IDENTIFIER);
    }
    
    protected void addKeyword() {
        addNewItem(ParsedTextItem.Type.KEYWORD);
    }
    
    protected void addString() {
        addNewItem(ParsedTextItem.Type.STRING);
    }
    
    private void addNewItem(ParsedTextItem.Type type) {
        items.add( new ParsedTextItem(type, accumulator.toString()));
        accumulator.setLength(0);
    }
}
