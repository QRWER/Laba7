package threads;

import functions.basic.Log;

import java.util.Random;

public class Generator extends Thread{
    private Task task;
    private Semaphore semaphore;

    public Generator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
    }

    public void run() {
        Random random = new Random();
        for(int i = 0; i<task.getCountOfTask(); i++){
            if(isInterrupted()){
                break;
            }
            try {
                semaphore.beginGenerator();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            task.setFunction(new Log(random.nextDouble(1, 10)));
            task.setLeftBorder(random.nextDouble(0, 100));
            task.setRightBorder(random.nextDouble(100, 200));
            task.setStep(random.nextDouble(0, 1));
            System.out.println("Source: " + task.getLeftBorder() + ' ' + task.getRightBorder() + ' ' + task.getStep());
            semaphore.endGenerator();
        }
    }

}