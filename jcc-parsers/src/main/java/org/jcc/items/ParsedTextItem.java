package org.jcc.items;

/**
 * Item of parsed text
 */
public class ParsedTextItem {

    public enum Type {
        BLANK, COMMENT, STRING, CHAR, KEYWORD, DELIMITER, IDENTIFIER, NUMBER, META
    }

    private final String text;
    private final Type type;

    public ParsedTextItem(Type type, String text) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public Type getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "{" + type.name() + ": '" + text + "'}";
    }
         
}
