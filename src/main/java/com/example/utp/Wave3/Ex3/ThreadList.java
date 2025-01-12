package com.example.utp.Wave3.Ex3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class ThreadList extends JScrollPane {
    private final ExecutorService exec;
    private final DefaultListModel<Future<String>> model = new DefaultListModel<>();
    JList<Future<String>> list = new JList<>();

    public ThreadList(int thread_pool_num) {
        exec = Executors.newFixedThreadPool(thread_pool_num);
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setViewportView(list);
    }

    public void addTask(Callable<String> task) {
        Future<String> future = exec.submit(task);
        SwingUtilities.invokeLater(() -> model.addElement(future));
    }

    public void cancelTask() {
        final int idx = list.getSelectedIndex();
        if(idx != -1) {
            Future<String> future = model.getElementAt(idx);
            if(!future.isCancelled() && !future.isDone()) {
                future.cancel(true);
                JOptionPane.showMessageDialog(null, future + " was cancelled", "Task Cancelled", JOptionPane.INFORMATION_MESSAGE);
            } else if(future.isDone()) {
                JOptionPane.showMessageDialog(null, "The task is finished, it cannot be cancelled anymore", "Task" + future + "Cannot be Cancelled", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            noTaskSelected();
        }
    }

    public void getResult() {
        int idx = list.getSelectedIndex();
        String result = null;

        if (idx == -1) {
            noTaskSelected();
            return;
        }

        Future<String> future = model.getElementAt(idx);
        try {
            if(future.isCancelled()) {
                result = "TASK CANCELLED";
            } else if(future.isDone()) {
                result = future.get();
            } else {
                result = "Task in progress, waiting for the result ...";
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
            result = "Error";
        } finally {
            if(result != null) {
                System.out.println(result);
                JOptionPane.showMessageDialog(null, future + ":\n" + result, "Task Result", result.equals("Error") ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    public void getSelectedTaskState() {
        String result = null;
        int idx = list.getSelectedIndex();
        if(idx == -1) {
            noTaskSelected();
            return;
        }

        Future<String> future = model.getElementAt(idx);
        if(future.isCancelled()) {
            System.out.println(future);
            result = "ABORTED";
        } else if(future.isDone()) {
            result = "DONE";
        } else {
            result = "RUNNING";
        }

        System.out.println(result);
        JOptionPane.showMessageDialog(null, future + " " + result, "Task State", JOptionPane.INFORMATION_MESSAGE);;

    }

    public void noTaskSelected() {
        JOptionPane.showMessageDialog(list, "No task was selected", "Missing Selection", JOptionPane.ERROR_MESSAGE);
    }

    public void shutdown() {
        exec.shutdown();
        try {
            if (!exec.awaitTermination(5, TimeUnit.SECONDS)) {
                exec.shutdownNow();
            }
        } catch (InterruptedException e) {
            exec.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
