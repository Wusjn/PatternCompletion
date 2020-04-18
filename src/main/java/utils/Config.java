package utils;

public class Config {

    public static boolean batchMode() {
        return false;
    }

    public static String getAllPatternDir() {
        return "data";
    }

    public static String getFoucsPatternDir() {
        return "data/fill_color";
    }

    public static boolean debug() {
        return false;
    }

    public static double getSimilarThreshold() {
        return 0.95;
    }
    public static double getReferenceThreshold() {
        return 0.95;
    }
}
