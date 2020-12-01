package org.jcc.java.code.line.counter.output;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Iterator;
import org.jcc.java.code.line.counter.model.CountedLines;

/**
 * Print counted lines to some print stream
 */
class XmlPrinter implements Printer {

    /**
     * Print counted lines into print stream
     * 
     * @param countedLines
     * @param ps 
     */
    @Override
    public void print(CountedLines countedLines, PrintStream ps) {
        ps.printf("<java-sources name=\"%s\" count=\"%d\">\n", countedLines.getName(), countedLines.getCount());
        final Iterator<CountedLines> children = countedLines.getChildren();
        // Directory has children, but file does not
        if (children != null) {
            for (; children.hasNext();) {
                // Print all children with offset
                print( children.next(), ps);
            }
        }
        ps.println("</java-sources>");
    }
}