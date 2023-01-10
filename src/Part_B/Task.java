package Part_B;

/**
 * This class represents a task that will be submitted into a thread pool to be handled.
 * It is a generic class that accepts any type of asynchronous operation. Thus, it implements Callable <T> interface.
 * An instance of this class also contains a specified enum which indicates what is the type of the current task is.
 *
 * @Authors: Osama & Hamad.
 */

import java.io.IOException;
import java.util.concurrent.Callable;

public class Task <T> implements Callable <T>{
    // An attribute that represents an enum - the type of task.
    private TaskType type;
    // An attribute that represents an asynchronous operation.
    private Callable <T> callable;

    /**
     * This constructor initializes our asynchronous operation with the given one.
     * @param callable - An asynchronous operation.
     * @throws IllegalArgumentException
     */
    public Task(Callable <T> callable) throws IllegalArgumentException {
        try {
            this.callable = callable;
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This constructor initializes our asynchronous operation with the given one & the enum type.
     * @param callable - An asynchronous operation.
     * @param type - The type of the callable parameter - an enum.
     * @throws IllegalArgumentException
     */
    public Task(Callable <T> callable, TaskType type) throws IllegalArgumentException {
        try {
            this.callable = callable;
            this.type = type;
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This is a getting function for the enum attribute.
     * @return - The type of the task - an enum.
     */
    public synchronized TaskType getType() {
        return this.type;
    }

    /**
     * This is a setter function for the enum attribute.
     * @param type - The new enum to be assigned for the enum attribute.
     */
    public synchronized void setType(TaskType type) {
        this.type = type;
    }

    /**
     * This is a getter function for our asynchronous attribute.
     * @return - An asynchronous operation.
     */
    public Callable <T> getCallable() {
        return this.callable;
    }

    /**
     * This is a setter function for our asynchronous attribute.
     * @param callable - The new asynchronous operation to be assigned for the asynchronous attribute.
     */
    public void setCallable(Callable<T> callable) {
        this.callable = callable;
    }

    /**
     * This is a getter function for our enum's priority.
     * @return - The priority of the enum attribute - an integer value.
     */
    public int getPriority() {
        return this.type.getPriorityValue();
    }

    /**
     * This is a static factory method which allows us to create an asynchronous task manually - including its type.
     * @param callable - An asynchronous operation.
     * @param type - The type of the asynchronous operation.
     * @return - An instance of Task <T> that represents the given asynchronous operation and its type.
     */
    public static <T> Task <T> createTask(Callable <T> callable, TaskType type) {
        return new Task <> (callable, type);
    }

    /**
     * This is a generic function that returns any result of any asynchronous operation.
     * @return - The result of a task.
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        try {
            return this.getCallable().call();
        }

        catch (IOException e) {
            throw new IOException("The call() method has failed to return its value!");
        }
    }
}
