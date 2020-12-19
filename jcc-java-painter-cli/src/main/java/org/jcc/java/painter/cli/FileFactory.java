package org.jcc.java.painter.cli;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileFactory {

    private final String[] args;

    public FileFactory(String[] args) {
        this.args = args;
    }

    public File buildFile() {
        if (args.length == 0) {
            printError("no source file");
        } else {
            final File result = new File(args[0]);
            if (result.isDirectory()) {
                printError("'" + result.getPath() + "' is directory");
            } else if (!isJavaSource(result)) {
                printError("'" + result.getPath() + "' is not Java source");
            } else {
                return result;
            }
        }
        return null;
    }

    private void printError(String msg) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, msg);
    }

    private static boolean isJavaSource(File file) {
        return file.getPath().endsWith(".java");
    }

}
