package ar.edu.itba.ss.Float;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static java.lang.System.exit;

public class Main {
    private static PriorityQueue<Event> events;
    private static float currentTime = 0.0F;
    private static final Ball[] holes = new Ball[6];
    private static final List<Ball> balls = new ArrayList<>();
    public static final List<Ball> ballsInHoles = new ArrayList<>();
    private static final int floatIterationWithThatYPosOfWhiteBall = 56;
    private static final boolean flagOfReRunFloat = true;


    private static void createCollisions(Ball a) {
        if (a == null) return;
        if (a.getType() == BallType.HOLE) return;
        if (ballsInHoles.contains(a)) return;

        //Check if ball with collide with another in given time
        for (Ball b : balls) {
            float timeToCollide = a.collides(b);
            events.add(new Event(currentTime + timeToCollide, EventType.BALL, a,
                    b));
        }

        for (Ball hole : holes) {
            float timeToCollide = a.collides(hole);
            events.add(new Event(currentTime + timeToCollide, EventType.HOLE, a,
                    hole));
        }

        // Check if ball will collide with horizontal wall
        float timeToHorizontalWall = a.collidesY();
        events.add(
                new Event(currentTime + timeToHorizontalWall, EventType.HWALL,
                        null, a));

        // Check if ball will collide with vertical wall
        float timeToWallX = a.collidesX();
        events.add(
                new Event(currentTime + timeToWallX, EventType.VWALL, a, null));
    }

    private static void updateEventsTime(Ball a) {
        if (a == null || a.getType() == BallType.HOLE) return;
        if (ballsInHoles.contains(a)) return;

        // Update time of events that do not contain ball a
        for (Event e : events) {
            if ((e.getA() != null && e.getA().equals(a)) ||
                    (e.getB() != null && e.getB().equals(a))) {
                continue;
            }
            switch (e.getEventType()) {
                case BALL:
                case HOLE:
                    e.updateTime(currentTime + e.getA().collides(e.getB()));
                    break;
                case HWALL:
                    e.updateTime(currentTime + e.getB().collidesY());
                    break;
                case VWALL:
                    e.updateTime(currentTime + e.getA().collidesX());
                    break;
            }
        }
    }

    private static void predict(Ball a) {
        if (a == null) return;
        if (a.getType() == BallType.HOLE) return;
        if (ballsInHoles.contains(a)) return;

        // Filter events to only remain the ones that do not include ball a
        events.removeIf(e -> (e.getA() != null && e.getA().equals(a)) ||
                (e.getB() != null && e.getB().equals(a)));

        if (ballsInHoles.contains(a)) return;

        createCollisions(a);
    }

    static List<Float> times = new ArrayList<>();

    private static void simulate(File fileAnimationFile) {

        events = new PriorityQueue<>();
        // Populate PQ
        balls.forEach(Main::createCollisions);
        int index = 0;
        int previos_index = index - 1;

        while (events.size() > 0) {

            if (index != previos_index) {
                FilesParser.writeAnimationFile(fileAnimationFile, index, balls,
                        List.of(holes));
                previos_index++;
            }

            // Retrieve and delete impending event - will be one with minimum priority
            if (balls.isEmpty()) {
                System.out.println("Finished simulation with " + index +
                        " iterations");
                return;
            }
            Event currentEvent = events.poll();

            // Invalidated events are discarded
            if (!currentEvent.isValid()) continue;

            // Collision occurred
            Ball a = currentEvent.getA();
            Ball b = currentEvent.getB();

            for (Ball ball : balls) {
                ball.move(currentEvent.getTime() - currentTime);
            }
            times.add(Math.abs(currentEvent.getTime() - currentTime));
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
                    System.out.println(
                            "Ball in hole: " + a.toString());
                    System.out.println("Balls in table: " + balls.size());
                    System.out.println("Iteration: " + index);
                    balls.remove(a);
                    ballsInHoles.add(a);
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
        System.out.println("Balls size: " + balls.size());
    }

    public static void reRunSimulation(File fileOfPositions, List<Ball> balls) {
        List<Pair<Float>> positions = FilesParser.readPositionsFile(
                fileOfPositions);
        for (int i = 0; balls.size() > i && positions.size() > i; i++) {
            balls.get(i).setX(positions.get(i).getFirst());
            balls.get(i).setY(positions.get(i).getSecond());
        }
    }

    private static void allMain() {
        // Initialize holes
        Utils.initializeHoles(holes);
        // Initialize balls
        Utils.initializeTable(balls,
                Utils.whiteBallInitialPosX, Utils.whiteBallInitialPosY,
                Utils.whiteBallInitialVelX, Utils.whiteBallInitialVelY,
                Utils.firstBallInitialPosX, Utils.firstBallInitialPosY);

        System.out.println("Holes: ");
        for (Ball hole : holes) {
            System.out.println(hole);
        }

        String directoryWhiteBallInitialPosY =
                Float.toString(Utils.whiteBallInitialPosY);
        // Create directory if it does not exist
        File directory =
                new File(FilesParser.RESOURCES_PATH +
                        directoryWhiteBallInitialPosY);
        directory.mkdir();
        String animationFullFileName =
                ar.edu.itba.ss.Main.iterationWithThatYPosOfWhiteBall +
                        "_float_" +
                        FilesParser.ANIMATION_FILE;
        File animationFile;
        File onlyPositionFile =
                new File(directory + File.separator +
                        ar.edu.itba.ss.Main.iterationWithThatYPosOfWhiteBall +
                        "_onlyXandY.txt");
        File floatTimesFile;

        if (flagOfReRunFloat && onlyPositionFile.exists() &&
                onlyPositionFile.length() > 0) {
            // El archivo existe y no esta vacio
            System.out.println("Re-running simulation");
            reRunSimulation(onlyPositionFile, balls);
            floatTimesFile = new File(directory + File.separator +
                    ar.edu.itba.ss.Main.iterationWithThatYPosOfWhiteBall +
                    "_times_in_float_bis.txt");
            onlyPositionFile = new File(directory + File.separator +
                    ar.edu.itba.ss.Main.iterationWithThatYPosOfWhiteBall +
                    "_onlyXandY_in_float_bis.txt");
            animationFile = new File(directory + File.separator +
                    ar.edu.itba.ss.Main.iterationWithThatYPosOfWhiteBall +
                    "_float_animation_bis.txt");

        } else {
            floatTimesFile = new File(directory + File.separator +
                    ar.edu.itba.ss.Main.iterationWithThatYPosOfWhiteBall +
                    "_times_in_float.txt");
            animationFile = new File(directory + File.separator +
                    animationFullFileName);
            onlyPositionFile = new File(directory + File.separator +
                    ar.edu.itba.ss.Main.iterationWithThatYPosOfWhiteBall +
                    "_onlyXandY_in_float.txt");
        }
        // print all balls
        System.out.println("Balls: ");
        balls.forEach(System.out::println);

        // delete content if has something in it
        ar.edu.itba.ss.FilesParser.deleteFileContent(onlyPositionFile);
        ar.edu.itba.ss.FilesParser.deleteFileContent(animationFile);
        ar.edu.itba.ss.FilesParser.deleteFileContent(floatTimesFile);

        FilesParser.writeOnlyPositions(onlyPositionFile, balls);
        FilesParser.writeAnimationFile(animationFile, 0, balls,
                List.of(holes));

        simulate(animationFile);
        FilesParser.writeTimesFile(floatTimesFile, times, currentTime);
        System.out.println("Finished simulation with all balls in holes");
    }

    public static void main(String[] args) {
        allMain();
        System.out.print("End allMain() in Float");
    }

}
