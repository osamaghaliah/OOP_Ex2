package Part_A;

import static Part_A.Ex2_1.*;

public class Ex2_1_RuntimeComparison {
    public static void main (String [] args) {
        String [] names_of_files = createTextFiles(10000, 2, 100000);

        long start_time2 = System.nanoTime();
        int lines = getNumOfLines(names_of_files);
        long f2_time = System.nanoTime() - start_time2;
        System.out.println("Method 1: WITHOUT USING ANY THREADS - Accumulated Lines: " + lines + " , Runtime: " + (f2_time / 1000000) + "ms");

        long start_time3 = System.nanoTime();
        lines = getNumOfLinesThreads(names_of_files);
        long f3_time = System.nanoTime() - start_time3;
        System.out.println("Method 2: USING ONE THREAD - Accumulated Lines: " + lines + " , Runtime: " + (f3_time / 1000000) + "ms");

        long start_time4 = System.nanoTime();
        lines = getNumOfLinesThreadPool(names_of_files);
        long f4_time = System.nanoTime() - start_time4;
        System.out.println("Method 3: USING A THREAD POOL - Accumulated Lines: " + lines + " , Runtime: " + (f4_time / 1000000) + "ms");
    }
}
