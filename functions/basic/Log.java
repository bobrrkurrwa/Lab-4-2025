package functions.basic;

import functions.Function;

public class Log implements Function {
    double base;

    public Log(double base) {
        if ( base < 0 || base ==1 ) throw new IllegalArgumentException("Log base is incorrect");
        this.base = base;
    }

    @Override
    public double getLeftDomainBorder() { return 0; }

    @Override
    public double getRightDomainBorder() { return Double.POSITIVE_INFINITY; }

    @Override
    public double getFunctionValue(double x) {
        if (x <= 0) return Double.NaN;

        return Math.log(x) / Math.log(base);
    }
}
