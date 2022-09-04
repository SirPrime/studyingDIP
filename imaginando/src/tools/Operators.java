package tools;

public class Operators {

    public static double[][] prewittX() {
        return new double[][]{
                {-1.0/6, 0.0, 1.0/6},
                {-1.0/6, 0.0, 1.0/6},
                {-1.0/6, 0.0, 1.0/6}};
    }

    public static double[][] prewittY() {
        return new double[][]{
                {-1.0/6, -1.0/6, -1.0/6},
                {0.0, 0.0, 0.0},
                {1.0/6, 1.0/6, 1.0/6}};
    }

    public static double[][] sobelX() {
        return new double[][]{
                {-1.0/8, 0.0, 1.0/8},
                {-2.0/8, 0.0, 2.0/8},
                {-1.0/8, 0.0, 1.0/8}};
    }

    public static double[][] sobelY() {
        return new double[][]{
                {-1.0/8, -2.0/8, -1.0/8},
                {0.0, 0.0, 0.0},
                {1.0/8, 2.0/8, 1.0/8}};
    }

    public static double[][] impSobelX() {  // improved Sobel
        return new double[][]{
                {-3.0/32, 0.0, 3.0/32},
                {-10.0/32, 0.0, 10.0/32},
                {-3.0/32, 0.0, 3.0/32}};
    }

    public static double[][] impSobelY() {  // improved Sobel
        return new double[][]{
                {-3.0/32, -10.0/32, -3.0/32},
                {0.0, 0.0, 0.0},
                {3.0/32, 10.0/32, 3.0/32}};
    }

    public static double[][] laplacian() {
        return new double[][]{
                {0., 1., 0.},
                {1., -4., 1.},
                {0., 1, 0.}};
    }

    public static double[][] impLaplacian() {  // improved Laplacian
        return new double[][] {
                {1.0/6, 4.0/6, 1.0/6},
                {4.0/6, -20.0/6, 4.0/6},
                {1.0/6, 4.0/6, 1.0/6}
        };
    }
}
