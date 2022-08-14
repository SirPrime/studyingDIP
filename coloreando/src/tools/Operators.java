package tools;

public class Operators {

    public static double[][] prewittX() {
        return new double[][]{
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}};
    }

    public static double[][] prewittY() {
        return new double[][]{
                {-1, -1, -1},
                {0, 0, 0},
                {1, 1, 1}};
    }

    public static double[][] sobelX() {
        return new double[][]{
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}};
    }

    public static double[][] sobelY() {
        return new double[][]{
                {-1, -2, -1},
                {0, 0, 0},
                {1, 2, 1}};
    }

    public static double[][] laplacian() {
        return new double[][]{
                {0., 1., 0.},
                {1., -4., 1.},
                {0., 1, 0.}};
    }
}
