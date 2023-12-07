package functions.meta;

import functions.Function;

public class Composition implements Function {

    private Function firstFunc;
    private Function secondFunc;

    public Composition(Function firstFunc, Function secondFunc){
        this.firstFunc = firstFunc;
        this.secondFunc = secondFunc;
    }

    public double getLeftDomainBorder() {
        return firstFunc.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return firstFunc.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return firstFunc.getFunctionValue(secondFunc.getFunctionValue(x));
    }
}
