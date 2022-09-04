package tools;

/*
Kernels generator
 */

public class KernelGen {

    private static int weight(int[][] kernel) {
        int m = kernel.length;  // rows
        int n = kernel[0].length;  // columns

        int W = 0;
        for (int[] row : kernel) {
            for (int value : row) {
                W += value;
            }
        }
        return W;
    }

    /**
     * Box kernel
     * @param r: width and height from the center
     * @return box kernel
     */
    public static double[][] box(int r) {

        int a = 2*r + 1;
        int W = 0;
        int[][] kernel = new int[a][a];
        double[][] kd = new double[a][a];  // normalized
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                kernel[i][j] = 1;
                W += 1;
            }
        }
        // Normalization
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                kd[i][j] = (double) kernel[i][j] / W;
            }
        }
        return kd;
    }

    /**
     * Gauss kernel
     * @param sigma: standard deviation
     * @param radius: kernel's radius
     * @return Gauss kernel
     */
    public static double[][] gauss(double sigma, int radius) {

        int kernelWidth = 2*radius + 1;
        double[][] kernel = new double[kernelWidth][kernelWidth];

        double H;
        double W = 0.0;
        for (int x = -radius; x < radius + 1; x++) {
            for (int y = -radius; y < radius + 1; y++) {
                if (sigma == 0.0) {
                    H = 0.0;
                }
                else {
                    if ((x*x + y*y) <= radius*radius) {
                        H = Math.exp(-0.5 * (x*x + y*y) / sigma/sigma);  // gaussian expression
                    }
                    else {
                        H = 0.0;
                    }
                }
                int i = x + radius;
                int j = y + radius;
                kernel[i][j] = H;
                W += H;
            }
        }
        // Normalization
        for (int i = 0; i < kernelWidth; i++) {
            for (int j = 0; j < kernelWidth; j++) {
                kernel[i][j] = kernel[i][j] / W;
            }
        }
        return kernel;
    }

    /**
     * Gaussian filter 1d (separable filter)
     * @param s: standard deviation
     * @param r: kernel's radius
     * @return Gaussian kernel in 1d
     */
    public double[] gauss1d(double s, int r) {

        int kw = 2*r + 1;  // kernel's width
        double[] kernel = new double[kw];

        if (s == 0.0) {
            for (int j = 0; j < kw; j++) {
                kernel[j] = 0.0;
            }
        }
            else {
            double H;
            double W = 0.0;
                for (int x = -r; x < r + 1; x++) {
                    H = Math.exp(-0.5 * x*x / s/s);  // gaussian expression
                    int i = x + r;
                    kernel[i] = H;
                    W += H;
                }
            // Normalization
            for (int i = 0; i < kw; i++) {
                    kernel[i] = kernel[i] / W;
            }
            }
        return kernel;
    }

    public static double[][] laplacianOfGaussian(double s, int r) {
        int kw = 2*r + 1;
        double[][] kernel = new double[kw][kw];

        double H;
        double W = 0.0;
        for (int x = -r; x < r + 1; x++) {
            for (int y = -r; y < r + 1; y++) {
                if (s == 0.0) {
                    H = 0.0;
                }
                else {
                    int z2 = x*x + y*y;
                    if (z2 <= r*r) {
                        double s2 = s*s;
                        double s4 = s2 * s2;
                        H = -(z2 - 2*s2)/s4 * Math.exp(-0.5 * z2 / s2);
                    }
                    else {
                        H = 0.0;
                    }
                }
                int i = x + r;
                int j = y + r;
                kernel[i][j] = H;
                W += H;
            }
        }
        // Normalization
        for (int i = 0; i < kw; i++) {
            for (int j = 0; j < kw; j++) {
                kernel[i][j] = kernel[i][j] / W;
            }
        }
        return kernel;
    }
}
