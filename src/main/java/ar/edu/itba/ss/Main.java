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

    private static void createCollisions(Ball a) {
        if (a == null) return;

        //Check if ball with collide with another in given time
        for (Ball b: balls) {
            double timeToCollide = a.collides(b);
            events.add(new Event(currentTime + timeToCollide, EventType.BALL, a, b));
        }

        for (Ball hole: holes) {
            double timeToCollide = a.collides(hole);
            events.add(new Event(currentTime + timeToCollide, EventType.HOLE, a, hole));
        }

        // Check if ball will collide with horizontal wall
        double timeToHorizontalWall = a.collidesY();
        events.add(new Event(currentTime + timeToHorizontalWall, EventType.HWALL, null, a));

        // Check if ball will collide with vertical wall
        double timeToWallX = a.collidesX();
        events.add(new Event(currentTime + timeToWallX, EventType.VWALL, a, null));
    }

    private static void predict(Ball a) {
        if (a == null) return;

        // Filter events to only remain the ones that do not include ball a
        events.removeIf(e -> (e.getA() != null && e.getA().equals(a)) || (e.getB() != null && e.getB().equals(a)));

        if (ballsInHoles.contains(a)) return;

        createCollisions(a);
    }

    private static void simulate() {
        events = new PriorityQueue<>();
        // Populate PQ
        balls.stream().filter(b -> b.getType() == BallType.BALL).forEach(Main::createCollisions);
        int index = 0;

        while(events.size() > 0) {
            // Retrieve and delete impending event - will be one with minimum priority
            Event currentEvent = events.poll();

            if (Double.isNaN(currentEvent.getTime())) {
                System.out.println("NaN event: " + currentEvent + "in index: " + index);
                return;
            }

            // Invalidated events are discarded
            if (!currentEvent.isValid()) continue;

            // Collision occurred
            Ball a = currentEvent.getA();
            Ball b = currentEvent.getB();

            for (Ball ball : balls) {
                ball.move(currentEvent.getTime() - currentTime);
            }
            currentTime = currentEvent.getTime();

            // Analyze event

            switch (currentEvent.getEventType()) {
                case BALL:
                    a.bounce(b);
                    break;
                case HWALL:
                    b.bounceY();
                    break;
                case VWALL:
                    a.bounceX();
                    break;
                case HOLE:
                    Optional<Ball> ballInHole = isBallInHole(a, b);
                    if(ballInHole.isPresent()) {
                        System.out.println("Ball in hole: " + ballInHole.get().toString());
                        System.out.println("Balls in table: " + balls.size());
                        System.out.println("Iteration: " + index);
                        balls.remove(ballInHole.get());
                        ballsInHoles.add(ballInHole.get());
                        removeEventsWith(ballInHole.get());
                    }
                    break;
            }
            index++;

            predict(a);
            predict(b);

        }
        System.out.println("Balls size: " + balls.size());
    }

    private static void removeEventsWith(Ball toRemove) {
        events.removeIf(event -> (event.getA() != null && event.getA().equals(toRemove)) || (event.getB() != null && event.getB().equals(toRemove)));
    }

    private static Optional<Ball> isBallInHole(Ball a, Ball b) {
        if (a.getType() == BallType.BALL && b.getType() == BallType.BALL)
            return Optional.empty();
        if (a.getType() == BallType.BALL && b.getType() == BallType.HOLE)
            return Optional.of(a);
        if (b.getType() == BallType.BALL && a.getType() == BallType.HOLE)
            return Optional.of(b);
        return Optional.empty();
    }

    public static void main(String[] args) {
        Utils.initializeTable(holes, balls,
            Utils.whiteBallInitialPosX, Utils.whiteBallInitialPosY,
            Utils.whiteBallInitialVelX, Utils.whiteBallInitialVelY,
            Utils.firstBallInitialPosX, Utils.firstBallInitialPosY);

        System.out.println("Holes: ");
        for (Ball hole : holes) {
            System.out.println(hole);
        }
        // print all balls
        System.out.println("Balls: ");
        balls.forEach(System.out::println);

        simulate();
    }


}
