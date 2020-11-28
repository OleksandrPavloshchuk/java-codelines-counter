package tests.java.code.line.counter.builder;

import java.io.File;
import java.io.IOException;
import tests.java.code.line.counter.model.CountedLines;
import tests.java.code.line.counter.model.DirCountedLines;

/**
 * Counter implementation for directory.
 *
 * Recursive traverse by subdirectories and files
 */
class DirCounter extends CounterBase {

    public DirCounter(File file) {
        super(file);
    }

    @Override
    public CountedLines count() throws IOException {
        final DirCountedLines result = new DirCountedLines(file.getName());
        // Loop by all the subdirectories and files:
        for (final String fileName : file.list()) {
            final String path = file.getCanonicalPath() + File.separator + fileName;
            final Counter subCounter = CounterFactory.build(path);
            if (subCounter != null) {
                // If subcounter is not null, then it is build over java source or directory
                result.addChild(subCounter.count());
            }
        }
        return result;
    }

}
