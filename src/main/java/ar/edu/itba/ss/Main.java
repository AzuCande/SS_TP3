package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class Main {
    private PriorityQueue<Event> events;
    private double currentTime = 0.0;
    private static final Ball[] holes = new Ball[6];
    private static final List<Ball> balls = new ArrayList<>();
    public static final List<Ball> ballsInHoles = new ArrayList<>();

    // TODO: Check if separate first prediction from the rest (in the rest cases, update the events)
    private void predict(Ball a) {
        //Check if ball with collide with another in given time
        for(Ball b: balls) {
            double timeToCollide = a.collides(b);
            a.setCollisionCount(a.getCollisionCount() + 1);
            events.add(new Event(currentTime + timeToCollide, a, b));
        }

        // Check if ball will collide with horizontal wall
        double timeToWallY = a.collidesY();
        a.setCollisionCount(a.getCollisionCount() + 1);
        events.add(new Event(currentTime + timeToWallY, null, a));

        // Check if ball will collide with vertical wall
        double timeToWallX = a.collidesX();
        a.setCollisionCount(a.getCollisionCount() + 1);
        events.add(new Event(currentTime + timeToWallX, a, null));
    }

    private void simulate() {
        events = new PriorityQueue<>();
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
            if(currentEvent.isValid()) {
                // Collision between balls
                if(a != null && b!= null) {
                    Optional<Ball> ballInHole = isBallInHole(a, b);
                    if(ballInHole.isPresent()) {
                        balls.remove(ballInHole.get());
                        ballsInHoles.add(ballInHole.get());
                        removeEventsWith(ballInHole.get());
                    }
                    else {
                        a.move(eventTime);
                        b.move(eventTime);
                        a.bounce(b);
                        currentTime = eventTime;
                        predict(a);
                        predict(b);
                    }
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

    private void removeEventsWith(Ball toRemove) {
        events.removeIf(event -> event.getA().equals(toRemove) || event.getB().equals(toRemove));
    }

    private Optional<Ball> isBallInHole(Ball a, Ball b) {
        if (a.getType() == BallType.BALL && b.getType() == BallType.BALL)
            return Optional.empty();
        if (a.getType() == BallType.BALL && b.getType() == BallType.HOLE)
            return Optional.of(a);
        if (b.getType() == BallType.BALL && a.getType() == BallType.HOLE)
            return Optional.of(b);
        return Optional.empty();
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
