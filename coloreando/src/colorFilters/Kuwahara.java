package colorFilters;// filters.Kuwahara filter using apache.commons linear algebra library

import ij.process.ColorProcessor;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import tools.RGB;

import java.util.Arrays;

import static grayFilters.Kuwahara.subregions;
import static tools.someMaths.max3dArray;

public class Kuwahara {

    /**
     * filters.Kuwahara color filter
     * @param cp: ColorProcessor
     * @return Filtered image
     */
    public static ColorProcessor colorFilter(ColorProcessor cp) {

        int rad = max3dArray(subregions());  // radius of regions

        ColorProcessor cp2 = (ColorProcessor) cp.duplicate();
        int M = cp.getWidth();
        int N = cp.getHeight();

        for (int v = rad; v < N - rad; v++) {  // rows
            for (int u = rad; u < M - rad; u++) {  // columns
                double varMin = Double.MAX_VALUE;  // set variance with big value
                double[] meanVm = {0, 0, 0};  // initialize mean for min variance
                for (int[][] R : subregions()) {  // iterate over subregions
                    double[] values = evalWithVar(cp, R, u, v);  // eval subregions
                    double var = values[0];  // variance
                    double[] mean = Arrays.stream(values, 1, 4)
                            .toArray();  // mean vector
                    if (var < varMin) {
                        varMin = var;  // set min variance
                        meanVm = mean;  // mean when variance is minimum
                    }
                }
                int r = (int) meanVm[0];  // red
                int g = (int) meanVm[1];  // green
                int b = (int) meanVm[2];  // blue
                int c = RGB.colorPixel(r, g, b);  // assembly of color pixel
                cp2.putPixel(u, v, c);  //  put pixel at the image
            }
        }
        return cp2;
    }

    /**
     * Returns the total variance and the mean vector of the color image
     * I for the subregion R positioned at (u, v).
     * @param cp: Color Image
     * @param R: Region
     * @param u: vertical coordinate
     * @param v: horizontal coordinate
     * @return four numbers: total variance and mean vector of 3 components
     */
    static double[] evalWithVar(ColorProcessor cp, int[][] R, int u, int v) {

        int n = R.length;
        int[][][] imArray = RGB.imToArray(cp);
        RealVector S1 = new ArrayRealVector(new double[] {0,0,0}, false);
        RealVector S2 = new ArrayRealVector(new double[] {0,0,0}, false);

        for (int[] r: R) {
            int i = r[0];
            int j = r[1];
            int[] aaa = imArray[v+j][u+i];
            double[] aa = Arrays.stream(aaa).asDoubleStream().toArray();
            RealVector a = new ArrayRealVector(aa);

            S1 = S1.add(a);
            double a2 = a.dotProduct(a);  // L2 norm a squared
            S2 = S2.mapAdd(a2);  // sum a2 to each component of S2

        }
        RealVector S = S2.add(S1.ebeMultiply(S1).mapDivide(-n)).mapDivide(n);
        double variance = S.getEntry(0) + S.getEntry(1) + S.getEntry(2);
        RealVector m = S1.mapDivide(n);

        return new double[]{variance, m.getEntry(0), m.getEntry(1), m.getEntry(2)};
    }

}
