package Part_B;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class TempMain {
    public static void main(String [] args) throws Exception {
        CustomExecutor executor = new CustomExecutor();

        Callable <Integer> task1 = Task.createTask(() -> {
            int sum = 0;

            for (int i = 1; i <= 10; i++)
                sum += i;

            return sum;
        }, TaskType.COMPUTATIONAL);


        TaskType.COMPUTATIONAL.setPriority(10);
        Task <Integer> t = new Task<>(() -> {
            int sum = 0;

            for (int i = 1; i <= 10; i++)
                sum += i;

            return sum;
        }, TaskType.COMPUTATIONAL);

        Callable <String> task2 = Task.createTask(() -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

            return sb.reverse().toString();
        }, TaskType.IO);

        Callable <Double> task3 = Task.createTask(() -> {
            double ans = 0;

            ans = 1000 * Math.pow(1.02, 5);

            return ans;
        }, TaskType.OTHER);

        // System.out.println(task1.compareTo(task3.getPriority()));

        Future <Integer> f1 = executor.submit(task1);
        Future <String> f2 = executor.submit(task2);
        Future <Double> f3 = executor.submit(task3);

        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
        }
        catch (Exception e) {
            throw new Exception();
        }

        executor.gracefullyTerminate();

    }
}
