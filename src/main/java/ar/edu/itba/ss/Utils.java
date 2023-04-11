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

    public static void intializeTable(Ball[] holes, Ball[] balls,
                                       double whiteBallIntialPosX,
                                       double whiteBallIntialPosY,
                                       double whiteBallIntialVelX,
                                       double whiteBallIntialVelY,
                                       double firstBallIntialPosX,
                                       double firstBallIntialPosY) {
        holes[0] = new Ball(0, Utils.tableHeight, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE);
        holes[1] = new Ball(Utils.tableWidth / 2, Utils.tableHeight,
                0, 0, Utils.particleRadius * 2, 0,
                BallType.HOLE);
        holes[2] = new Ball(Utils.tableWidth, Utils.tableHeight, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE);
        holes[3] = new Ball(0, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE);
        holes[4] = new Ball(Utils.tableWidth / 2, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE);
        holes[5] = new Ball(Utils.tableWidth, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE);

        //TODO: check velocity!!
        // white ball
        balls[0] = new Ball(whiteBallIntialPosX, whiteBallIntialPosY,
                whiteBallIntialVelX, whiteBallIntialVelY,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);

        balls[1] = new Ball(firstBallIntialPosX, firstBallIntialPosY, 0,
                0, Utils.particleRadius, Utils.particleMass, BallType.BALL);

        //TODO: check relative position!!
        //para entender como son sus posiciones preguntar a Santi con un dibujo
        balls[2] = new Ball(balls[0].getX() + Utils.moveInX,
                balls[0].getY() + Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[3] = new Ball(balls[0].getX() + Utils.moveInX,
                balls[0].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);

        balls[4] = new Ball(balls[2].getX() + Utils.moveInX,
                balls[2].getY() + Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        //black ball
        balls[5] = new Ball(balls[2].getX() + Utils.moveInX,
                balls[2].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[6] = new Ball(balls[3].getX() + Utils.moveInX,
                balls[3].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);

        balls[7] = new Ball(balls[4].getX() + Utils.moveInX,
                balls[4].getY() + Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[8] = new Ball(balls[4].getX() + Utils.moveInX,
                balls[4].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[9] = new Ball(balls[6].getX() + Utils.moveInX,
                balls[6].getY() + Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[10] = new Ball(balls[6].getX() + Utils.moveInX,
                balls[6].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);

        balls[11] = new Ball(balls[7].getX() + Utils.moveInX,
                balls[7].getY() + Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[12] = new Ball(balls[7].getX() + Utils.moveInX,
                balls[7].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[13] = new Ball(balls[9].getX() + Utils.moveInX,
                balls[9].getY() + Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[14] = new Ball(balls[9].getX() + Utils.moveInX,
                balls[9].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
        balls[15] = new Ball(balls[10].getX() + Utils.moveInX,
                balls[10].getY() - Utils.moveInY, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
    }
}
