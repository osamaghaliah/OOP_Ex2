package Part_A;

/**
 * This class represents a customized thread that reads a specific file and counts its lines.
 * An object of such class is used in Ex2_1 Java file.
 *
 * @author: Osama & Hamad
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLinesCalculationThread extends Thread {
    // An attribute that represents a file's name that will be given from other classes/functions.
    private String fileName;
    // An attribute that represents the current file's lines - defined as volatile, so it can be visible for other threads.
    private volatile int lines;

    /**
     * Constructor - Assigning our file's name attribute & initializing the "lines" attribute as 0
     * @param fileName - A file's name that will be passed from other classes/functions.
     */
    public FileLinesCalculationThread(String fileName) {
        this.fileName = fileName;
        this.lines = 0;
    }

    /**
     * This is a getter function.
     * @return "lines" attribute value.
     */
    public int getLines() {
        return this.lines;
    }

    /**
     * This function starts running once the start() function is called of this thread - extending from Thread.
     */
    public void run() {
        try {
            // Reading the current file's name.
            FileReader in = new FileReader(this.fileName);
            BufferedReader br = new BufferedReader(in);

            // Counting the current file's lines.
            while (br.readLine() != null) {
                this.lines++;
            }

            // Closing the current file after reading it.
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
