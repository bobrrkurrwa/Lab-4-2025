package functions;

public class FunctionPoint {

    private double x;
    private double y;

    // геттеры точек
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    // сетеры значений точек
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }

    // конструктор с заданными координатами
    public FunctionPoint(double x, double y)
    {   this.x = x;
        this.y = y; }

    public FunctionPoint(FunctionPoint point){ // конструктор копирования
        this(point.x, point.y);
    }
    public FunctionPoint(){ // конструктор по умолчанию (точка 0,0)
        this(0.0, 0.0);
    }
}
