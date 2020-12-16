package org.jcc.parsers.impl.java;

import org.jcc.items.ParsedTextItemsFactory;
import org.jcc.parsers.NextParserStateEvent;

/**
 * Parsed items factory for java
 */
public class JavaParsedTextItemsFactory extends ParsedTextItemsFactory<JavaParserState> {

    public JavaParsedTextItemsFactory() {
        super(new JavaLanguageInfo(), JavaParserState.CODE_BLANK);
    }

    @Override
    public void accept(NextParserStateEvent event) {
        // TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
