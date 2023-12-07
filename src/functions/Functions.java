package functions;
import functions.meta.*;

public class Functions {

    private Functions() {
        throw new UnsupportedOperationException("Illegal create class");
    }

    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power){
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2){
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2){
        return new Composition(f1, f2);
    }

    public static double integrate(Function function, double leftX, double rightX, double step){
        if(leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) throw new IllegalArgumentException("Illegal argument");
        double total = 0;
        for (;leftX+step<rightX; leftX += step) {
            total += 0.5 * step * (function.getFunctionValue(leftX) + function.getFunctionValue(leftX+step));
        }
        total += 0.5 * (rightX-leftX+step) * (function.getFunctionValue(leftX-step) + function.getFunctionValue(rightX));
        return total;
    }
}
