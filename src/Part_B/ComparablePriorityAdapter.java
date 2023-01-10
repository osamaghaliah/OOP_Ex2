package Part_B;

/**
 * This class represents a customized futuristic task which extends FutureTask <T> class.
 * It allows us to make our thread pool accept customized callables. Thus, it implements Comparable interface so
 * our thread pool can adjust itself to handle tasks according to the highest priority - it is set up in CustomExecutor.
 *
 * @Authors: Osama & Hamad.
 */

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ComparablePriorityAdapter <T> extends FutureTask <T> implements Comparable <ComparablePriorityAdapter<T>> {

    private final Task <T> task;

    /**
     * This constructor initializes an asynchronous task by using FutureTask <T> constructor.
     * Next, it initializes the Task <T> attribute by passing to it the given asynchronous task.
     * @param task - An asynchronous task of type Callable <T>.
     */
    public ComparablePriorityAdapter(Callable <T> task) {
        super(task);
        this.task = new Task<>(task);
    }

    /**
     * This is a getter function for the Task <T> attribute.
     * @return - An object of type Task <T> which is the main attribute.
     */
    public Task <T> getTask() {
        return this.task;
    }

    /**
     * This function compares two different tasks according to their priorities.
     * @param otherTask the object to be compared.
     * @return - An integer value that represents a YES/NO answer of which task's priority is higher.
     */
    @Override
    public int compareTo(ComparablePriorityAdapter<T> otherTask) {
        int ans = 0;

        if (this.getTask().getType() != null && otherTask.getTask().getType() != null)
            ans = Integer.compare(this.getTask().getPriority(), otherTask.getTask().getPriority());

        return ans;
    }
}
