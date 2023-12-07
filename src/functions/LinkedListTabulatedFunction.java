package functions;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable{

    private static class FunctionNode {
        FunctionPoint data;
        FunctionNode next;
        FunctionNode prev;

        FunctionNode(){
            this.data = null;
            this.next = null;
            this.prev = null;
        }

        FunctionNode(FunctionNode prev, FunctionNode next, FunctionPoint data){
            this.data = new FunctionPoint(data);
            this.next = next;
            this.prev = prev;
        }

        FunctionNode(FunctionNode node) {
            this.data = new FunctionPoint(node.data);
            this.next = node.next;
            this.prev = node.prev;
        }

    }

    private FunctionNode head;

    private int countOfNodes;

    private FunctionNode cache;

    private FunctionNode getNodeByIndex(int index) {
        FunctionNode tmp = head.next;
        for(int i = 0; i<index; i++){
            tmp = tmp.next;
        }
        return tmp;
    }

    private FunctionNode addNodeToTail() {
        FunctionNode newNode = new FunctionNode(head.prev, head.next, new FunctionPoint());
        head.next.prev = newNode;
        head.prev.next = newNode;
        head.prev = newNode;
        countOfNodes++;
        cache = newNode;
        return newNode;
    }

    private FunctionNode addNodeToHead() {
        FunctionNode newNode = new FunctionNode(head.prev, head.next, new FunctionPoint());
        head.next.prev = newNode;
        head.prev.next = newNode;
        head.next = newNode;
        countOfNodes++;
        cache = newNode;
        return newNode;
    }

    private FunctionNode addNodeByIndex(int index) {
        FunctionNode oldNode = getNodeByIndex(index);
        FunctionNode newNode = new FunctionNode(oldNode.prev, oldNode, new FunctionPoint());
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        countOfNodes++;
        cache = newNode;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index) {
        if (countOfNodes == 2 || index<0 || index >= countOfNodes) throw new IllegalStateException("Illegal argument");
        FunctionNode deletedNode = getNodeByIndex(index);
        if (index == 0) head.next = deletedNode.next;
        else if (index == countOfNodes - 1) head.prev = deletedNode.prev;
        deletedNode.prev.next = deletedNode.next;
        deletedNode.next.prev = deletedNode.prev;
        countOfNodes--;
        return deletedNode;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) {
        if (points.length<2 || points[0].getX() >= points[1].getX()) throw new IllegalArgumentException("Illegal argument");
        firs2Elements(new FunctionPoint(points[0]), new FunctionPoint(points[1]));
        for(int i = 2; i<points.length; i++){
            if(points[i].getX() <= points[i-1].getX()) throw new IllegalArgumentException("Illegal argument");
            addNodeToTail();
            head.prev.data.setX(points[i].getX());
            head.prev.data.setY(points[i].getY());
        }
        countOfNodes = points.length;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX >= rightX || pointsCount<2) throw new IllegalArgumentException("Illegal argument");
        double j = (rightX-leftX)/(pointsCount-1);
        firs2Elements(new FunctionPoint(leftX, 0), new FunctionPoint(leftX+j, 0));
        leftX+= 2 * j;
        for (int i = 2; i<pointsCount; i++){
            addNodeToTail();
            head.prev.data.setX(leftX);
            leftX+=j;
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values){
        if (leftX >= rightX || values.length<2) throw new IllegalArgumentException("Illegal argument");
        double j = (rightX-leftX)/(values.length-1);
        firs2Elements(new FunctionPoint(leftX, values[0]), new FunctionPoint(leftX+j, values[1]));
        leftX+= 2 * j;
        for (int i = 2;i<values.length;i++){
            addNodeToTail();
            head.prev.data.setX(leftX);
            head.prev.data.setY(values[i]);
            leftX+=j;
        }
    }

    public double getLeftDomainBorder() {
        return head.next.data.getX();
    }

    public double getRightDomainBorder() {
        return head.prev.data.getX();
    }

    public double getFunctionValue(double x) {
        if(x>=getLeftDomainBorder() && x<=getRightDomainBorder()){
            double x1 = getLeftDomainBorder();
            double x2 = getRightDomainBorder();
            double y1 = head.next.data.getY();
            double y2 = head.prev.data.getY();
            return (((x-x1)*(y2-y1))/(x2-x1))+y1;
        }
        else
            return Double.NaN;
    }

    public int getPointsCount() {
        return countOfNodes;
    }

    public FunctionPoint getPoint(int index) {
        if(index<0 || index>=countOfNodes) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        FunctionNode tmpNode = getNodeByIndex(index);
        return new FunctionPoint(tmpNode.data.getX(), tmpNode.data.getY());
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index<0 || index>=countOfNodes) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        FunctionNode tmp = getNodeByIndex(index);
        if((index == 0 && tmp.next.data.getX() > point.getX()) || (index == countOfNodes-1 && point.getX() > tmp.prev.data.getX()) || tmp.prev.data.getX() < point.getX() && point.getX() < tmp.next.data.getX()){
            tmp.data = point;
        }
        else {
            throw new InappropriateFunctionPointException("Illegal argument");
        }
    }

    public double getPointX(int index){
        if(index<0 || index>=countOfNodes) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        return getNodeByIndex(index).data.getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= countOfNodes) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        FunctionNode tmp = getNodeByIndex(index);
        if(tmp.prev.data.getX() <= x && x <= tmp.next.data.getX()){
            tmp.data.setX(x);
        }
        else {
            throw new InappropriateFunctionPointException("Illegal argument");
        }
    }

    public double getPointY(int index) {
        if(index<0 || index>=countOfNodes) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        return getNodeByIndex(index).data.getY();
    }

    public void setPointY(int index, double y) {
        if(index<0 || index>=countOfNodes) throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        getNodeByIndex(index).data.setY(y);
    }

    public void deletePoint(int index) {
        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode tmp = head.next;
        int index = 0;
        while(point.getX() > tmp.data.getX() && tmp != head.prev) {
            tmp = tmp.next;
            index++;
        }
        if(point.getX() == tmp.data.getX()) throw new InappropriateFunctionPointException("Illegal argument");
        if(tmp == head.prev){
            addNodeToTail();
        }
        else if(tmp == head.next){
            addNodeToHead();
        }
        else {
            addNodeByIndex(index);
        }
        cache.data.setX(point.getX());
        cache.data.setY(point.getY());
    }

    private void firs2Elements(FunctionPoint first, FunctionPoint second) {
        head = new FunctionNode();
        countOfNodes = 2;
        FunctionNode firstNode = new FunctionNode(null, null, first);
        FunctionNode secondNode = new FunctionNode(firstNode, firstNode, second);
        head.next = firstNode;
        head.prev = secondNode;
        firstNode.next = secondNode;
        firstNode.prev = secondNode;
    }

    public String toString(){
        String strings = "{ ";
        FunctionNode tmp = head.next;
        for(int i = 0; i<countOfNodes; i++){
            strings+= tmp.data.toString() + " ";
            tmp = tmp.next;
        }
        strings+= "}";
        return strings;
    }

    public boolean equals(Object obj){
        if(this == obj) return true;
        if (!(obj instanceof TabulatedFunction)) return false;
        if (obj instanceof LinkedListTabulatedFunction) {
            LinkedListTabulatedFunction aObj = (LinkedListTabulatedFunction) obj;
            if(aObj.countOfNodes != this.countOfNodes) return false;
            FunctionNode thisTmp = this.head.next;
            FunctionNode objTmp = aObj.head.next;
            for(int i = 0; i<countOfNodes; i++){
                if(!thisTmp.data.equals(objTmp.data)) return false;
                thisTmp = thisTmp.next;
                objTmp = objTmp.next;
            }
        }
        else{
            if(((TabulatedFunction) obj).getPointsCount() != countOfNodes) return false;
            FunctionNode thisTmp = this.head.next;
            for(int i = 0; i<countOfNodes; i++){
                if(!((TabulatedFunction) obj).getPoint(i).equals(thisTmp.data)) return false;
                thisTmp = thisTmp.next;
            }
        }
        return true;
    }

    public int hashCode(){
        int result = Objects.hashCode(countOfNodes);
        FunctionNode tmp = head.next;
        for(int i = 0; i<countOfNodes; i++){
            result = 31 * result + Objects.hash(tmp.data);
            tmp=tmp.next;
        }
        return result;
    }

    public Object clone()throws CloneNotSupportedException{
        FunctionPoint[] newNodes = new FunctionPoint[countOfNodes];
        for(int i = 0; i<countOfNodes; i++){
            newNodes[i] = new FunctionPoint(this.getPoint(i));
        }
        return new LinkedListTabulatedFunction(newNodes);
    }

    public Iterator<FunctionPoint> iterator() {
        return new Iterator<>() {
            private FunctionNode current = head.next;
            private boolean flag = true;

            public boolean hasNext() {
                if(flag && current.next != head.next)
                    return true;
                else if (current.next == head.next){
                    flag = false;
                    return true;
                }
                else
                    return false;

            }

            public FunctionPoint next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                FunctionPoint tmp = current.data;
                current = current.next;
                return new FunctionPoint(tmp);
            }

            public void remove() {
                throw new UnsupportedOperationException("This iterator can not delete");
            }
        };
    }

    public static class ListTabulatedFunction implements TabulatedFunctionFactory{

        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new LinkedListTabulatedFunction(points);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }
    }
}
