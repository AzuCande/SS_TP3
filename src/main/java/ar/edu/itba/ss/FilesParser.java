package ar.edu.itba.ss;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilesParser {
    public final static String RESOURCES_PATH = "src/main/resources/";
    public final static String DYNAMIC_FILE = "dynamic.txt";
    public final static String STATIC_FILE = "static.txt";
    public final static String ANIMATION_FILE = "animation.txt";

    public static void parseStaticFile() {
        File staticFile = new File(RESOURCES_PATH + STATIC_FILE);
        try (Scanner scanner = new Scanner(staticFile)) {
            if (scanner.hasNextLine()) {
                Utils.tableWidth = Double.parseDouble(scanner.nextLine());
            }
            if (scanner.hasNextLine()) {
                Utils.tableHeight = Double.parseDouble(scanner.nextLine());
            }
            if (scanner.hasNextLine()) {
                Utils.particleMass = Double.parseDouble(scanner.nextLine());
            }
            if (scanner.hasNextLine()) {
                Utils.particleRadius =
                        Double.parseDouble(scanner.nextLine()) / 2;
            }
        } catch (Exception e) {
            System.err.println("Error while parsing static file");
        }
    }

    // Dynamic file format: x y vx vy
    public static Ball parseDynamicFile() {
        File dynamicFile = new File(RESOURCES_PATH + DYNAMIC_FILE);
        Ball whiteBall = null;
        try (Scanner scanner = new Scanner(dynamicFile)) {
            if (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                whiteBall = new Ball(
                        Double.parseDouble(line[0]),
                        Double.parseDouble(line[1]),
                        Double.parseDouble(line[2]),
                        Double.parseDouble(line[3]),
                        Utils.particleRadius,
                        Utils.particleMass,
                        BallType.BALL,
                        Integer.parseInt(line[4]),
                        Integer.parseInt(line[5]),
                        Integer.parseInt(line[6]),
                        "Cu"
                );
            }
        } catch (Exception e) {
            System.err.println("Error while parsing dynamic file");
        }
        return whiteBall;
    }

    public static void createAnimationFile(String fileFullPath,
                                           List<Ball> ballsList,
                                           List<Ball> holesList) {
//        File file = new File(RESOURCES_PATH + ANIMATION_FILE);
        File file = new File(fileFullPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writeAnimationFileLines(0, writer, ballsList, holesList);
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
        }
    }

    public static void writeAnimationFile(File fileFullPath, int time,
                                          List<Ball> ballsList,
                                          List<Ball> holesList) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileFullPath, true))) {
            writeAnimationFileLines(time, writer, ballsList, holesList);
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
        }
    }

    public static void writeOnlyPositions(File fileFullPath,
                                          List<Ball> ballsList) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileFullPath))) {
            for (Ball ball : ballsList) {
                writer.append(String.valueOf(ball.getX()))
                        .append("\t")
                        .append(String.valueOf(ball.getY()))
                        .append("\n");
            }
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
        }
    }

    private static void writeCollectionToFileLines(BufferedWriter writer,
                                                   List<Ball> list)
            throws IOException {
        for (Ball ball : list) {
            writer.append(String.valueOf(ball.getId()))
                    .append("\t")
                    .append(String.valueOf(ball.getX()))
                    .append("\t")
                    .append(String.valueOf(ball.getY()))
                    .append("\t")
                    .append(String.valueOf(ball.getVx()))
                    .append("\t")
                    .append(String.valueOf(ball.getVy()))
                    .append("\t")
                    .append(String.valueOf(ball.getRadius()))
                    .append("\t")
                    .append(String.valueOf(ball.getMass()))
                    .append("\t")
                    .append(String.valueOf(ball.getColorR())).append("\t")
                    .append(String.valueOf(ball.getColorG())).append("\t")
                    .append(String.valueOf(ball.getColorB()))
                    .append(String.valueOf(ball.getColorG())).append("\t")
                    .append(String.valueOf(ball.getSymbol()))
                    .append("\n");
        }
    }

    private static void writeAnimationFileLines(int time, BufferedWriter writer,
                                                List<Ball> ballsList,
                                                List<Ball> holesList)
            throws IOException {
        int totalBalls = ballsList.size() + holesList.size();
        writer.append(String.valueOf(totalBalls))
                .append("\n")
                .append("Generation: ")
                .append(String.valueOf(time))
                .append("\n");
        writeCollectionToFileLines(writer, holesList);
        writeCollectionToFileLines(writer, ballsList);
//        writer.append("\n");
    }

    public static List<Pair<Double>> readPositionsFile(File fileOfPositions) {
        List<Pair<Double>> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(fileOfPositions)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("\t");
                lines.add(new Pair<>(
                        Double.parseDouble(line[0]),
                        Double.parseDouble(line[1])
                ));
            }
        } catch (Exception e) {
            System.err.println("Error while reading positions file");
        }

        return lines;
    }

    public static void writeTimesFile(File fileName, List<Double> times,
                                      double currentTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Double time : times) {
                writer.append(String.valueOf(time))
                        .append("\n");
            }
            writer.append("Total Time: ").append(String.valueOf(currentTime))
                    .append("\n");
        } catch (Exception e) {
            System.err.println("Error while writing times file");
        }
    }

    public static void deleteFileContent(File fileName) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            raf.setLength(0);
            raf.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
