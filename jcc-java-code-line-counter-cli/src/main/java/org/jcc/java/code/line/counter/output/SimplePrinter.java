package org.jcc.java.code.line.counter.output;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Iterator;
import org.jcc.java.code.line.counter.model.CountedLines;

/**
 * Print counted lines to some print stream
 */
class SimplePrinter implements Printer {

    /**
     * Print counted lines into print stream
     * 
     * @param countedLines
     * @param ps 
     */
    @Override
    public void print(CountedLines countedLines, PrintStream ps) {
        print(0, countedLines, ps);
    }

    /**
     * Pretty print counted lines with offset by level
     * 
     * @param level
     * @param countedLines
     * @param ps 
     */
    private void print(int level, CountedLines countedLines, PrintStream ps) {
        // Counted lines info
        ps.printf("%s%s : %d\n", offset(level), countedLines.getName(), countedLines.getCount());
        final Iterator<CountedLines> children = countedLines.getChildren();
        // Directory has children, but file does not
        if (children != null) {
            for (; children.hasNext();) {
                // Print all children with offset
                print( level+1, children.next(), ps);
            }
        }
    }

    /**
     * @param level
     * @return offset of level spaces (2 spaces per level)
     */
    private static String offset(int level) {
        return String.join("", Collections.nCopies(level, "  "));
    }

}