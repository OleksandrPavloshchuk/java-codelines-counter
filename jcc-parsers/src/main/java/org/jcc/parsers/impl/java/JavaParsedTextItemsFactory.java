package org.jcc.parsers.impl.java;

import org.jcc.items.ParsedTextItemsFactory;
import org.jcc.parsers.NextParserStateEvent;
import org.jcc.parsers.ParserState;

/**
 * Parsed items factory for java
 */
public class JavaParsedTextItemsFactory extends ParsedTextItemsFactory {

    public JavaParsedTextItemsFactory() {
        super(new JavaLanguageInfo());
    }

    @Override
    public void accept(NextParserStateEvent event) {
        final int c = event.getChr();
        final JavaParserState oldParserState = JavaParserState.class.cast(event.getOldParserState());
        if (oldParserState == JavaParserState.BLOCK_COMMENT_END) {
            append(c);
            addComment();
            return;
        }
        final ParserState newParserState = event.getNewParserState();
        if (newParserState == JavaParserState.CODE_LITERAL && isDelimiter(c)) {
            if (oldParserState == JavaParserState.CODE_LITERAL) {
                addItem();
            } else {
                addBlank();
            }
            append(c);
            addDelimiter();
            return;
        }
        if (oldParserState == JavaParserState.CODE_BLANK
                && newParserState != JavaParserState.CODE_BLANK) {
            addBlank();
        } else if (newParserState == JavaParserState.CODE_BLANK) {
            switch (oldParserState) {
                case LINE_COMMENT:
                    addComment();
                    break;
                case STRING:
                case PROTECTED_CHAR_IN_STRING:
                    append(c);
                    addString();
                    return;
                case CHAR:
                case PROTECTED_CHAR_IN_CHAR:
                    append(c);
                    addChar();
                    return;
                case CODE_LITERAL:
                    addItem();
            }
        }
        append(c);
    }

    private void addItem() {
        if (isKeyword()) {
            addKeyword();
        } else if (isAnnotation()) {
            addMeta();
        } else if (isNumber()) {
            addNumber();
        } else {
            addIdentifier();
        }
    }

    private boolean isAnnotation() {
        return '@' == getFirstChar();
    }

    private boolean isNumber() {
        final char c = getFirstChar();
        return '0' <= c && '9' >= c;
    }

}
