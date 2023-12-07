package functions;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable{

    private FunctionPoint[] array;
    private int length;

    public ArrayTabulatedFunction(FunctionPoint[] points) {
        if (points.length<2) throw new IllegalArgumentException("Illegal argument");
        array = new FunctionPoint[points.length + 5];
        array[0] = new FunctionPoint(points[0]);
        for(int i = 1; i<points.length; i++){
            if(points[i].getX() <= points[i-1].getX()) throw new IllegalArgumentException("Illegal argument");
            array[i] = new FunctionPoint(points[i]);
        }
        length = points.length;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        if (leftX >= rightX || pointsCount<2) throw new IllegalArgumentException("Illegal argument");
        array = new FunctionPoint[pointsCount+5];
        length = pointsCount;
        double j = (rightX-leftX)/(pointsCount-1);
        for (int i = 0;i<pointsCount;i++){
            array[i] = new FunctionPoint(leftX,0);
            leftX+=j;
        }
    }
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        if (leftX >= rightX || values.length<2) throw new IllegalArgumentException("Illegal argument");
        array = new FunctionPoint[values.length+5];
        length = values.length;
        double j = (rightX-leftX)/(values.length-1);
        for (int i = 0;i< values.length;i++){
            array[i] = new FunctionPoint(leftX, values[i]);
            leftX+=j;
        }
    }
    public double getLeftDomainBorder(){
        return array[0].getX();
    }
    public double getRightDomainBorder(){
        return array[length-1].getX();
    }
    public double getFunctionValue(double x){
        if(x>=array[0].getX() && x<=array[length-1].getX()){
            double x1 = getLeftDomainBorder();
            double x2 = getRightDomainBorder();
            double y1 = array[0].getY();
            double y2 = array[length-1].getY();
            return (((x-x1)*(y2-y1))/(x2-x1))+y1;
        }
        else
            return Double.NaN;
    }

    public int getPointsCount(){
        return length;
    }

    public FunctionPoint getPoint(int index){
        if(index<0 || index>=length) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        return new FunctionPoint(array[index].getX(), array[index].getY());
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index<0 || index>=length) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        if (index == 0) {
            if(point.getX() <= array[index+1].getX()) {
                array[index] = new FunctionPoint(point);
            }
            else {
                throw new InappropriateFunctionPointException("Illegal argument");
            }
        }
        else if (index == length-1){
            if(point.getX()>=array[index-1].getX()){
                array[index] = new FunctionPoint(point);
            }
            else{
                throw new InappropriateFunctionPointException("Illegal argument");
            }
        }
        else{
            if(point.getX() >= this.array[index - 1].getX() && point.getX() <= this.array[index + 1].getX()){
                array[index] = new FunctionPoint(point);
            }
            else{
                throw new InappropriateFunctionPointException("Illegal argument");
            }
        }
    }

    public double getPointX(int index){
        if(index<0 || index>=length) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        return array[index].getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if(index<0 || index>=length) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        if(index == 0){
            if(x <= array[index+1].getX()) {
                array[index].setX(x);
            }
            else {
                throw new InappropriateFunctionPointException("Illegal argument");
            }
        }
        else if (index == length-1){
            if(x >= array[index-1].getX()){
                array[index].setX(x);
            }
            else{
                throw new InappropriateFunctionPointException("Illegal argument");
            }
        }
        else{
            if(x >= this.array[index - 1].getX() && x <= this.array[index + 1].getX()){
                array[index].setX(x);
            }
            else{
                throw new InappropriateFunctionPointException("Illegal argument");
            }
        }
    }

    public double getPointY(int index){
        if(index<0 || index>=length) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        return array[index].getY();
    }

    public void setPointY(int index, double y) {
        if(index<0 || index>=length) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        array[index].setY(y);
    }

    public void deletePoint(int index){
        if(index<0 || index>=length) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        if(length<3) throw new IllegalStateException("Illegal argument");
        System.arraycopy(array, index+1, array, index, length-index-1);
        length--;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        int index;
        if(point.getX() <= array[length-1].getX()){
            for(index = 0; point.getX() >= array[index].getX(); index++){
                if(array[index].getX() == point.getX()) throw new InappropriateFunctionPointException("Illegal argument");
            }
        }
        else{
            index = length;
        }
        if(length != array.length){
            System.arraycopy(array, index, array, index+1, length-index);
            array[index] = new FunctionPoint(point);
        }
        else {
            FunctionPoint[] NewArray = new FunctionPoint[length+6];
            System.arraycopy(array, 0, NewArray, 0, index);
            NewArray[index] = new FunctionPoint(point);
            System.arraycopy(array, index, NewArray, index+1, length-index);
            array=NewArray;
        }
        length++;
    }

    public void out() {
        for (int i = 0; i < length; i++) {
            System.out.print(array[i].getX() + " ");
            System.out.print(array[i].getY());
            System.out.println();
        }
    }

    public String toString(){
        String strings = "{ ";
        for(int i = 0; i<length; i++){
            strings+= array[i].toString() + " ";
        }
        strings+= "}";
        return strings;
    }

    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof TabulatedFunction)) return false;
        if(obj instanceof ArrayTabulatedFunction){
            ArrayTabulatedFunction aObj = (ArrayTabulatedFunction) obj;
            if(this.length != aObj.length) return false;
            for(int i = 0; i<length; i++){
                if(!array[i].equals(aObj.array[i])) return false;
            }
        }
        else{
            if(((TabulatedFunction) obj).getPointsCount() != length) return false;
            for(int i = 0; i<length; i++){
                if(!(((TabulatedFunction) obj).getPoint(i)).equals(array[i])) return false;
            }
        }
        return true;
    }

    public int hashCode(){
        int result = Objects.hashCode(length);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    public Object clone() throws CloneNotSupportedException{
        FunctionPoint[] newArray = new FunctionPoint[length];
        System.arraycopy(array, 0, newArray, 0, length);
        return new ArrayTabulatedFunction(newArray);
    }
}
