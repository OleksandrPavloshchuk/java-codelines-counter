package org.jcc.java.code.line.counter;

import java.io.File;
import java.io.IOException;
import org.jcc.java.code.line.counter.model.CountedLines;
import org.jcc.java.code.line.counter.model.DirCountedLines;

/**
 * Counter implementation for directory.
 *
 * Recursive traverse by subdirectories and files
 */
class DirCounter extends CounterBase {

    DirCounter(File file) {
        super(file);
    }

    @Override
    public CountedLines count() throws IOException {
        final DirCountedLines result = new DirCountedLines(file.getName());
        // Loop by all the subdirectories and files:
        for (final String fileName : file.list()) {
            final Counter subCounter = CounterFactory.build(file.getCanonicalPath() + File.separator + fileName);
            if (subCounter != null) {
                // If subcounter is not null, then it is build over java source or directory
                result.addChild(subCounter.count());
            }
        }
        return result;
    }

}
