package org.jcc.parsers.impl.java;

import java.util.Arrays;
import java.util.Collection;
import org.jcc.parsers.LanguageInfo;

/**
 * Java language info
 *
 */
public class JavaLanguageInfo implements LanguageInfo {

    private static final Collection<Integer> DELIMITERS = Arrays.asList(
            (int) '.', (int) ';', (int) '/', (int) '*',
            (int) ':', (int) '-', (int) ',', (int) '+',
            (int) '{', (int) '}', (int) '(', (int) ')',
            (int) '[', (int) ']', (int) '<', (int) '>',
            (int) '=', (int) '~', (int) '!', (int) '&',
            (int) '^', (int) '%'
    );

    private static final Collection<String> KEYWORDS = Arrays.asList(
            "class", "interface", "extends", "implements",
            "private", "protected", "public", "final", "abstract",
            "static", "default", "super", "this", "import",
            "boolean", "int", "float", "double", "long", "short",
            "char", "byte", "package", "synchronized",
            "volatile", "if", "for", "while", "do", "switch",
            "case", "new", "break", "continue", "goto"
    );

    @Override
    public Collection<Integer> getDelimiters() {
        return DELIMITERS;
    }

    @Override
    public Collection<String> getKeywords() {
        return KEYWORDS;
    }

}
