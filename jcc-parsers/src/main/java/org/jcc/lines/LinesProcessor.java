package org.jcc.lines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Line by line processor
 */
public class LinesProcessor {

    private LinesProcessor() {

    }

    /**
     * 
     * @param reader 
     * @param lineConsumer processor of line given by reader
     * @throws IOException 
     */
    public static void loopByLines(BufferedReader reader, Consumer<String> lineConsumer) throws IOException {
        reader.lines().forEach(lineConsumer);
    }
    
    /**
     * 
     * @param file
     * @param lineConsumer processor of line given by file
     * @throws IOException 
     */
    public static void loopByLines(File file, Consumer<String> lineConsumer) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            loopByLines(reader, lineConsumer);
        }
    }

}