package org.jcc.items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.jcc.parsers.LanguageInfo;
import org.jcc.parsers.NextParserStateEvent;

/**
 * Consumes lines and generates stream of parsed text items
 */
public abstract class ParsedTextItemsFactory implements Consumer<NextParserStateEvent> {

    private final LanguageInfo languageInfo;

    private final List<ParsedTextItem> items = new ArrayList<>();
    private final StringBuilder accumulator = new StringBuilder(16);

    public ParsedTextItemsFactory(LanguageInfo languageInfo) {
        this.languageInfo = languageInfo;
    }

    public List<ParsedTextItem> getItems() {
        return items;
    }

    protected boolean isKeyword() {
        return languageInfo.getKeywords().contains(accumulator.toString());
    }

    protected void append(int c) {
        if (c > 0) {
            accumulator.append((char) c);
        }
    }
    
    protected char getFirstChar() {
        return accumulator.length()>0 ? accumulator.charAt(0) : 0;
    }

    protected boolean isDelimiter(int c) {
        return languageInfo.getDelimiters().contains(c);
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

    protected void addNumber() {
        addNewItem(ParsedTextItem.Type.NUMBER);
    }  
    
    protected void addMeta() {
        addNewItem(ParsedTextItem.Type.META);
    }       
    
    private void addNewItem(ParsedTextItem.Type type) {
        items.add(new ParsedTextItem(type, accumulator.toString()));
        accumulator.setLength(0);
    }
}
