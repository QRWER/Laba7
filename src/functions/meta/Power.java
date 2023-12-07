package functions.meta;

import functions.Function;

public class Power implements Function {

    private Function func;
    private double degree;

    public Power(Function func, double degree){
        this.func = func;
        this.degree = degree;
    }

    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return func.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return Math.pow(func.getFunctionValue(x), degree);
    }
}
