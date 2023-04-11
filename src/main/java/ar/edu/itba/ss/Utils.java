package ar.edu.itba.ss;

import java.util.Random;

public class Utils {
    public static Double tableWidth = 224.0;
    public static Double tableHeight = 112.0;
    public static Double particleMass = 165.0; // gramos
    public static Double particleRadius = 5.7/2;
    public static Double whiteBallIntialPosX = 56.0;
    public static Double whiteBallIntialPosY = 56.0;
    public static Double whiteBallIntialVelX = 200.0;
    public static Double whiteBallIntialVelY = 0.0;
    public static Double firstBallIntialPosX = 168.0;
    public static Double firstBallIntialPosY = 56.0;

    public static Double topEpsilon = 0.03;
    public static Double buttomEpsilon = 0.02;

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

    public static double moveInX = (Utils.particleRadius * 2) + (Utils.topEpsilon / 2);
    public static double moveInY = Utils.particleRadius + (Utils.topEpsilon / 2);

    public static double randomEpsilonValue() {
        Random random = new Random();
        return random.nextDouble() * (topEpsilon - buttomEpsilon) + buttomEpsilon;
    }
}
