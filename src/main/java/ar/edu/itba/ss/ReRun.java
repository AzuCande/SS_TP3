package ar.edu.itba.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class ReRun {
    private static PriorityQueue<Event> reRunEvents;
    private static double reRunCurrentTime = 0.0;
    private static final Ball[] reRunHoles = new Ball[6];
    private static final List<Ball> reRunBalls = new ArrayList<>();
    public static final List<Ball> reRunBallsInHoles = new ArrayList<>();
    private static final int reRunFileNumber = 1;

    private static void createCollisions(Ball a) {
        if (a == null) return;
        if (a.getType() == BallType.HOLE) return;
        if (reRunBallsInHoles.contains(a)) return;

        //Check if ball with collide with another in given time
        for (Ball b : reRunBalls) {
            double timeToCollide = a.collides(b);
            reRunEvents.add(new Event(reRunCurrentTime + timeToCollide,
                    EventType.BALL, a,
                    b));
        }

        for (Ball hole : reRunHoles) {
            double timeToCollide = a.collides(hole);
            reRunEvents.add(new Event(reRunCurrentTime + timeToCollide,
                    EventType.HOLE, a,
                    hole));
        }

        // Check if ball will collide with horizontal wall
        double timeToHorizontalWall = a.collidesY();
        reRunEvents.add(
                new Event(reRunCurrentTime + timeToHorizontalWall,
                        EventType.HWALL,
                        null, a));

        // Check if ball will collide with vertical wall
        double timeToWallX = a.collidesX();
        reRunEvents.add(
                new Event(reRunCurrentTime + timeToWallX, EventType.VWALL, a,
                        null));
    }

    private static void updateEventsTime(Ball a) {
        if (a == null || a.getType() == BallType.HOLE) return;
        if (reRunBallsInHoles.contains(a)) return;

        // Update time of events that do not contain ball a
        for (Event e : reRunEvents) {
            if ((e.getA() != null && e.getA().equals(a)) ||
                    (e.getB() != null && e.getB().equals(a))) {
                continue;
            }
            switch (e.getEventType()) {
                case BALL:
                case HOLE:
                    e.updateTime(reRunCurrentTime + e.getA().collides(e.getB()));
                    break;
                case HWALL:
                    e.updateTime(reRunCurrentTime + e.getB().collidesY());
                    break;
                case VWALL:
                    e.updateTime(reRunCurrentTime + e.getA().collidesX());
                    break;
            }
        }
    }

    private static void predict(Ball a) {
        if (a == null) return;
        if (a.getType() == BallType.HOLE) return;
        if (reRunBallsInHoles.contains(a)) return;

        // Filter events to only remain the ones that do not include ball a
        reRunEvents.removeIf(e -> (e.getA() != null && e.getA().equals(a)) ||
                (e.getB() != null && e.getB().equals(a)));

        if (reRunBallsInHoles.contains(a)) return;

        createCollisions(a);
    }

    public static void simulate(File fileAnimationFile) {
        reRunEvents = new PriorityQueue<>();
        // Populate PQ
//        reRunBalls
//                .stream()
//                .filter(b -> b.getType() == BallType.BALL)
//                .forEach(ReRun::createCollisions);
        reRunBalls.forEach(ReRun::createCollisions);
        int index = 0;

        while (reRunEvents.size() > 0) {
            // Retrieve and delete impending event - will be one with minimum priority
            if (reRunBalls.isEmpty()) {
                System.out.println("Finished simulation with " + index +
                        " iterations");
                return;
            }

            Event currentEvent = reRunEvents.poll();
            FilesParser.writeAnimationFile(fileAnimationFile, index, reRunBalls,
                    List.of(reRunHoles));

//            if (Double.isNaN(currentEvent.getTime())) {
//                System.out.println(
//                        "NaN event: " + currentEvent + "in index: " + index);
//                return;
//            }


            // Invalidated events are discarded
            if (!currentEvent.isValid()) continue;

            // Collision occurred
            Ball a = currentEvent.getA();
            Ball b = currentEvent.getB();

            for (Ball ball : reRunBalls) {
                ball.move(currentEvent.getTime() - reRunCurrentTime);
            }
            reRunCurrentTime = currentEvent.getTime();

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
                    System.out.println(
                            "Ball in hole: " + a.toString());
                    System.out.println("Balls in table: " + reRunBalls.size());
                    System.out.println("Iteration: " + index);
                    reRunBalls.remove(a);
                    reRunBallsInHoles.add(a);
                    break;
                default:
                    throw new IllegalStateException(
                            "Unexpected value: " + currentEvent.getEventType());
            }
            index++;

            predict(a);
            predict(b);

            updateEventsTime(a);
            updateEventsTime(b);
        }
        System.out.println("Balls size: " + reRunBalls.size());
    }

    private static void removeEventsWith(Ball toRemove) {
        reRunEvents.removeIf(event ->
                (event.getA() != null && event.getA().equals(toRemove)) ||
                        (event.getB() != null &&
                                event.getB().equals(toRemove)));
    }

    private static Optional<Ball> isBallInHole(Ball a, Ball b) {
        if (a.getType() == BallType.BALL && b.getType() == BallType.BALL) {
            return Optional.empty();
        }
        if (a.getType() == BallType.BALL && b.getType() == BallType.HOLE) {
            return Optional.of(a);
        }
        if (b.getType() == BallType.BALL && a.getType() == BallType.HOLE) {
            return Optional.of(b);
        }
        return Optional.empty();
    }

    public static void reRunSimulation(File fileOfPositions, List<Ball> balls) {
        List<Pair<Double>> positions = FilesParser.readPositionsFile(
                fileOfPositions);
        for (int i = 0; Math.max(positions.size(), balls.size()) > i; i++) {
            balls.get(i).setX(positions.get(i).getFirst());
            balls.get(i).setY(positions.get(i).getSecond());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Initialize holes
        Utils.initializeHoles(reRunHoles);
        // Initialize balls
        Utils.initializeTable(reRunBalls, Utils.whiteBallInitialPosX,
                Utils.whiteBallInitialPosY, Utils.whiteBallInitialVelX,
                Utils.whiteBallInitialVelY, Utils.firstBallInitialPosX,
                Utils.firstBallInitialPosY);

        File directory =
                new File(FilesParser.RESOURCES_PATH +
                        Double.toString(Utils.whiteBallInitialPosY));
        directory.mkdir();
        /**
         * Aca ponemos el archivo que queremos volver a correr.
         * Cambiar el numero de arriba (reRunFileNumber)
         * */
        File onlyPositionFile = new File(directory + File.separator
                + reRunFileNumber + "_onlyXandY.txt");

        // Initialize balls
        reRunSimulation(onlyPositionFile, reRunBalls);

        System.out.println("Holes: ");
        for (Ball hole : reRunHoles) {
            System.out.println(hole);
        }
        // print all balls
        System.out.println("Balls: ");
        reRunBalls.forEach(System.out::println);

        // Create animation file
        File directoryReRun = new File(directory + File.separator + "reRun");
        directoryReRun.mkdir();

        String animationFileName = reRunFileNumber + "_" + FilesParser.ANIMATION_FILE;

        File animationFile = new File(directoryReRun + File.separator
                + animationFileName);

        try {
            RandomAccessFile raf = new RandomAccessFile(animationFile, "rw");
            raf.setLength(0);
            raf.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        simulate(animationFile);

        System.out.println("Finished reRun");
    }
}
