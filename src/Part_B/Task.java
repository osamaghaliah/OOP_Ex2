package Part_B;

import java.util.concurrent.Callable;

public class Task <T> implements Callable <T>{
    private TaskType type;
    private Callable <T> callable;

    public Task(Callable <T> callable) throws IllegalArgumentException {
        try {
            this.callable = callable;
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Task(Callable <T> callable, TaskType type) throws IllegalArgumentException {
        try {
            this.callable = callable;
            this.type = type;
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public synchronized TaskType getType() {
        return this.type;
    }

    public synchronized void setType(TaskType type) {
        this.type = type;
    }

    public Callable <T> getCallable() {
        return this.callable;
    }

    public void setCallable(Callable<T> callable) {
        this.callable = callable;
    }

    public int getPriority() {
        return this.type.getPriorityValue();
    }
    public T result(Callable <T> callable, TaskType type) throws Exception {
        try {
            this.type = type;
            return callable.call();
        }

        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> Callable <T> createTask(Callable <T> callable, TaskType type) {
        return callable;
    }

    @Override
    public T call() throws Exception {
        return this.getCallable().call();
    }
}
