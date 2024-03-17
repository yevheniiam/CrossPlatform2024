package laba1_3;

public class TestClass {
    private double x;

    public TestClass(double x) {
        this.x = x;
    }

    public double sinSquare() {
        return Math.pow(Math.sin(this.x), 2.0);
    }
}