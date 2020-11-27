package tests.java.code.line.counter.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import tests.java.code.line.counter.model.CountedLines;
import tests.java.code.line.counter.model.FileCountedLines;

/**
 * Counter implementation for file. Read lines of file and counts java code
 * lines.
 *
 * @author oleksandr
 */
class FileCounter extends CounterBase {

    // States of the parser
    private enum ParserState {
        CODE,
        COMMENT_MULTILINE,
        COMMENT_ONE_LINE
    }

    // State of current string parser:
    private ParserState parserState = ParserState.CODE;
    // Current string:
    private String str;
    // Counter
    private int count = 0;

    public FileCounter(File file) {
        super(file);
    }

    @Override
    public CountedLines count() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Loop by all lines if file:
            while (true) {
                final String line = reader.readLine();
                if (line == null) {
                    break;
                }
                // Make blank string empty:
                str = line.trim();
                if (!str.isEmpty()) {
                    parseLine();
                }
            }
        }
        final FileCountedLines result = new FileCountedLines(file.getName());
        result.setCount(count);
        return result;
    }

    /**
     * Parse line of source and detect java code
     */
    private void parseLine() {
        // One line comment is ended
        if (parserState == ParserState.COMMENT_ONE_LINE) {
            parserState = ParserState.CODE;
        }
        // Check if line starts with code
        boolean lineContainsCode = parserState == ParserState.CODE;
        if (isTailLongerThan2(0)) {

            // Check line for switches:
            for (int i = 0; i <= str.length() - 2; i++) {
                if (isEndOfMultilineComment(i)) {
                    parserState = ParserState.CODE;
                    if (i == 0) {
                        lineContainsCode = false;
                    }
                    // Skip the multiline comment end
                    i++;
                } else if (isStartOfMultilineComment(i)) {
                    parserState = ParserState.COMMENT_MULTILINE;
                    if (i == 0) {
                        lineContainsCode = false;
                    }
                    // Skip the multiline comment start
                    i++;
                } else if (isStartOfOneLineComment(i)) {
                    parserState = ParserState.COMMENT_ONE_LINE;
                    // Tail of string is a comment
                    break;
                } else if (isCode(i)) {
                    lineContainsCode = true;
                }
            }
        }
        if (lineContainsCode) {
            // Code is detected in this line
            count++;
        }
    }

    private boolean isTailLongerThan2(int i) {
        return isTailLongerThan(i, 2);
    }

    private boolean isTailLongerThan(int i, int limit) {
        return (str.length() - i) >= limit;
    }

    private boolean isEndOfMultilineComment(int i) {
        return parserState == ParserState.COMMENT_MULTILINE
                && matches(i, "*/");
    }

    private boolean isStartOfMultilineComment(int i) {
        return parserState == ParserState.CODE
                && matches(i, "/*");
    }

    private boolean isStartOfOneLineComment(int i) {
        return parserState == ParserState.CODE
                && matches( i, "//");
    }

    private boolean matches(int i, String match) {
        return str.substring(i, i + 2).equals(match);
    }

    /**
     * Check the code text (skip blanks)
     *
     * @param i
     * @return
     */
    private boolean isCode(int i) {
        final int c = str.charAt(i);
        return parserState == ParserState.CODE
                && c != '\t' && c != ' ';
    }
}
