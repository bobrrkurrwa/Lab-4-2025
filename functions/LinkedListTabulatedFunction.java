package functions;
import java.io.*;

public class LinkedListTabulatedFunction implements TabulatedFunction {

    public static final double EPSILON = 1e-10;

    private static class FunctionNode {

        // узлы
        private FunctionNode next;
        private FunctionNode prev;

        //  информац. поле
        private FunctionPoint point;

        // конструкторы
        private FunctionNode(FunctionPoint point){
            this.point = point;
            this.next = null;
            this.prev = null;
        }

        private FunctionNode(FunctionPoint point, FunctionNode prev, FunctionNode next){
            this.point = point;
            this.prev = prev;
            this.next = next;
        }

        private FunctionNode(FunctionNode part){
            this.point = new FunctionPoint(part.point);
            this.next = null;
            this.prev = null;
        }



        // геттеры для точки и узлов
        public FunctionPoint getPoint() { return point; }
        public FunctionNode getNext(){ return next; }
        public FunctionNode getPrev(){ return prev; }

        //сеттеры для точки и узлов
        public void setPoint(FunctionPoint point){ this.point = point; }
        public void setNext(FunctionNode next){ this.next = next; }
        public void setPrev(FunctionNode prev){ this.prev = prev; }



    }

    public LinkedListTabulatedFunction(FunctionPoint[] points){
        if(points.length < 2){
            throw new IllegalArgumentException("Points count must be more than two");
        }
        for (int i = points.length - 1; i > 0; i--){
            if (points[i].getX() < points[i-1].getX() - EPSILON){
                throw new IllegalArgumentException("Points must be in order");
            }
        }
        this.pointsCount = points.length;
        this.head = new FunctionNode(new FunctionPoint(0,0));
        head.next = head;
        head.prev = head;

        FunctionNode current = head;
        for (int i = 0; i < pointsCount; i++) {

            FunctionNode newNode = new FunctionNode(new FunctionPoint(points[i]));

            newNode.prev = current;
            newNode.next = head;
            current.next = newNode;
            head.prev = newNode;
            current = newNode;
        }

        this.lastIndex = 0;
        this.lastNode = head.next;
    }
    public LinkedListTabulatedFunction() {
        this.head = null;
        this.pointsCount = 0;
        this.lastIndex = -1;
        this.lastNode = null;
    }
    private FunctionNode head = new FunctionNode(new FunctionPoint(0, 0)); //голова списка
    private int pointsCount;
    private int lastIndex;
    private FunctionNode lastNode;

    // достать узел из списка по индексу
    private FunctionNode getNodeByIndex(int index){
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }
        if (lastNode != null && lastIndex == index ){
            return lastNode;
        }

        FunctionNode temp;
        if (index > pointsCount / 2){
            temp = head.prev;
            for (int i = pointsCount -1; i > index; i--){
                temp = temp.prev;
            }
        } else {
            temp = head.next;
            for (int i = 0; i < index; i++){
                temp = temp.next;
            }
        }

