package org.jcc.java.code.line.counter.model;

import java.util.Iterator;

/**
 * Lines, counted in file. Only holds number, set by Counter
 */
public class FileCountedLines extends CountedLines {
    
    private int count = 0;

    public FileCountedLines(String name) {
        super(name);
    }

    @Override
    public int getCount() {
        return count;
    }
    
    @Override
    public Iterator<CountedLines> getChildren() {
        return null;
    }    
    
    /**
     * This method is used by Counter to set counted number of lines.
     * @param count 
     */
    public void setCount(int count) {
        this.count = count;
    }
    
}
