package functions;

// интерфейс взаимодействия с функцией одной переменной Х

public interface Function {

    // узнать левую границу функции
    double getLeftDomainBorder();

    // узнать правую границу функции
    double getRightDomainBorder();

    // узнать значение Х
    double getFunctionValue(double x); }
