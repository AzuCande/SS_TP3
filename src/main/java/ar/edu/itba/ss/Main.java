package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class Main {
    private static PriorityQueue<Event> events;
    private static double currentTime = 0.0;
    private static final Ball[] holes = new Ball[6];
    private static final List<Ball> balls = new ArrayList<>();
    public static final List<Ball> ballsInHoles = new ArrayList<>();

    // TODO: Check if separate first prediction from the rest (in the rest cases, update the events)
    private static void createEvents(Ball a) {
        //Check if ball with collide with another in given time
        for(Ball b: balls) {
            if (a.getType() == BallType.HOLE && b.getType() == BallType.HOLE) continue;
            double timeToCollide = a.collides(b);
            a.setCollisionCount(a.getCollisionCount() + 1);
            events.add(new Event(currentTime + timeToCollide, a, b));
        }

        // Check if ball will collide with horizontal wall
        double timeToWallY = a.collidesY();
        Event eventToAdd = new Event(currentTime + timeToWallY, null, a);
        if (!events.contains(eventToAdd)) {
            events.add(eventToAdd);
        }

        // Check if ball will collide with vertical wall
        double timeToWallX = a.collidesX();
        eventToAdd = new Event(currentTime + timeToWallX, a, null);
        if (!events.contains(eventToAdd)) {
            events.add(eventToAdd);
        }
    }

    private static void predict(Ball affectedBall) {
        if (affectedBall == null) return;
        for (Event e : events) {
            if (e.getA() != null && e.getA().equals(affectedBall)) {
                if (e.getB() == null)
                    e.updateEvent(currentTime + affectedBall.collidesX());
                else
                    e.updateEvent(currentTime + affectedBall.collides(e.getB()));
            }
            if (e.getB() != null && e.getB().equals(affectedBall)) {
                if (e.getA() == null)
                    e.updateEvent(currentTime + affectedBall.collidesY());
                else
                    e.updateEvent(currentTime + affectedBall.collides(e.getA()));
            }
        }
    }

    private void simulate() {
        events = new PriorityQueue<>();
        // Populate PQ
        for(Ball a: balls) {
            createEvents(a);
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
                    System.out.println("Is ball in hole? " + ballInHole.isPresent());
                    if (ballInHole.isPresent()) {
                        System.out.println("Ball with id " + ballInHole.get().getId() + " entered hole");
                        balls.remove(ballInHole.get());
                        ballsInHoles.add(ballInHole.get());
                        removeEventsWith(ballInHole.get());
                        System.out.println("There are " + balls.size() + "now in the game");
                        currentTime = eventTime;
                    }
                    else {
                        a.move(eventTime);
                        b.move(eventTime);
                        a.bounce(b);
                        currentTime = eventTime;
                        events.add(new Event(currentTime + a.collides(b), a, b));
                    }
                }
                // Collision with vertical wall
                else if(a != null){
                    a.move(eventTime);
                    a.bounceX();
                    currentTime = eventTime;
                    events.add(new Event(currentTime + a.collidesX(), a, null));
                }
                // Collision with horizontal wall
                else {
                    b.move(eventTime);
                    b.bounceY();
                    currentTime = eventTime;
                    events.add(new Event(currentTime + b.collidesY(), null, b));
                }
                if(a == null) {
                    System.out.printf("%.2f - Wall, ((%.2f, %.2f)%n", currentTime, b.getX(), b.getY());
                }
                else if(b == null) {
                    System.out.printf("%.2f - ((%.2f, %.2f), Wall%n", currentTime, a.getX(), a.getY());
                }
                else {
                    System.out.printf("%.2f - (%.2f, %.2f), ((%.2f, %.2f)%n", currentTime, a.getX(), a.getY(), b.getX(), b.getY());
                    System.out.printf("BOLA BLANCA: %.2f - (%.2f, %.2f) v = ((%.2f, %.2f)%n", currentTime, balls.get(0).getX(), balls.get(0).getY(), balls.get(0).getVx(), balls.get(0).getVx());
                }

                predict(a);
                predict(b);
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
