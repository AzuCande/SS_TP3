package ar.edu.itba.ss;

public class Utils {
    public static Integer tableWidth;
    public static Integer tableHeight;
    public static Integer particleMass;
    public static Integer particleRadius;
    public static Integer initialXPosition;
    public static Integer initialYPosition;

    // Returns difference of velocities between two balls
    // Remember: v is a vector
    public static double[] getDeltaV(double vx1, double vy1, double vx2, double vy2) {
        double[] deltaV = new double[2];
        deltaV[0] = vx1 - vx2;
        deltaV[1] = vy1 - vy2;
        return deltaV;
    }

    // Returns difference of positions between two balls
    // Remember: r is a vector
    // TODO: Check if r means position
    public static double[] getDeltaR(double x1, double y1, double x2, double y2) {
        double[] deltaR = new double[2];
        deltaR[0] = x1 - x2;
        deltaR[1] = y1 - y2;
        return deltaR;
    }

    public static double getScalarProduct(double[] v1, double[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1];
    }
}
