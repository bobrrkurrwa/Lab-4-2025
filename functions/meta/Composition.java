package functions.meta;

import functions.Function;

public class Composition implements Function {
    private Function funcIn;
    private Function funcOut;

    public Composition(Function funcIn, Function funcOut) {
        if (funcIn == null || funcOut == null) throw new IllegalArgumentException("Functions must exist");
        this.funcIn = funcIn;
        this.funcOut = funcOut;
    }

    @Override
    public double getRightDomainBorder() { return funcIn.getRightDomainBorder(); }

    @Override
    public double getLeftDomainBorder() { return funcIn.getLeftDomainBorder(); }

    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;
        return funcOut.getFunctionValue(funcIn.getFunctionValue(x));
    }
}