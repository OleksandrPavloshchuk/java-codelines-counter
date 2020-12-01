package tests.java.code.line.counter.builder;

import java.io.File;
import java.util.Objects;

/**
 * Base of counters.
 * 
 * Holds file object
 */
abstract class CounterBase implements Counter {
 
    protected final File file;
    
    CounterBase(File file) {
        this.file = Objects.requireNonNull(file, "file is null");
    }
}
