package Part_B;

/**
 * This class represents a custom thread pool that extends from Java's built-in ThreadPoolExecutor class.
 * Since Java DOESN'T support priorities for the TASKS that are being handled by the threads, we have implemented
 * this customized thread pool that DOES handle tasks by their priorities.
 *
 * @Authors: Osama & Hamad.
 */

import java.util.HashMap;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    // This attribute represents the highest priority of a task that is sitting among other tasks in pool's queue.
    private int currentMaxPriority;
    // Setting up our minimum and maximum pool sizes according to your computer's number of cores for the constructor.
    private static final int cores = Runtime.getRuntime().availableProcessors();
    private static final int minPoolSize = cores / 2;
    private static final int maxPoolSize = cores - 1;

    /**
     * This constructor essentially uses ThreadPoolExecutor class constructor.
     * It starts our thread pool with the following conditions:
     *      - Its minimum pool size equals half the number of your computer's cores.
     *      - Its maximum pool size equals the number of your computer's cores - 1.
     *      - Each idle thread has to stay alive for 300 milliseconds.
     *      - Its dedicated data structure for holding our tasks is: PriorityBlockingQueue
     * Next, initializing the currentMaxPriority attribute with 1 - the least general valid priority value of a task.
     */
    public CustomExecutor() {
        super(minPoolSize, maxPoolSize, 300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue <> ());
        this.currentMaxPriority = 1;
    }

    /**
     * This is a getter function for our currentMaxPriority attribute.
     * @return currentMaxPriority attribute's int value.
     */
    public int getCurrentMaxPriority() {
        return this.currentMaxPriority;
    }

    /**
     * This is a setter function for our currentMaxPriority attribute.
     * @param currentMaxPriority - the new value to be assigned for our currentMaxPriority attribute.
     */
    public void setCurrentMaxPriority(int currentMaxPriority) {
       if (currentMaxPriority >= 1 && currentMaxPriority <= 10)
            this.currentMaxPriority = currentMaxPriority;
    }

    /**
     * This is a customized wrapper function that sets up a task to be ready to be handled in the thread pool.
     * @param task - An asynchronous task of type Callable <T>.
     * @return - A new customized FutureTask that will run in the thread pool.
     */
    protected <T> RunnableFuture <T> newTaskFor(Callable <T> task) {
        return new ComparablePriorityAdapter <> (task);
    }

    /**
     * This function adds a task into our thread pool's queue to be handled next.
     * @param task - A Task <T> instance which represents an asynchronous task.
     * @return - An object of type Future <T> which holds the result of our asynchronous task.
     */
    public synchronized <T> Future <T> submit(Task <T> task) {
        try {
            if (task.getType() != null) {
                if (task.getPriority() >= this.currentMaxPriority)
                    this.setCurrentMaxPriority(task.getPriority());
            }

            return super.submit(task.getCallable());
        }

        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The given argument is illegal!");
        }
    }

    /**
     * This function adds a callable operation into our thread pool's queue to be handled next.
     * It uses the first submit(Task <T> task) function.
     * @param task - An asynchronous task of type Callable <T>.
     * @return - An object of type Future <T> which holds the result of our asynchronous task.
     */
    public synchronized <T> Future <T> submit(Callable <T> task) {
        // Initializing an instance of Task <T> and passing the callable task into it.
        // The Task <T> instance is passed into the first submit(Task <T>) function.
        return this.submit(new Task <> (task));
    }

    /**
     * This function adds a callable operation into our thread pool's queue to be handled next.
     * It uses the first submit(Task <T> task) function.
     * @param task - An asynchronous task of type Callable <T>.
     * @param type - An instance of TaskType enum which represents the type of our asynchronous task.
     * @return - An object of type Future <T> which holds the result of our asynchronous task.
     */
    public synchronized <T> Future <T> submit(Callable <T> task, TaskType type) {
        // Initializing an instance of Task <T> and passing the callable task & the type of the task into it.
        // The Task <T> instance is passed into the first submit(Task <T>) function.
        return this.submit(new Task <> (task, type));
    }

    /**
     * This function informs us of the highest priority of a task which sits among other tasks in the pool's queue.
     * @return - currentMaxPriority attribute's value - which was adjusted accordingly.
     */
    public synchronized int getCurrentMax() {
        return this.getCurrentMaxPriority();
    }


    /**
     * This function shuts down our whole thread pool as the following:
     *      - It is no longer accepting any other tasks.
     *      - It performs the tasks that are left in the queue.
     *      - It finishes the whole left un-finished tasks.
     */
    public synchronized void gracefullyTerminate() {
        super.shutdown();
    }
}
