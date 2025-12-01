import functions.*;
import functions.basic.*;
import functions.meta.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Function sinFunc = new Sin();
            Function cosFunc = new Cos();

            // Часть 1: Вывод sin и cos
            System.out.println("sin(x) от 0 до π:");
            for(double x = 0; x <= Math.PI; x += 0.1) {
                System.out.printf("sin(%.1f) = %.6f%n", x, sinFunc.getFunctionValue(x));
            }

            System.out.println("\ncos(x) от 0 до π:");
            for(double x = 0; x <= Math.PI; x += 0.1) {
                System.out.printf("cos(%.1f) = %.6f%n", x, cosFunc.getFunctionValue(x));
            }

            // Часть 2: Табулированные аналоги
            TabulatedFunction sinTab = TabulatedFunctions.tabulate(sinFunc, 0, Math.PI, 10);
            TabulatedFunction cosTab = TabulatedFunctions.tabulate(cosFunc, 0, Math.PI, 10);

            System.out.println("\nСравнение точных и табулированных значений:");
            for(double x = 0; x <= Math.PI; x += 0.1) {
                double exactSin = sinFunc.getFunctionValue(x);
                double exactCos = cosFunc.getFunctionValue(x);
                double tabSin = sinTab.getFunctionValue(x);
                double tabCos = cosTab.getFunctionValue(x);

                System.out.printf("x=%.1f: sin=%.6f(tab=%.6f) cos=%.6f(tab=%.6f)%n",
                        x, exactSin, tabSin, exactCos, tabCos);
            }

            // Часть 3: Сумма квадратов
            Function sumSquares = Functions.sum(
                    Functions.power(sinTab, 2),
                    Functions.power(cosTab, 2)
            );

            System.out.println("\nСумма квадратов sin²+cos²:");
            for(double x = 0; x <= Math.PI; x += 0.1) {
                System.out.printf("x=%.1f: %.8f%n", x, sumSquares.getFunctionValue(x));
            }

            // Исследование количества точек
            System.out.println("\nВлияние количества точек на точность:");
            for(int points : new int[]{5, 10, 20, 50}) {
                TabulatedFunction testSin = TabulatedFunctions.tabulate(sinFunc, 0, Math.PI, points);
                TabulatedFunction testCos = TabulatedFunctions.tabulate(cosFunc, 0, Math.PI, points);
                Function testSum = Functions.sum(
                        Functions.power(testSin, 2),
                        Functions.power(testCos, 2)
                );

                double maxError = 0;
                for(double x = 0; x <= Math.PI; x += 0.05) {
                    double value = testSum.getFunctionValue(x);
                    if(!Double.isNaN(value)) {
                        double error = Math.abs(1.0 - value);
                        if(error > maxError) maxError = error;
                    }
                }
                System.out.printf("Точек: %d, Макс.ошибка: %.10f%n", points, maxError);
            }

            // Часть 4: Экспонента и символьные потоки
            TabulatedFunction expTab = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);
            String expFile = "exp.txt";

            try(FileWriter writer = new FileWriter(expFile)) {
                TabulatedFunctions.writeTabulatedFunction(expTab, writer);
            }

            TabulatedFunction readExp;
            try(FileReader reader = new FileReader(expFile)) {
                readExp = TabulatedFunctions.readTabulatedFunction(reader);
            }

            System.out.println("\nЭкспонента - сравнение:");
            for(int i = 0; i < expTab.getPointsCount(); i++) {
                double x = expTab.getPointX(i);
                double y1 = expTab.getPointY(i);
                double y2 = readExp.getFunctionValue(x);
                System.out.printf("x=%.1f: %.6f (исх.) vs %.6f (чит.)%n", x, y1, y2);
            }

            // Часть 5: Логарифм и байтовые потоки
            TabulatedFunction logTab = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
            String logFile = "log.dat";

            try(FileOutputStream out = new FileOutputStream(logFile)) {
                TabulatedFunctions.outputTabulatedFunction(logTab, out);
            }

            TabulatedFunction readLog;
            try(FileInputStream in = new FileInputStream(logFile)) {
                readLog = TabulatedFunctions.inputTabulatedFunction(in);
            }

            System.out.println("\nЛогарифм - сравнение:");
            for(int i = 0; i < logTab.getPointsCount(); i++) {
                double x = logTab.getPointX(i);
                double y1 = logTab.getPointY(i);
                double y2 = readLog.getFunctionValue(x);
                System.out.printf("x=%.1f: %.6f (исх.) vs %.6f (чит.)%n", x, y1, y2);
            }

            // Часть 6: Сериализация
            Function compFunc = Functions.composition(new Log(Math.E), new Exp());
            TabulatedFunction compTab = TabulatedFunctions.tabulate(compFunc, 0, 10, 11);
            String serFile = "composition.ser";

            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFile))) {
                oos.writeObject(compTab);
            }

            TabulatedFunction deserTab;
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serFile))) {
                deserTab = (TabulatedFunction) ois.readObject();
            }

            System.out.println("\nСериализация - сравнение (ln(e^x)=x):");
            boolean allMatch = true;
            for(double x = 0; x <= 10; x += 1.0) {
                double y1 = compTab.getFunctionValue(x);
                double y2 = deserTab.getFunctionValue(x);
                boolean match = Math.abs(y1 - y2) < 1e-10;
                if(!match) allMatch = false;
                System.out.printf("x=%.1f: %.6f vs %.6f (совп.: %b)%n", x, y1, y2, match);
            }
            System.out.println("Все совпадают: " + allMatch);


        } catch(Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}