package ar.edu.itba.ss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            if (scanner.hasNextLine()) Utils.tableWidth = Integer.parseInt(scanner.nextLine());
            if (scanner.hasNextLine()) Utils.tableHeight = Integer.parseInt(scanner.nextLine());
            if (scanner.hasNextLine()) Utils.particleMass = Integer.parseInt(scanner.nextLine());
            if (scanner.hasNextLine()) Utils.particleRadius = Integer.parseInt(scanner.nextLine());
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
                        BallType.BALL
                );
            }
        } catch (Exception e) {
            System.err.println("Error while parsing dynamic file");
        }
        return whiteBall;
    }

    public static void createAnimationFile(List<Ball> ballsList) {
        File file = new File(RESOURCES_PATH + ANIMATION_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writeAnimationFileLines(0, writer, ballsList);
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
        }
    }

    public static void writeAnimationFIle(int time, List<Ball> ballsList) {
        File file = new File(RESOURCES_PATH + ANIMATION_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writeAnimationFileLines(time, writer, ballsList);
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
        }
    }

    private static void writeAnimationFileLines(int time, BufferedWriter writer, List<Ball> ballsList) throws IOException {
        writer.append("Generation: ")
                .append(String.valueOf(time))
                .append("\n");
        for (Ball ball : ballsList) {
            // TODO: Check correct format for Ovito (moving balls)
        }
    }
}
