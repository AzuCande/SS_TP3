package ar.edu.itba.ss;

import java.util.List;
import java.util.Random;

public class Utils {
    public static Double tableWidth = 224.0;
    public static Double tableHeight = 112.0;
    public static Double particleMass = 165.0; // gramos
    public static Double particleRadius = 5.7/2;
    public static Double whiteBallInitialPosX = 56.0;
    public static Double whiteBallInitialPosY = 56.0;
    public static Double whiteBallInitialVelX = 200.0;
    public static Double whiteBallInitialVelY = 0.0;
    public static Double firstBallInitialPosX = 168.0;
    public static Double firstBallInitialPosY = 56.0;

    public static Double topEpsilon = 0.03;
    public static Double bottomEpsilon = 0.02;

    private static final Random random = new Random();

    // Returns difference of velocities between two balls
    public static double[] getDeltaV(double vx1, double vy1, double vx2, double vy2) {
        double[] deltaV = new double[2];
        deltaV[0] = vx1 - vx2;
        deltaV[1] = vy1 - vy2;
        return deltaV;
    }

    // Returns difference of positions between two balls
    public static double[] getDeltaR(double x1, double y1, double x2, double y2) {
        double[] deltaR = new double[2];
        deltaR[0] = x1 - x2;
        deltaR[1] = y1 - y2;
        return deltaR;
    }

    public static double getScalarProduct(double[] v1, double[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1];
    }

    public static void initializeTable(Ball[] holes, List<Ball> balls,
                                       double whiteBallInitialPosX,
                                       double whiteBallInitialPosY,
                                       double whiteBallInitialVelX,
                                       double whiteBallInitialVelY,
                                       double firstBallInitialPosX,
                                       double firstBallInitialPosY) {
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

        // white ball
        balls.add(new Ball(whiteBallInitialPosX, whiteBallInitialPosY,
                whiteBallInitialVelX, whiteBallInitialVelY,
                Utils.particleRadius, Utils.particleMass, BallType.BALL));

        // triangle
        balls.add(new Ball(firstBallInitialPosX, firstBallInitialPosY, 0,
                0, Utils.particleRadius, Utils.particleMass, BallType.BALL));
        balls.add(createBall(balls.get(1).getX(), balls.get(1).getY(), 1.0));
        balls.add(createBall(balls.get(1).getX(), balls.get(1).getY(), -1.0));
        balls.add(createBall(balls.get(2).getX(), balls.get(2).getY(), 1.0));
        balls.add(createBall(balls.get(2).getX(), balls.get(2).getY(), -1.0));
        balls.add(createBall(balls.get(3).getX(), balls.get(3).getY(), -1.0));
        balls.add(createBall(balls.get(4).getX(), balls.get(4).getY(), 1.0));
        balls.add(createBall(balls.get(4).getX(), balls.get(4).getY(), -1.0));
        balls.add(createBall(balls.get(5).getX(), balls.get(5).getY(), -1.0));
        balls.add(createBall(balls.get(6).getX(), balls.get(6).getY(), -1.0));
        balls.add(createBall(balls.get(7).getX(), balls.get(7).getY(), 1.0));
        balls.add(createBall(balls.get(7).getX(), balls.get(7).getY(), -1.0));
        balls.add(createBall(balls.get(8).getX(), balls.get(8).getY(), -1.0));
        balls.add(createBall(balls.get(9).getX(), balls.get(9).getY(), -1.0));
        balls.add(createBall(balls.get(10).getX(), balls.get(10).getY(), -1.0));
    }

    private static Ball createBall(double relativeBallX, double relativeBallY, double sign) {
        double epsilon = bottomEpsilon + (topEpsilon - bottomEpsilon) * random.nextDouble();
        double moveInX = (Utils.particleRadius * 2) + (epsilon / 2);
        double moveInY = Utils.particleRadius + (epsilon / 2);
        return new Ball(relativeBallX + moveInX, relativeBallY + moveInY * sign, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL);
    }
}
