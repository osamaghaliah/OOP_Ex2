package Part_A;

/**
 * This class represents a single task as the following: reading a file and counting its lines.
 * It implements Callable <Integer> interface, so we can have a customized call() function and return an Integer object.
 * The Integer object is representing the current file's number of lines.
 * This task will basically be submitted into a thread pool.
 * An object of such class is used in Ex2_1 Java file.
 *
 * @author: Osama & Hamad
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class FileLinesCalculationTask implements Callable <Integer> {
    // An attribute that represents a file's name that will be given from other classes/functions.
    private String fileName;
    // An attribute that represents the current file's lines - defined as volatile, so it can be visible for other threads.
    private volatile int lines;

    /**
     * Constructor - Assigning our file's name attribute & initializing the "lines" attribute as 0
     * @param fileName - A file's name that will be passed from other classes/functions.
     */
    public FileLinesCalculationTask (String fileName) {
        this.fileName = fileName;
        this.lines = 0;
    }

    /**
     * An overridden function: performs the above-mentioned task - reading a file and counting its lines.
     * This function starts running once the submit() function of an ExecutionService object is called - thread pool related.
     * @return An Integer object which represents the current file's lines.
     * @throws Exception of type IOException.
     */
    @Override
    public Integer call() throws Exception {
        try {
            // Reading the current file's name.
            FileReader in = new FileReader("src\\Part_A\\WrittenFiles\\" + this.fileName);
            BufferedReader br = new BufferedReader(in);

            // Counting the current file's lines.
            while (br.readLine() != null) {
                this.lines++;
            }

            // Closing the current file after reading it.
            br.close();
        } catch (IOException e) {
            throw new IOException();
        }

        return this.lines;
    }
}
