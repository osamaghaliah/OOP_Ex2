package Part_B;

import java.util.*;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    private int currentMaxPriority;
    private static final int cores = Runtime.getRuntime().availableProcessors();
    private static final int minPoolSize = cores / 2;
    private static final int maxPoolSize = cores - 1;
    public CustomExecutor() {
        super(minPoolSize, maxPoolSize, 300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue <> ());
        this.currentMaxPriority = 0;
        super.prestartAllCoreThreads();
    }

    public int getCurrentMaxPriority() {
        return this.currentMaxPriority;
    }

    public void setCurrentMaxPriority(int currentMaxPriority) {
        this.currentMaxPriority = currentMaxPriority;
    }

    protected <T> RunnableFuture <T> newTaskFor(Callable <T> task) {
        return new CustomFutureTask<>(task);
    }

    public synchronized <T> Future <T> submit(Task <T> task) {
        if (task.getType() != null) {
            if (task.getPriority() >= this.currentMaxPriority)
                this.setCurrentMaxPriority(task.getPriority());
        }

        return super.submit(task.getCallable());
    }

    public synchronized <T> Future <T> submit(Callable <T> task) {
        return this.submit(new Task<>(task));
    }

    public synchronized <T> Future <T> submit(Callable <T> task, TaskType type) {
        return this.submit(new Task<>(task, type));
    }

    public synchronized String getCurrentMax() {
        return String.valueOf(this.getCurrentMaxPriority());
    }

    public synchronized void gracefullyTerminate() {
        super.shutdown();
    }
}
