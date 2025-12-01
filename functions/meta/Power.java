package functions.meta;

import functions.Function;

public class Power implements Function {
    private Function func;
    private double pow;

    public Power(Function func, double pow) {
        if(func == null) throw new IllegalArgumentException("Function must exist");
        this.func = func;
        this.pow = pow;
    }

    @Override
    public double getRightDomainBorder() { return func.getRightDomainBorder(); }

    @Override
    public double getLeftDomainBorder() { return func.getLeftDomainBorder(); }

    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;
        return Math.pow(func.getFunctionValue(x), pow);
    }
}
