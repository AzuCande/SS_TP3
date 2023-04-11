package ar.edu.itba.ss;

public class Main {

    public static void main(String[] args) {
        //TODO: chequear que no haya bochas superpuestas!
        Ball[] holes = new Ball[6];
        Ball[] balls = new Ball[16];
//        intializeTable(holes, balls,
//                56, 56, 200, 0,
//                168, 56); // ==>
        intializeTable(holes, balls,
            Utils.whiteBallIntialPosX, Utils.whiteBallIntialPosY,
            Utils.whiteBallIntialVelX, Utils.whiteBallIntialVelY,
            Utils.firstBallIntialPosX, Utils.firstBallIntialPosY);


    }

    private static void intializeTable(Ball[] holes, Ball[] balls,
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
