package functions.meta;

import functions.Function;

public class Sum implements Function {

    private Function func1;
    private Function func2;

    public Sum(Function func1, Function func2) {
        if (func1 == null || func2 == null) throw new IllegalArgumentException("Functions must exist");
        this.func1 = func1;
        this.func2 = func2;
    }

    @Override
    public double getRightDomainBorder() { return Math.min(func1.getRightDomainBorder(), func2.getRightDomainBorder()); }

    @Override
    public double getLeftDomainBorder() { return Math.max(func1.getLeftDomainBorder(), func2.getLeftDomainBorder()); }

    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;
        return func1.getFunctionValue(x) + func2.getFunctionValue(x);
    }
}
