package tests.java.code.line.counter.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import tests.java.code.line.counter.model.CountedLines;
import tests.java.code.line.counter.model.FileCountedLines;

/**
 * Counter implementation for file. 
 * 
 * Read lines of file and counts java code lines.
 */
class FileCounter extends CounterBase {

    // States of the parser
    private enum ParserState {
        // from tab or space inside the code:
        CODE_BLANK {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '/':
                        return COMMENT_START;
                    case '"':
                        fileCounter.lineContainsCode = true;
                        return STRING;
                    case '\'':
                        fileCounter.lineContainsCode = true;
                        return CHAR;
                    case ' ':
                    case '\t':
                        return CODE_BLANK;
                    default:
                        fileCounter.lineContainsCode = true;
                        return CODE_LITERAL;
                }
            }

        },
        // from the literal in code:
        CODE_LITERAL {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '/':
                        return COMMENT_START;
                    case '"':
                        return STRING;
                    case '\'':
                        return CHAR;
                    case ' ':
                    case '\t':
                        return CODE_BLANK;
                    default:
                        return CODE_LITERAL;
                }
            }

            @Override
            ParserState nextStateOnNewLine() {
                return CODE_BLANK;
            }
        },
        // from '/' possibly comment start:
        COMMENT_START {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '/':
                        return LINE_COMMENT;
                    case '*':
                        return BLOCK_COMMENT;
                    case ' ':
                    case '\t':
                        // There was '/' literal:
                        fileCounter.lineContainsCode = true;
                        return CODE_BLANK;
                    default:
                        // There was '/' literal:
                        fileCounter.lineContainsCode = true;
                        return CODE_LITERAL;
                }
            }
        },
        // from line comment:
        LINE_COMMENT {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                // After line comment start we should skip the line
                return this;
            }

            @Override
            ParserState nextStateOnNewLine() {
                return CODE_BLANK;
            }
        },
        // from multiline block comment:
        BLOCK_COMMENT {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '*':
                        return ASTERISK;
                    default:
                        return BLOCK_COMMENT;
                }
            }

        },
        // from '*' character in the block comment
        ASTERISK {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '/':
                        return BLOCK_COMMENT_END;
                    case '*':
                        return ASTERISK;
                    default:
                        return BLOCK_COMMENT;
                }
            }
        },
        // from the multiline block comment end
        BLOCK_COMMENT_END {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '/':
                        return COMMENT_START;
                    default:
                        return CODE_BLANK;
                }
            }
        },
        // inside the string:
        STRING {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '\\':
                        return PROTECTED_CHAR_IN_STRING;
                    case '"':
                        return CODE_BLANK;
                    default:
                        return STRING;
                }
            }
        },
        // next char in the string is protected by preceding '\':
        PROTECTED_CHAR_IN_STRING {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                return STRING;
            }
        },
        // inside the char:
        CHAR {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                switch (c) {
                    case '\\':
                        return PROTECTED_CHAR_IN_CHAR;
                    case '\'':
                        return CODE_BLANK;
                    default:
                        return CHAR;
                }
            }
        },
        // next char in the char is protected by preceding '\':
        PROTECTED_CHAR_IN_CHAR {
            @Override
            ParserState nextState(int c, FileCounter fileCounter) {
                return CHAR;
            }
        };

        /**
         * Switch parser to the new state by character c
         *
         * @param c
         * @param fileCounter for setting indicator 'this line contains code'
         * @return
         */
        abstract ParserState nextState(int c, FileCounter fileCounter);

        /**
         * @return parser state for begin of new line (will be redefined in code
         * literal and line comment)
         */
        ParserState nextStateOnNewLine() {
            return this;
        }
    }

    // State of current string parser:
    private ParserState parserState = ParserState.CODE_BLANK;
    // Is current line contains code?
    private boolean lineContainsCode;
    // Counter
    private int count = 0;

    public FileCounter(File file) throws FileNotFoundException {
        super(file);
    }

    @Override
    public CountedLines count() throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines()
                    .forEach(line -> parseLine(line.trim()));
        }
        final FileCountedLines result = new FileCountedLines(file.getName());
        result.setCount(count);
        return result;
    }

    /**
     * Parse line of source and detect java code
     */
    private void parseLine(String line) {
        // Skip empty lines
        if (line.isEmpty()) {
            return;
        }

        // Set parser state for start of line
        parserState = parserState.nextStateOnNewLine();
        lineContainsCode = false;
        line.chars().forEach(c -> {
            // Switch parser state and conditionally set 'contains code' indicator
            parserState = parserState.nextState(c, this);
        });
        if (lineContainsCode) {
            // Code is detected in this line
            count++;
        }
    }
}
