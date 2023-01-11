package Part_B;

/**
 * This is a JUNIT5 class that represents the correctness of our whole assignment implementation.
 * It tests multiple manually created asynchronous tasks that are submitted into a customized thread pool.
 * We have managed to extend the given partial test into a complete one. Multiple cases and implementations were taken
 * into consideration - such as: creating a task by connecting it to one of the implementations of Part_A.
 *
 * @Authors: Osama & Hamad.
 */

import Part_A.Ex2_1;
import Part_A.FileLinesCalculationTask;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);

    @Test
    public void completeTest() throws Exception {
        // Initializing our custom thread pool.
        CustomExecutor ThreadPool = new CustomExecutor();

        var task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);

        var sumTask = ThreadPool.submit(task);
        final int sum;

        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        logger.info(() -> "Sum of 1 through 10 = " + sum);

        Callable <String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };

        // var is used to infer the declared type automatically
        var priceTask = ThreadPool.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);

        var reverseTask = ThreadPool.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;

        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " +
                ThreadPool.getCurrentMax());

        // Initializing a computational task that represents factorial from 1 to 5 (5!)
        Task <Integer> factorialTask = new Task<>(() -> {
            int f = 1;

            for (int i = 1; i <= 5; i++) {
                f *= i;
            }

            return f;
        }, TaskType.COMPUTATIONAL);

        // Initializing a string-insertion task of type IO-Bound.
        Task <String> stringInsertionTask = new Task <> (() -> {
            StringBuilder str = new StringBuilder("Ariel University");
            return str.insert(0, "This is ").toString();
        }, TaskType.IO);

        // Tweaking tasks priorities
        factorialTask.getType().setPriority(2);
        stringInsertionTask.getType().setPriority(5);

        // Submitting the above two tasks into our thread pool.
        Future<Integer> factorialTaskFuture = ThreadPool.submit(factorialTask, factorialTask.getType());
        Future<String> stringInsertionTaskFuture = ThreadPool.submit(stringInsertionTask);

        // Attempting to retrieve each task's result from our Future objects.
        final int factorialTaskResult;
        final String stringInsertionTaskResult;
        try {
            factorialTaskResult = factorialTaskFuture.get();
            stringInsertionTaskResult = stringInsertionTaskFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // Making sure that our future objects are holding our expected results.
        assertEquals(factorialTaskResult, 120);
        assertEquals(stringInsertionTaskResult, "This is Ariel University");


        /*
            Initializing a task that reads the first 3 written files (in WrittenFiles package of Part_A) and counts their lines.
            Files names are: "file_0", "file_1" and "file_2". It is the same files that were written by using
            createTextFiles(int n, int seed, int bound) in Part_A in Ex2_1 class.
            This task contains a task that we implemented in Part_A - FileLineCalculationTask class.
         */
        Task <Integer> linesCalculatorTask = Task.createTask(() -> {
            int accumulated_lines = 0;

            for (int i = 0; i < 3; i++) {
                FileLinesCalculationTask flct = new FileLinesCalculationTask("file_" + i);
                accumulated_lines += flct.call();
            }

            return accumulated_lines;
        }, TaskType.OTHER);

        // Tweaking linesCalculatorTask priority.
        linesCalculatorTask.getType().setPriority(8);

        // Submitting linesCalculatorTask task into our thread pool.
        Future<Integer> linesCalculatorTaskFuture = ThreadPool.submit(linesCalculatorTask);

        // Attempting to retrieve the number of lines of a Future object.
        final int linesCalculatorTaskResult;
        try {
            linesCalculatorTaskResult = linesCalculatorTaskFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        /*
            Performing correctness of the last implemented task by comparing it with the result of a static function
            that was implemented in Part_A (getNumOfLines(String [])) which achieves the same goal without using
            any threads or asynchronous operations.
         */
        assertEquals(linesCalculatorTaskResult, Ex2_1.getNumOfLines(new String [] {"file_0", "file_1", "file_2"}));

        // Grouping the whole above defined tasks in a list.
        List <Task> list_of_tasks = new ArrayList<>();
        list_of_tasks.add(factorialTask);
        list_of_tasks.add(stringInsertionTask);
        list_of_tasks.add(linesCalculatorTask);

        // Submitting the whole tasks at once into our thread pool using invokeAll(list_of_tasks) function.
        List <Future> list_of_tasks_futures = ThreadPool.invokeAll((Collection) list_of_tasks);

        for (int i = 0; i < list_of_tasks_futures.size(); i++) {
            int ind = i;

            logger.info(() -> {
                try {
                    return "Task #" + ind + " result = " + list_of_tasks_futures.get(ind).get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // Returning the maximum priority after tweaking.
        logger.info(() -> "Maximum priority = " + ThreadPool.getCurrentMaxPriority());

        // Gracefully terminating our thread pool and finishing its own inner left tasks.
        ThreadPool.gracefullyTerminate();

        // Making sure that the thread pool has been successfully terminated.
        ThreadPool.awaitTermination(1, TimeUnit.MILLISECONDS);
        assertTrue(ThreadPool.isTerminated());
        logger.info(() -> "The thread pool has been successfully TERMINATED!");
    }

}
