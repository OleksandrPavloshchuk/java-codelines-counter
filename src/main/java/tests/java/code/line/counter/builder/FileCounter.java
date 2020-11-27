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
    // Current position in the string:
    private int index;
    // Is current line contains code?
    private boolean lineContainsCode;
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
        lineContainsCode = parserState == ParserState.CODE;
        if (isTailLongerThan2(0)) {

            // Check line for switches:
            for (index = 0; index <= str.length() - 2; index++) {
                if (isEndOfMultilineComment()) {
                    parserState = ParserState.CODE;
                    setNoCodeAtStart();
                    skipCommentMarker();
                } else if (isStartOfMultilineComment()) {
                    parserState = ParserState.COMMENT_MULTILINE;
                    setNoCodeAtStart();
                    skipCommentMarker();
                } else if (isStartOfOneLineComment()) {
                    parserState = ParserState.COMMENT_ONE_LINE;
                    // Tail of string is a comment
                    break;
                } else if (isCode()) {
                    lineContainsCode = true;
                }
            }
        }
        if (lineContainsCode) {
            // Code is detected in this line
            count++;
        }
    }

    /**
     * If line starts from comment, it does not contain code
     */
    private void setNoCodeAtStart() {
        if (index == 0) {
            lineContainsCode = false;
        }
    }

    private void skipCommentMarker() {
        index++;
    }

    private boolean isTailLongerThan2(int i) {
        return isTailLongerThan(i, 2);
    }

    private boolean isTailLongerThan(int i, int limit) {
        return (str.length() - i) >= limit;
    }

    /**
     * @return true, if this is end of multiline comment
     */
    private boolean isEndOfMultilineComment() {
        return parserState == ParserState.COMMENT_MULTILINE
                && matches("*/");
    }

    /**
     * @return true, if this is start of multiline comment
     */
    private boolean isStartOfMultilineComment() {
        return parserState == ParserState.CODE && matches("/*");
    }

    /**
     * @return true, if this is start of one line comment
     */
    private boolean isStartOfOneLineComment() {
        return parserState == ParserState.CODE && matches("//");
    }

    /**
     * @param s
     * @return true, if next 2 characters matches string s
     */
    private boolean matches(String s) {
        return str.substring(index, index + 2).equals(s);
    }

    /**
     * @return true, if parser in the code state and current character is not
     * blank
     */
    private boolean isCode() {
        final int c = str.charAt(index);
        return parserState == ParserState.CODE
                && c != '\t' && c != ' ';
    }
}
