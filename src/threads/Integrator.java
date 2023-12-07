package threads;

import functions.basic.Log;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
    }

    public synchronized void run() {
        for(int i = 0; i<task.getCountOfTask(); i++) {
            if (isInterrupted()){
                break;
            }
            try {
                semaphore.beginIntegrator();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Result: " + task.getLeftBorder() + ' ' + task.getRightBorder() + ' ' + task.getStep() + ' ' + task.getResult());
            semaphore.endIntegrator();
        }
    }
}
