package functions.basic;

import functions.Function;

public class Exp implements Function {
    @Override
    public double getLeftDomainBorder() { return Double.POSITIVE_INFINITY; }

    @Override
    public double getRightDomainBorder() { return Double.NEGATIVE_INFINITY; }

    @Override
    public double getFunctionValue(double x) { return Math.exp(x); }
}
