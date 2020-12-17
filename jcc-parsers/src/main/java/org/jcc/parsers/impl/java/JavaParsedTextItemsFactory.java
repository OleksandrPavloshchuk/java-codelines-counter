package org.jcc.parsers.impl.java;

import org.jcc.items.ParsedTextItemsFactory;
import org.jcc.parsers.NextParserStateEvent;
import org.jcc.parsers.ParserState;

/**
 * Parsed items factory for java
 */
public class JavaParsedTextItemsFactory extends ParsedTextItemsFactory<JavaParserState> {

    public JavaParsedTextItemsFactory() {
        super(new JavaLanguageInfo(), JavaParserState.CODE_BLANK);
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
            if (isKeyword()) {
                addKeyword();
            } else {
                addIdentifier();
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
                    addString();
                    break;
                case CHAR:
                case PROTECTED_CHAR_IN_CHAR:
                    addChar();
                    break;
                case CODE_LITERAL:
                    if (isKeyword()) {
                        addKeyword();
                    } else {
                        addIdentifier();
                    }
            }
        }
        append(c);
    }

}
