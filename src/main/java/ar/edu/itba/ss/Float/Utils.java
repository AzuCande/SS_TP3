package ar.edu.itba.ss.Float;

import java.util.List;
import java.util.Random;

public class Utils {
    private static final Random random = new Random();
    public static Float tableWidth = 224.0F;
    public static Float tableHeight = 112.0F;
    public static Float particleMass = 165.0F; // gramos
    public static Float particleRadius = (float) (5.7 / 2);
    public static Float whiteBallInitialPosX = 56.0F;
    public static Float whiteBallInitialPosY = 56.0F;
    public static Float whiteBallInitialVelX = 200.0F;
    public static Float whiteBallInitialVelY = 0.0F;
    public static Float firstBallInitialPosX = 168.0F;
    public static Float firstBallInitialPosY = 56.0F;
    public static Float topEpsilon = 0.03F;
    public static Float bottomEpsilon = 0.02F;

    // Returns difference of velocities between two balls
    public static float[] getDeltaV(float vx1, float vy1, float vx2,
                                    float vy2) {
        float[] deltaV = new float[2];
        deltaV[0] = vx2 - vx1;
        deltaV[1] = vy2 - vy1;
        return deltaV;
    }

    // Returns difference of positions between two balls
    public static float[] getDeltaR(float x1, float y1, float x2,
                                    float y2) {
        float[] deltaR = new float[2];
        deltaR[0] = x2 - x1;
        deltaR[1] = y2 - y1;
        return deltaR;
    }

    public static float getScalarProduct(float[] v1, float[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1];
    }

    public static void initializeHoles(Ball[] holes) {

        holes[0] = new Ball(0, Utils.tableHeight, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");
        holes[1] = new Ball(Utils.tableWidth / 2, Utils.tableHeight,
                0, 0, Utils.particleRadius * 2, 0,
                BallType.HOLE, 169, 169, 169, "H");
        holes[2] = new Ball(Utils.tableWidth, Utils.tableHeight, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");
        holes[3] = new Ball(0, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");
        holes[4] = new Ball(Utils.tableWidth / 2, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");
        holes[5] = new Ball(Utils.tableWidth, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");

    }

    public static void initializeTable(List<Ball> balls,
                                       float whiteBallInitialPosX,
                                       float whiteBallInitialPosY,
                                       float whiteBallInitialVelX,
                                       float whiteBallInitialVelY,
                                       float firstBallInitialPosX,
                                       float firstBallInitialPosY) {

        // white ball
        balls.add(new Ball(whiteBallInitialPosX, whiteBallInitialPosY,
                whiteBallInitialVelX, whiteBallInitialVelY,
                Utils.particleRadius, Utils.particleMass, BallType.BALL, 255,
                255, 255, "He"));

        // triangle
        balls.add(new Ball(firstBallInitialPosX, firstBallInitialPosY, 0,
                0, Utils.particleRadius, Utils.particleMass, BallType.BALL,
                255, 255, 0, "Li"));
        balls.add(createBall(balls.get(1).getX(), balls.get(1).getY(), 1.0F, 0
                , 0, 255, "Be"));
        balls.add(createBall(balls.get(1).getX(), balls.get(1).getY(), -1.0F,
                255, 0, 0, "B"));
        balls.add(createBall(balls.get(2).getX(), balls.get(2).getY(), 1.0F, 128,
                0, 128, "C"));
        balls.add(createBall(balls.get(2).getX(), balls.get(2).getY(), -1.0F, 0
                , 0, 0, "N"));
        balls.add(
                createBall(balls.get(3).getX(), balls.get(3).getY(), -1.0F, 255,
                        165, 0, "O"));
        balls.add(createBall(balls.get(4).getX(), balls.get(4).getY(), 1.0F, 0,
                128, 0, "F"));
        balls.add(
                createBall(balls.get(4).getX(), balls.get(4).getY(), -1.0F, 165,
                        42, 42, "Ne"));
        balls.add(
                createBall(balls.get(5).getX(), balls.get(5).getY(), -1.0F, 139,
                        69, 19, "Na"));
        balls.add(
                createBall(balls.get(6).getX(), balls.get(6).getY(), -1.0F, 173,
                        255, 47, "Fe"));
        balls.add(createBall(balls.get(7).getX(), balls.get(7).getY(), 1.0F, 173,
                216, 230, "Co"));
        balls.add(
                createBall(balls.get(7).getX(), balls.get(7).getY(), -1.0F, 199,
                        21, 133, "Ni"));
        balls.add(
                createBall(balls.get(8).getX(), balls.get(8).getY(), -1.0F, 139,
                        0, 0, "Cu"));
        balls.add(createBall(balls.get(9).getX(), balls.get(9).getY(), -1.0F, 34,
                139, 34, "Zn"));
        balls.add(createBall(balls.get(10).getX(), balls.get(10).getY(), -1.0F
                , 128, 128, 0, "Na"));

    }

    public static void perturbateBalls(List<Ball> balls,
                                       float whiteBallInitialPosX,
                                       float whiteBallInitialPosY,
                                       float whiteBallInitialVelX,
                                       float whiteBallInitialVelY) {
        for (Ball ball : balls) {
            if (ball.getX() == whiteBallInitialPosX &&
                    ball.getY() == whiteBallInitialPosY &&
                    ball.getVx() == whiteBallInitialVelX &&
                    ball.getVy() == whiteBallInitialVelY) {
                continue;
            }
            perturbBall(ball);
        }
    }

    private static Ball createBall(float relativeBallX, float relativeBallY
            , float sign, int colorR, int colorG, int colorB, String symbol) {
        float hypothenus = 2 * (Utils.particleRadius + Utils.topEpsilon);
        float moveInX = (float) (hypothenus * Math.cos(Math.toRadians(30)));
        float moveInY = (float) (hypothenus * Math.sin(Math.toRadians(30)));
        return new Ball(relativeBallX + moveInX,
                (float) (relativeBallY + moveInY * sign),
                0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL, colorR,
                colorG, colorB, symbol);
    }

    private static void perturbBall(Ball ball) {
        float epsilon = bottomEpsilon +
                (topEpsilon - bottomEpsilon) * random.nextFloat();
        float moveInX = epsilon * (random.nextBoolean() ? 1 : -1);
        float moveInY = epsilon * (random.nextBoolean() ? 1 : -1);

        ball.setX(ball.getX() + moveInX);
        ball.setY(ball.getY() + moveInY);
    }
}
