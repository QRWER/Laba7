package functions;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabulatedFunctions {

    private static TabulatedFunctionFactory functionFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    private TabulatedFunctions(){
        throw new UnsupportedOperationException("Can not create this class");
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        if(function.getLeftDomainBorder() > leftX || function.getRightDomainBorder() < rightX || pointsCount<2) throw new IllegalArgumentException();
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double j = (rightX-leftX)/(pointsCount-1);
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + j * i;
            points[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return functionFactory.createTabulatedFunction(points);
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount, Class<? extends TabulatedFunction> cls){
        if(function.getLeftDomainBorder() > leftX || function.getRightDomainBorder() < rightX || pointsCount<2) throw new IllegalArgumentException();
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double j = (rightX-leftX)/(pointsCount-1);
        for (int i = 0; i < pointsCount; ++i) {
            double x = leftX + j * i;
            points[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return createTabulatedFunction(points, cls);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out){
        try {
            DataOutputStream stream = new DataOutputStream(out);
            stream.writeInt(function.getPointsCount());
            for(int i = 0; i< function.getPointsCount(); i++) {
                stream.writeDouble(function.getPointX(i));
                stream.writeDouble(function.getPointY(i));
            }
            stream.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in){
        try {
            DataInputStream stream = new DataInputStream(in);
            int pointCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointCount];
            for(int i = 0; i<pointCount; i++){
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            stream.close();
            return functionFactory.createTabulatedFunction(points);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in, Class<TabulatedFunction> cls){
        try {
            DataInputStream stream = new DataInputStream(in);
            int pointCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointCount];
            for(int i = 0; i<pointCount; i++){
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }
            stream.close();
            return createTabulatedFunction(points, cls);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        try {
            BufferedWriter stream = new BufferedWriter(out);
            stream.write(function.getPointsCount() + " ");
            for(int i = 0; i< function.getPointsCount(); i++) {
                stream.write(function.getPointX(i) + " " + function.getPointY(i) + " ");
            }
            stream.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) {
        try {
            StreamTokenizer stream = new StreamTokenizer(in);
            stream.nextToken();
            int pointCount = (int) stream.nval;
            FunctionPoint[] points = new FunctionPoint[pointCount];
            for (int i = 0; i < pointCount; i++) {
                stream.nextToken();
                double x = stream.nval;
                stream.nextToken();
                double y = stream.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return  new ArrayTabulatedFunction(points);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static TabulatedFunction readTabulatedFunction(Reader in, Class<TabulatedFunction> cls) {
        try {
            StreamTokenizer stream = new StreamTokenizer(in);
            stream.nextToken();
            int pointCount = (int) stream.nval;
            FunctionPoint[] points = new FunctionPoint[pointCount];
            for (int i = 0; i < pointCount; i++) {
                stream.nextToken();
                double x = stream.nval;
                stream.nextToken();
                double y = stream.nval;
                points[i] = new FunctionPoint(x, y);
            }
            return createTabulatedFunction(points, cls);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory functionFactory){
        TabulatedFunctions.functionFactory = functionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points){
        return functionFactory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount){
        return functionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values){
        return functionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points, Class<? extends TabulatedFunction> cls){
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(FunctionPoint[].class);
            function = constructor.newInstance((Object) points);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount, Class<? extends TabulatedFunction> cls){
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(double.class, double.class, int.class);
            function = constructor.newInstance(leftX, rightX, pointsCount);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values, Class<? extends TabulatedFunction> cls){
        TabulatedFunction function;
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(double.class, double.class, double[].class);
            function = constructor.newInstance(leftX, rightX, values);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return function;
    }
}
