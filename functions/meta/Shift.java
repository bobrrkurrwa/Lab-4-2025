package functions.meta;

import functions.Function;

public class Shift implements Function {
    private Function func;
    private double shiftX;
    private double shiftY;

    public Shift(Function func, double shiftX, double shiftY) {
        if(func == null) throw new IllegalArgumentException("Function must exist");
        this.func = func;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    @Override
    public double getRightDomainBorder() { return func.getRightDomainBorder() + shiftX; }

    @Override
    public double getLeftDomainBorder() { return func.getLeftDomainBorder() + shiftX; }

    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;
        return func.getFunctionValue(x - shiftX) + shiftY;
    }
}
