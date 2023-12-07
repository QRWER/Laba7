package threads;

import functions.basic.Log;

public class SimpleIntegrator implements Runnable {
    private Task task;

    public SimpleIntegrator(Task task){
        this.task = task;
    }

    public synchronized void run() {
        for(int i = 0; i<task.getCountOfTask(); i++) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            double result;
            synchronized (task){
                result = task.getResult();
            }
            System.out.println("Result: " + task.getLeftBorder() + ' ' + task.getRightBorder() + ' ' + task.getStep() + ' ' + result);
        }
    }
}
