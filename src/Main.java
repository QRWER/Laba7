import functions.*;
import functions.basic.*;
import threads.*;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Main {

    public static void nonThread(){
        Random random = new Random();
        Task task = new Task(random.nextInt(100, 1000));
        System.out.println(task.getCountOfTask());
        for(int i = 0; i<task.getCountOfTask(); i++){
            task.setFunction(new Log(random.nextDouble(1, 10)));
            task.setLeftBorder(random.nextDouble(0, 100));
            task.setRightBorder(random.nextDouble(100, 200));
            task.setStep(random.nextDouble(0, 1));
            System.out.println("Source: " + task.getLeftBorder() + ' ' + task.getRightBorder() + ' ' + task.getStep());
            double result = task.getResult();
            System.out.println("Result: " + task.getLeftBorder()+ ' ' + task.getRightBorder()+ ' ' + task.getStep() + ' ' + result);
        }
    }

    public static void simpleThreads() {
        Random random = new Random();
        Task task = new Task(random.nextInt(100, 200));
        Thread thread1 = new Thread(new SimpleGenerator(task));
        Thread thread2 = new Thread(new SimpleIntegrator(task));
        thread1.start();
        thread2.start();
    }

    public static void complicatedThreads(){
        Random random = new Random();
        Semaphore semaphore = new Semaphore();
        Task task = new Task(random.nextInt(100, 500));
        Thread thread1 = new Generator(task, semaphore);
        Thread thread2 = new Integrator(task, semaphore);
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) throws InterruptedException {
        complicatedThreads();
    }
}