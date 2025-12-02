package functions;
import java.io.*;

//интерфейс взаимодействия с табулированной функцией (двусвязный список или массив)

public interface TabulatedFunction extends Function, Externalizable {

    // узнать количество точек
    int getPointsCount();

    // узнать значение точки по индексу
    FunctionPoint getPoint(int index);

    // установить значение точки по индексу
    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    // узнать значение координаты Х точки по индексу
    double getPointX(int index);

    // установить значение координаты Х точки по индексу
    void setPointX(int index, double x) throws InappropriateFunctionPointException;

    // узнать значение координаты Y точки по индексу
    double getPointY(int index);

    // задать значение координаты точки Y по индексу
    void setPointY(int index, double y);

    // удалить точку по индексу
    void deletePoint(int index);

    // добавить точку
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

}
