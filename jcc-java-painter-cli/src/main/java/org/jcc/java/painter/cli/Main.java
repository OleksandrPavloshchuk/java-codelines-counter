package org.jcc.java.painter.cli;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("no input file");
        } else {        
            System.err.println("TRACE: file=" + args[0]);
        }
    }
}
