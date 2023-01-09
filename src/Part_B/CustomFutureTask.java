package Part_B;

import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class CustomFutureTask <T> extends FutureTask <T> implements Comparable <CustomFutureTask <T>> {

    private final Task <T> task;

    public CustomFutureTask(Callable <T> task) {
        super(task);
        this.task = new Task<>(task);
    }

    public Task <T> getTask() {
        return this.task;
    }


    @Override
    public int compareTo(CustomFutureTask<T> otherTask) {
        int ans = 0;

        if (this.getTask().getType() != null && otherTask.getTask().getType() != null)
            ans = Integer.compare(this.getTask().getPriority(), otherTask.getTask().getPriority());

        return ans;
    }
}
