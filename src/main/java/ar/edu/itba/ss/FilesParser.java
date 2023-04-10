package ar.edu.itba.ss;

import java.io.File;
import java.util.Scanner;

public class FilesParser {
    public final static String RESOURCES_PATH = "src/main/resources/";
    public final static String DYNAMIC_FILE = "dynamic.txt";
    public final static String STATIC_FILE = "static.txt";

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

    public static Bocha parseDynamicFile() {
        File dynamicFile = new File(RESOURCES_PATH + DYNAMIC_FILE);
        Bocha whiteBall = null;
        try (Scanner scanner = new Scanner(dynamicFile)) {
            if (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                whiteBall = new Bocha(
                        Double.parseDouble(line[0]),
                        Double.parseDouble(line[1]),
                        Double.parseDouble(line[2]),
                        Double.parseDouble(line[3]),
                        Utils.particleRadius,
                        Utils.particleMass
                );
            }
        } catch (Exception e) {
            System.err.println("Error while parsing dynamic file");
        }
        return whiteBall;
    }
}
