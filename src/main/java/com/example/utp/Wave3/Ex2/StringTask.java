package com.example.utp.Wave3.Ex2;


import java.util.concurrent.atomic.AtomicReference;

public class StringTask implements Runnable {
    private String text;
    private int repetition;
    private volatile String result;
    private Thread task;
    private volatile boolean aborted;
    private AtomicReference<TaskState> state;

    public StringTask(String text, int repetition) {
        this.text = text;
        this.repetition = repetition;
        result = text;
        task = new Thread(this);
        state = new AtomicReference<>(TaskState.CREATED);
    }

    public String getResult() {
        return (state.get() == TaskState.READY || state.get() == TaskState.ABORTED) ? result : null;
    }

    public TaskState getState() {
        return state.get();
    }

    public boolean isDone() {
        return state.get() == TaskState.READY || state.get() == TaskState.ABORTED;
    }

    public void start() {
        if(state.compareAndSet(TaskState.CREATED, TaskState.RUNNING)) {
            task.start();
        }
    }

    public void abort() {
        if(state.compareAndSet(TaskState.CREATED, TaskState.ABORTED) || state.compareAndSet(TaskState.RUNNING, TaskState.ABORTED)) {
            aborted = true;
            task.interrupt();
        }
    }

    public enum TaskState {
        CREATED,
        RUNNING,
        ABORTED,
        READY
    }

    // Use of String concatenation is imposed by the exercise

    @Override
    public void run() {
        try {
            for(int i = 1; i < repetition; i++) {
                if(aborted || Thread.currentThread().isInterrupted()) {
                    state.set(TaskState.ABORTED);
                    return;
                }
                result = result + text;
            }
            state.set(TaskState.READY);
        } catch(Exception exc) {
            state.set(TaskState.ABORTED);
        }
    }
}
