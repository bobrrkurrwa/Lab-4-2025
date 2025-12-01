package functions;
import functions.meta.*;

/*

вспомогательный класс, необходимый для проведения математических операций с различными видами функций,
не предусматривающий создание объектов типа данного класса

*/

public class Functions {
    private Functions(){}
    // для того, чтобы возможности создания объекта вне класса не возникло, ограничиваем модификатор доступа конструктора на private

    // доступные математические операции

    // сумма функций
    public static Function sum(Function func1, Function func2) { return new Sum(func1, func2); }

    // умножение функций
    public static Function mult(Function func1, Function func2) { return new Mult(func1, func2); }

    // возведение функции в степень
    public static Function power(Function func, double power) { return new Power(func, power); }

    // масштабирование вдоль осей ХоY
    public static Function scale(Function func, double scaleX, double scaleY) { return new Scale(func, scaleX, scaleY); }

    // сдвиг осей ХоY
    public static Function shift(Function func, double shiftX, double shiftY) { return new Shift(func, shiftX, shiftY); }

    // композиция двух функций
    public static Function composition(Function func1, Function func2) { return new Composition(func1, func2); }

}