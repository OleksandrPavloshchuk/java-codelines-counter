package tests.java.code.line.counter;

import java.io.PrintStream;
import java.util.Iterator;
import tests.java.code.line.counter.model.CountedLines;

/**
 * Print counted lines to some print stream
 */
public class Printer {

    /**
     * Print counted lines into print stream
     * 
     * @param countedLines
     * @param ps 
     */
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
        printOffset(level, ps);
        // Counted lines info
        ps.printf("%s : %d\n", countedLines.getName(), countedLines.getCount());
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
     * Print offset of counted lines by level
     * 
     * @param level
     * @param ps 
     */
    private void printOffset(int level, PrintStream ps) {
        for (int i = 0; i < level; i++) {
            ps.print("  ");
        }
    }

}
