package Part_A;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound) {
        String [] names_of_files = new String [n];
        Random r = new Random(seed);

        try {
            for (int i = 0; i < n; i++) {
                FileWriter out = new FileWriter("file_" + i);
                BufferedWriter bw = new BufferedWriter(out);

                for (int j = 1; j <= r.nextInt(bound); j++) {
                    bw.write("Object-Oriented-Programming.\n");
                }

                bw.close();
                names_of_files[i] = ("file_" + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return names_of_files;
    }

    public static int getNumOfLines(String[] fileNames) {
        int accumulated_lines = 0;

        try {
            for (int i = 0; i < fileNames.length; i++) {
                FileReader in = new FileReader(fileNames[i]);
                BufferedReader br = new BufferedReader(in);

                while (br.readLine() != null)
                    accumulated_lines++;

                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accumulated_lines;
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        int accumulated_lines = 0;

        try {
            for (int i = 0; i < fileNames.length; i++) {
                OurThread t = new OurThread(fileNames[i]);
                t.start();
                t.join();
                accumulated_lines += t.getLines();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return accumulated_lines;
    }

    public static int getNumOfLinesThreadPool(String[] fileNames) {
        ExecutorService ThreadPool = Executors.newFixedThreadPool(fileNames.length);
        int accumulated_lines = 0;

        try {
            for (int i = 0; i < fileNames.length; i++) {
                Future<Integer> lines_calculated_in_call = ThreadPool.submit(new FileLinesCalculationTask(fileNames[i]));
                Integer lines = lines_calculated_in_call.get();
                accumulated_lines += lines;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ThreadPool.shutdown();
        return accumulated_lines;
    }


    public static void main(String [] args) {
        String [] names_of_files = createTextFiles(3, 1, 10);

        long start_time2 = System.nanoTime();
        getNumOfLines(names_of_files);
        long f2_time = System.nanoTime() - start_time2;
        System.out.println(f2_time);

        long start_time3 = System.nanoTime();
        getNumOfLinesThreads(names_of_files);
        long f3_time = System.nanoTime() - start_time3;
        System.out.println(f3_time);

        long start_time4 = System.nanoTime();
        getNumOfLinesThreadPool(names_of_files);
        long f4_time = System.nanoTime() - start_time4;
        System.out.println(f4_time);
    }
}