        lastIndex = index;
        lastNode = temp;
        return lastNode;
    }

    // добавить узел в конец списка
    private FunctionNode addNodeToTail(FunctionPoint point){
        FunctionNode newNode = new FunctionNode(point);
        newNode.prev = head.prev;
        head.prev.next = newNode;
        newNode.next = head;
        head.prev = newNode;
        pointsCount++;
        return newNode;
    }

    // добавить узел по индексу
    private FunctionNode addNodeByIndex(int index, FunctionPoint point){
        if (index > pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }
        FunctionNode newNode = new FunctionNode(point);
        FunctionNode temp = getNodeByIndex(index);
        newNode.prev = temp.prev;
        newNode.next = temp;
        temp.prev.next = newNode;
        temp.prev = newNode;
        pointsCount++;

        return newNode;
    }

    // удалить узел по индексу
    private FunctionNode deleteNodeByIndex(int index){
        if (index >= pointsCount || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException("Index must not be beyond borders");
        }
        FunctionNode temp = getNodeByIndex(index);
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        return temp;
    }

    // создание табулированной функции
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX) throw new IllegalArgumentException("Left border must be less than right border");
        if (pointsCount < 2) throw new IllegalArgumentException("Points count must be more than 2");

        this.pointsCount = pointsCount;
        double step = (rightX - leftX) / (pointsCount - 1);

        this.head = new FunctionNode(new FunctionPoint(0, 0));
        head.next = head;
        head.prev = head;

        FunctionNode current = head;
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + step * i;
            FunctionNode newNode = new FunctionNode(new FunctionPoint(x, 0));

            newNode.prev = current;
            newNode.next = head;
            current.next = newNode;
            head.prev = newNode;
            current = newNode;
        }

        this.lastIndex = 0;
        this.lastNode = head.next;
    }

    // создание табулированной функции со значениями игрек из массива
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX) throw new IllegalArgumentException("Left border must be less than right border");
        if (values.length < 2) throw new IllegalArgumentException("Points count must be more than 2");

        this.pointsCount = values.length;
        double step = (rightX - leftX) / (values.length - 1);

        this.head = new FunctionNode(new FunctionPoint(0, 0));
        head.next = head;
        head.prev = head;

        FunctionNode current = head;
        for (int i = 0; i < values.length; i++) {
            double x = leftX + step * i;
            FunctionNode newNode = new FunctionNode(new FunctionPoint(x, values[i]));

            newNode.prev = current;
            newNode.next = head;
            current.next = newNode;
            head.prev = newNode;
            current = newNode;
        }

        this.lastIndex = 0;
        this.lastNode = head.next;
    }

    // левая граница области опредления
    public double getLeftDomainBorder() { return head.next.point.getX(); }

    // правая граница области определения
    public double getRightDomainBorder() { return head.prev.point.getX(); }

    // полученеи кол-ва точек
    public int getPointsCount() { return pointsCount; }

    // вычисление значения функции в точке x
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        FunctionNode current = lastNode;
        int startIndex = lastIndex;

        // Оптимизация: определяем направление поиска от lastNode
        if (x < current.point.getX()) {
            while (current != head && current.point.getX() > x) {
                current = current.prev;
                startIndex--;
            }
        } else if (x > current.point.getX()) {
            while (current != head.prev && current.next.point.getX() < x) {
                current = current.next;
                startIndex++;
            }
        }

        // совпадение
        if (Math.abs(current.point.getX() - x) < EPSILON) {
            lastIndex = startIndex;
            lastNode = current;
            return current.point.getY();
        }

        // линейная интерполяция
        FunctionNode next = current.next;
        if (next == head) next = head.next;

        double slope = (next.point.getY() - current.point.getY()) /
                (next.point.getX() - current.point.getX());
        double result = current.point.getY() + slope * (x - current.point.getX());

        lastIndex = startIndex;
        lastNode = current;
        return result;
    }

    // получение точки по индексу
    public FunctionPoint getPoint(int index) {
        FunctionNode node = getNodeByIndex(index);
        return new FunctionPoint(node.point);
    }

    // установка точки по индексу
    public void setPoint(int index, FunctionPoint point) {
        FunctionNode node = getNodeByIndex(index);

        if (index > 0 && point.getX() <= node.prev.point.getX()) {
            throw new InappropriateFunctionPointException("X must be greater than previous point's X");
        }
        if (index < pointsCount - 1 && point.getX() >= node.next.point.getX()) {
            throw new FunctionPointIndexOutOfBoundsException("X must be less than next point's X");
        }

        node.point = new FunctionPoint(point);
    }

    // установка координаты X точки по индексу
    public void setPointX(int index, double x) {
        FunctionNode node = getNodeByIndex(index);

        if (index > 0 && x <= node.prev.point.getX()) {
            throw new InappropriateFunctionPointException("X must be greater than previous point's X");
        }
        if (index < pointsCount - 1 && x >= node.next.point.getX()) {
            throw new InappropriateFunctionPointException("X must be less than next point's X");
        }

        node.point.setX(x);
    }

    // установка координаты Y точки по индексу
    public void setPointY(int index, double y) {
        FunctionNode node = getNodeByIndex(index);
        node.point.setY(y);
    }

    // получение координаты X точки по индексу
    public double getPointX(int index) { return getNodeByIndex(index).point.getX(); }

    // получение координаты Y точки по индексу
    public double getPointY(int index) { return getNodeByIndex(index).point.getY(); }

    // удаление точки по индексу
    public void deletePoint(int index) {
        if (pointsCount < 3) {
            throw new IllegalStateException("Points count must be more than two");
        }

        FunctionNode deleted = deleteNodeByIndex(index);
        pointsCount--;

        // Сброс кэша при удалении закэшированного узла
        if (index == lastIndex) {
            lastIndex = -1;
            lastNode = null;
        }
    }

    // добавление новой точки (оптимизированная версия с использованием lastNode)
    public void addPoint(FunctionPoint point) {
        int newIndex = 0;
        FunctionNode current = (lastNode != null) ? lastNode : head.next;
        int currentIndex = (lastNode != null) ? lastIndex : 0;

        // Оптимизация: поиск позиции начинается с lastNode
        while (current != head && current.point.getX() < point.getX()) {
            current = current.next;
            currentIndex++;
        }

        if (current != head && Math.abs(current.point.getX() - point.getX()) < EPSILON) {
            throw new InappropriateFunctionPointException("This X is already here");
        }

        addNodeByIndex(currentIndex, point);
        lastIndex = currentIndex;
        lastNode = getNodeByIndex(currentIndex);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(pointsCount);
        FunctionNode current = head.next;

        while (current != head) {
            out.writeDouble(current.point.getX());
            out.writeDouble(current.point.getY());
            current = current.next;
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        head.next = head;
        head.prev = head;
        pointsCount = 0;

        int pointsCount = in.readInt();

        for (int i = 0; i < pointsCount; i++)
        {
            double x = in.readDouble();
            double y = in.readDouble();
            addNodeToTail(new FunctionPoint(x, y));
        }
    }


}