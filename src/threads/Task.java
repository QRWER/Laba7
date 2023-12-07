package threads;

import functions.Function;
import functions.Functions;

public class Task {
    private Function function;
    private double leftBorder;
    private double rightBorder;
    private double step;
    private final int countOfTask;

    public Task(Function function, double leftBorder, double rightBorder, double step, int countOfTask){
        this.function = function;
        this.leftBorder = leftBorder;
        this. rightBorder = rightBorder;
        this.step = step;
        this.countOfTask = countOfTask;
    }

    public Task(int countOfTask){
        this.countOfTask = countOfTask;
    }

    public double getResult(){
        return Functions.integrate(function, leftBorder, rightBorder, step);
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }

    public void setLeftBorder(double leftBorder) {
        this.leftBorder = leftBorder;
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public void setRightBorder(double rightBorder) {
        this.rightBorder = rightBorder;
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double getStep() {
            return step;
    }

    public int getCountOfTask() {
        return countOfTask;
    }
}
