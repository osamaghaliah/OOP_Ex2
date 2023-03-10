package Part_A;
/**
 * Assignment Ex2 - Part A :-
 * This class contains 4 functions that are dealing with writing/reading files and their lines in various ways:
 *
 *      1) Without a thread.
 *      2) With a thread.
 *      3) With a thread pool.
 *
 * NOTE: The written files are stored in package: WrittenFiles
 *
 * @Authors: Osama & Hamad.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {

    /**
     * This function writes n number of files such that each file contains a random number of lines.
     * The random number of lines is generated by using Random class containing a seed and a bound.
     * @param n - Number of files that need to be written.
     * @param seed - Parameter of Random class object.
     * @param bound - Parameter of Random class built-in functions: nextInt(bound), nextDouble(bound).. etc.
     * @return An array of type String that holds the names of the files that were successfully written.
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        // Initializing a String array that will be returned with size of: number of files.
        String [] names_of_files = new String [n];
        Random r = new Random(seed);

        try {
            // Writing n number of files into "WrittenFiles" package.
            for (int i = 0; i < n; i++) {
                FileWriter fw = new FileWriter("src\\Part_A\\WrittenFiles\\file_" + i);
                BufferedWriter bw = new BufferedWriter(fw);

                // Writing a random number of lines in each file - each line is "Object-Oriented-Programming.".
                for (int j = 1; j <= r.nextInt(bound); j++)
                    bw.write("Object-Oriented-Programming.\n");

                // Closing each file to ensure its content and inserting each file's name into our to-be-returned array.
                bw.close();
                names_of_files[i] = ("file_" + i);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return names_of_files;
    }

    /**
     * This function calculates the written lines of the whole files that were written by the above function.
     * @param fileNames - An array of type String holding the names of the files that were written before.
     * @return An accumulated lines of the whole files.
     */
    public static int getNumOfLines(String[] fileNames) {
        // Initializing a variable that holds a to-be-calculated accumulated lines of all files.
        int accumulated_lines = 0;

        try {
            // Reading each file and storing its number of lines into our accumulated_lines variable.
            for (String name : fileNames) {
                FileReader fr = new FileReader("src\\Part_A\\WrittenFiles\\" + name);
                BufferedReader br = new BufferedReader(fr);

                // Reading each line and counting it.
                while (br.readLine() != null)
                    accumulated_lines++;

                // Closing each file after finishing reading it.
                br.close();
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return accumulated_lines;
    }

    /**
     * This function calculates the written lines of the whole files that were written by the first function
     * by using a thread, it is related to OurThread class - a customized thread that extends Java's Thread class.
     * @param fileNames - An array of type String holding the names of the files that were written before.
     * @return An accumulated lines of the whole files.
     */
    public int getNumOfLinesThreads(String[] fileNames) {
        // Initializing a variable that holds a to-be-calculated accumulated lines of all files.
        int accumulated_lines = 0;

        try {
            // Reading each file and storing its number of lines by using a customized thread.
            for (String name : fileNames) {
                // Initializing our customized thread by informing him of the current file's name.
                FileLinesCalculationThread t = new FileLinesCalculationThread("src\\Part_A\\WrittenFiles\\" + name);
                // Starting our thread to perform the required task - reading the current file and counting its lines.
                t.start();
                t.join();
                accumulated_lines += t.getLines();
            }
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return accumulated_lines;
    }

    /**
     * This function calculates the written lines of the whole files that were written by the first function
     * by using a thread pool, it is related to FileLinesCalculationTask class which implements Callable <Integer>.
     * @param fileNames - An array of type String holding the names of the files that were written before.
     * @return An accumulated lines of the whole files.
     */
    public int getNumOfLinesThreadPool(String[] fileNames) {
        // Initializing a group of n (number of written files) threads by using ExecutorService class.
        ExecutorService ThreadPool = Executors.newFixedThreadPool(fileNames.length);
        // Initializing a list of tasks.
        List <FileLinesCalculationTask> tasks = new ArrayList<>();
        // Initializing a variable that holds a to-be-calculated accumulated lines of all files.
        int accumulated_lines = 0;

        try {
            // Filling up our empty initialized list of tasks - each task is responsible for each file.
            for (String name : fileNames) {
                FileLinesCalculationTask task = new FileLinesCalculationTask(name);
                tasks.add(task);
            }

            // Storing each file's amount of lines in a list by using invokeAll method - higher speed & less latency.
            List <Future <Integer>> lines_of_each_file_list = ThreadPool.invokeAll(tasks);

            // Adding each file's amount of lines into our accumulated_lines variable.
            for (Future<Integer> lines : lines_of_each_file_list)
                accumulated_lines += lines.get();
        }

        // Handling both exceptions: InterruptedException & ExecutionException
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Shutting down our thread pool to finish execution of all the submitted tasks and terminate it.
        ThreadPool.shutdown();

        return accumulated_lines;
    }
}
