package functions;

import java.io.*;

public class TabulatedFunctions {

    private TabulatedFunctions(){}; // приватный конструктор для исключения возможности создания объекта данного класса

    public static TabulatedFunction tabulate(Function func, double leftX, double rightX, int pointsCount){
        if (func == null) throw new IllegalArgumentException("Function must exist");
        if (leftX > rightX) throw new IllegalArgumentException("Left border must be less than right border");
        if (pointsCount < 2) throw new IllegalArgumentException("Points count must be more than 2");

        FunctionPoint point[] = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i<pointsCount; i++){
            double x = leftX + i*step;
            double y = func.getFunctionValue(x);
            point[i] = new FunctionPoint(x,y);
        }
        return new ArrayTabulatedFunction(point);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {

        DataOutputStream cout = new DataOutputStream(out);
        cout.writeInt(function.getPointsCount());
        for (int i = 0; i<function.getPointsCount(); i++){
            FunctionPoint point = function.getPoint(i);
            cout.writeDouble(point.getX());
            cout.writeDouble(point.getY());
        }
        cout.flush();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException{
        DataInputStream cin = new DataInputStream(in);
        int pointsCount = cin.readInt();
        FunctionPoint points[] = new FunctionPoint[pointsCount];
        for (int i =0; i<pointsCount; i++){
            double x = cin.readDouble();
            double y = cin.readDouble();
            points[i] = new FunctionPoint(x,y);
        }
        return new ArrayTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        PrintWriter cout = new PrintWriter(out);
        int pointsCount = function.getPointsCount();
        cout.print(pointsCount);
        cout.print(" ");

        for (int i = 0; i < pointsCount; i++) {
            cout.print(function.getPointX(i));
            cout.print(" ");
            cout.print(function.getPointY(i));
            cout.print(" ");
        }
        cout.flush();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int pointsCount = (int) tokenizer.nval;

        FunctionPoint[] points = new FunctionPoint[pointsCount];

        for (int i = 0; i < pointsCount; i++) {
            tokenizer.nextToken();
            double x = tokenizer.nval;
            tokenizer.nextToken();
            double y = tokenizer.nval;
            points[i] = new FunctionPoint(x, y);
        }
        return new ArrayTabulatedFunction(points);
    }

}
