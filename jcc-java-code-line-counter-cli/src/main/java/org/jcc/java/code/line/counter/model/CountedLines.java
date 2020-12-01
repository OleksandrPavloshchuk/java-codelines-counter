package org.jcc.java.code.line.counter.model;

import java.util.Iterator;
import java.util.Objects;

/**
 * Holds information about counted lines in file or directory
 */
public abstract class CountedLines {
    
    /**
     * @return number of lines in file or directory
     */
    public abstract int getCount(); 
    
    /**
     * @return iterator for children. Counted lines of file do not have children
     */
    public abstract Iterator<CountedLines> getChildren();
    
    private final String name;  // name of file or directory
    
    public CountedLines(String name) {
        this.name = Objects.requireNonNull(name, "name is null");
    }
    
    public String getName() {
        return name;
    }
     
}
