package tests.java.code.line.counter.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lines, counted in directory. Recurrently counts lines in all files and
 * directories.
 */
public class DirCountedLines extends CountedLines {

    // Counted lines for directory children:
    private final List<CountedLines> children = new ArrayList<>();

    public DirCountedLines(String name) {
        super(name);
    }

    @Override
    public int getCount() {
        return children.stream()
                .mapToInt(CountedLines::getCount)
                .sum();
    }

    @Override
    public Iterator<CountedLines> getChildren() {
        return children.iterator();
    }

    /**
     * Add counted child data to list
     *
     * @param child
     */
    public void addChild(CountedLines child) {
        if (child != null && child.getCount() > 0) {
            // Add only files and directories, which contains java code
            children.add(child);
        }
    }

}
