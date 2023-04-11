package ar.edu.itba.ss;

import java.util.PriorityQueue;

public class Main {
    private PriorityQueue<Event> events;
    private double time = 0.0;
    private static Ball[] holes = new Ball[6];
    private static Ball[] balls = new Ball[16];

    public static void main(String[] args) {
        //By default, the PQ is min with natural ordering

        //TODO: chequear que no haya bochas superpuestas!

//        intializeTable(holes, balls,
//                56, 56, 200, 0,
//                168, 56); // ==>
        Utils.intializeTable(holes, balls,
            Utils.whiteBallIntialPosX, Utils.whiteBallIntialPosY,
            Utils.whiteBallIntialVelX, Utils.whiteBallIntialVelY,
            Utils.firstBallIntialPosX, Utils.firstBallIntialPosY);
    }


}
