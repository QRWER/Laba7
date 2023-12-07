package functions.meta;

import functions.Function;

public class Sum implements Function {

    private Function firstFunc;
    private Function secondFunc;

    public Sum(Function firstFunc, Function secondFunc){
        this.firstFunc = firstFunc;
        this.secondFunc = secondFunc;
    }

    public double getLeftDomainBorder() {
        return Math.min(firstFunc.getLeftDomainBorder(), secondFunc.getLeftDomainBorder());
    }

    public double getRightDomainBorder() {
        return Math.min(firstFunc.getRightDomainBorder(), secondFunc.getRightDomainBorder());
    }

    public double getFunctionValue(double x) {
        return firstFunc.getFunctionValue(x) + secondFunc.getFunctionValue(x);
    }

}
