package functions.meta;

import functions.Function;

public class Scale implements Function {
    private Function func;
    private double scaleX;
    private double scaleY;

    public Scale(Function func, double scaleX, double scaleY) {
        if(func == null) throw new IllegalArgumentException("Function must exist");
        this.func = func;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public double getLeftDomainBorder() {
        if(scaleX > 0) return func.getLeftDomainBorder()/scaleX;
        else if (scaleX < 0) return func.getRightDomainBorder()/scaleX;
        else return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getRightDomainBorder() {
        if(scaleX > 0) return func.getRightDomainBorder()/scaleX;
        else if (scaleX < 0) return func.getLeftDomainBorder()/scaleX;
        else return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) return Double.NaN;
        return func.getFunctionValue(x * scaleX) * scaleY;
    }
}