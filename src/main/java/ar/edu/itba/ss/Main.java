package ar.edu.itba.ss;

import java.util.PriorityQueue;

public class Main {
    private PriorityQueue<Event> events;
    private double currentTime = 0.0;
    private static Ball[] holes = new Ball[6];
    private static Ball[] balls = new Ball[16];

    //TODO: Seguir a partir de aca! Predict sirve para ver que colisiones van a suceder
    private void predict(Ball a) {
        //Check if ball with collide with another in given time
        for(Ball b: balls) {
            double timeToCollide = a.collides(b);
            a.setCollisionCount(a.getCollisionCount() + 1);
            events.add(new Event(currentTime + timeToCollide, a, b));
        }

        // Check if ball will collide with horizontal wall
        double timeToWallX = a.collidesX();
        a.setCollisionCount(a.getCollisionCount() + 1);
        events.add(new Event(currentTime + timeToWallX, null, a));

        // Check if ball will collide with vertical wall
        double timeToWallY = a.collidesX();
        a.setCollisionCount(a.getCollisionCount() + 1);
        events.add(new Event(currentTime + timeToWallY, a, null));
    }

    private void simulate() {
        events = new PriorityQueue<Event>();
        // Populate PQ
        for(Ball a: balls) {
            predict(a);
        }
        while(events.size() > 0) {
            // Retrieve and delete impending event - will be one with minimum priority
            Event currentEvent = events.poll();
            Ball a = currentEvent.getA();
            Ball b = currentEvent.getB();
            double eventTime = currentEvent.getTime();
            // Invalidated events are discarded
            if(!currentEvent.wasSuperveningEvent(a, b)) {
                // Collision between balls
                if(a != null && b!= null) {
                    a.move(eventTime);
                    b.move(eventTime);
                    a.bounce(b);
                    currentTime = eventTime;
                    predict(a);
                    predict(b);
                }
                // Collision with vertical wall
                else if(a != null){
                    a.move(eventTime);
                    a.bounceX();
                    currentTime = eventTime;
                    predict(a);
                }
                // Collision with horizontal wall
                else {
                    b.move(eventTime);
                    b.bounceY();
                    currentTime = eventTime;
                    predict(b);
                }
            }
        }
    }


    public static void main(String[] args) {
        //By default, the PQ is min with natural ordering

        //TODO: chequear que no haya bochas superpuestas!

//        initializeTable(holes, balls,
//                56, 56, 200, 0,
//                168, 56); // ==>
        Utils.initializeTable(holes, balls,
            Utils.whiteBallInitialPosX, Utils.whiteBallInitialPosY,
            Utils.whiteBallInitialVelX, Utils.whiteBallInitialVelY,
            Utils.firstBallInitialPosX, Utils.firstBallInitialPosY);
    }


}
