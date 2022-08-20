package grayFilters;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class Kuwahara {

    // Attributes
    ImagePlus im;  // gray image

    // Constructor

    /**
     * Kuwahara gray filter
     * @param im : gray image
     */
    public Kuwahara(ImagePlus im) {
        this.im = im;
    }

    public static int[][][] subregions() {

        int[][] R1 = {{-1,-1}, {0,-1}, {-1,0}, {0,0}};
        int[][] R2 = {{0,-1}, {1,-1}, {0,0}, {1,0}};
        int[][] R3 = {{0,0}, {1,0}, {1,0}, {1,1}};
        int[][] R4 = {{-1,0}, {0,0}, {-1,1}, {1,0}};

        return new int[][][]{R1, R2, R3, R4};
    }

    public ImagePlus doIt() {

        ImageProcessor ip = this.im.getProcessor();
        ImageProcessor ip2 = ip.duplicate();

        int M = ip.getWidth();
        int N = ip.getHeight();

        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                double varMin = 1e100;  // a big value
                double mMin = 0.0;  // mean at varMin
                for (int[][] R : subregions()) {

                    double[] results = evalSubregion(ip, R, u, v);
                    double variance = results[0];
                    double mean = results[1];

                    if (variance < varMin) {
                        varMin = variance;
                        mMin = mean;
                    }
                }
                ip2.putPixelValue(u, v, mMin);
            }
        }
        return new ImagePlus("Kuwahara gray filter", ip2);
    }

    public double[] evalSubregion(ImageProcessor ip, int[][] R, int u, int v) {

        int n = R.length;  // size or R
        int S1 = 0, S2 = 0;
        double variance, mean;

        for (int[] r : R) {
            int i = r[0];
            int j = r[1];
            int a = ip.getPixel(u+i, v+j);

            S1 += a;
            S2 += a*a;
        }

        variance = (double) (S2 - S1*S1/n) / n;
        mean = (double) S1 / n;

        return new double[]{variance, mean};
    }
}
