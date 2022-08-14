package colorFilters;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import static tools.RGB.pixelToRealVector;
import static tools.RGB.realVectorToPixel;

public class Bilateral {
// Attributes
    private final ImagePlus im;  // Color image
    private final double sd;  // Standard deviations for domain filter
    private final double sr;  // Standard deviations for domain filter

    // Constructor with arguments
    public Bilateral(ImagePlus im, double sd, double sr) {
        this.im = im;
        this.sd = sd;
        this.sr = sr;
    }

    /**
     * Kernel domain width, i.e. radius of kernel
     * @return Domain width
     */
    public int domainWidth() {
        return (int) Math.ceil(3.5*sd);  // width of domain filter kernel
    }

    /**
     * filters.Bilateral color filter method
     * @return Filtered image
     */
    public ImagePlus colorFilter() {
        ImageProcessor ip = this.im.getProcessor().convertToColorProcessor();
        ImageProcessor ip2 = ip.duplicate();
        int D = this.domainWidth();
        int M = this.im.getWidth();
        int N = this.im.getHeight();

        for (int v = D; v < N - D; v++) {  // rows
            for (int u = D; u < M - D; u++) {  // columns
                // Sum of wighted pixel vectors
                RealVector S = new ArrayRealVector(new double[] {0,0,0});
                double W = 0.0;  // sum of pixels weights
                RealVector a = pixelToRealVector(this.im, u, v);

                for (int n = -D; n < D + 1; n++) {
                    for (int m = -D; m < D + 1; m++) {
                        RealVector b = pixelToRealVector(this.im, u+m, v+n);

                        double wd = Math.exp(-(m*m + n*n)/2.0/sd/sd);  // domain coefficient
                        double d = dist(a, b);
                        double wr = Math.exp(-d*d/2.0/sr/sr);  // range coefficient
                        double w = wd * wr;  // composite coefficient

                        S = S.add(b.mapMultiply(w));
                        W = W + w;
                    }
                }
                RealVector newPixel = S.mapDivide(W);
                int c = realVectorToPixel(newPixel);  // assembly
                ip2.putPixel(u, v, c);
            }
        }
        return new ImagePlus("filters.Bilateral color filter", ip2);
    }

    /**
     * L2 distance of two vectors divide by sqrt(3)
     * @param a: First vector
     * @param b: Second vector
     * @return Distance / sqrt(3)
     */
    static double dist(RealVector a, RealVector b) {
        RealVector c = a.subtract(b);
        return c.getNorm() / Math.sqrt(3.0);
    }

    /**
     * Off-center vector b for separable bilateral color filter
     * @param direction: 'X' or 'Y'
     * @param u: horizontal pixel position
     * @param v: vertical pixel position
     * @return Vector b
     */
    public RealVector bSeparable(char direction, int u, int v, int k) {

        RealVector b = new ArrayRealVector(new double[] {0,0,0});

        if (direction == 'X') {
            b = pixelToRealVector(this.im, u + k, v);
        } else if (direction == 'Y') {
            b = pixelToRealVector(this.im, u, v + k);
        }
        return b;
    }

    /**
     * Composite weight for separable bilateral color filter
     * @param a: Kernel center color vector
     * @param b: Off-center kernel color vector
     * @param k: counter for span the kernel
     * @return Composite weight
     */
    public double compositeWeightSeparable(RealVector a, RealVector b, int k) {

        double wd = Math.exp(-(k*k)/2.0/this.sd/this.sd);  // domain coefficient
        double wr = Math.exp(-dist(a, b)*dist(a, b)/2.0/this.sr/this.sr);  // range coefficient
        return wd * wr;
    }

    /**
     * filters.Bilateral filter applied just in 1 direction
     * @param imp: Image
     * @param direction: Direction to be considered
     * @return Filtered image
     */
    public ImagePlus filter1D(ImagePlus imp, char direction) {
        int M = imp.getWidth();
        int N = imp.getHeight();
        int D = this.domainWidth();
        ImageProcessor ip = imp.getProcessor().convertToColorProcessor();
        ImageProcessor ip2 = ip.duplicate();

        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                RealVector S = new ArrayRealVector(new double[] {0,0,0});
                double W = 0.0;  // sum of pixels weights
                RealVector a = pixelToRealVector(this.im, u, v);

                for (int k = -D; k < D + 1; k++) {
                    RealVector b = bSeparable(direction, u, v, k);  // off-center pixel
                    double w = compositeWeightSeparable(a, b, k);  // composite weight
                    S = S.add(b.mapMultiply(w));
                    W = W + w;
                }
                RealVector newPixel = S.mapDivide(W);
                int c = realVectorToPixel(newPixel);  // assembly
                ip2.putPixel(u, v, c);
            }
        }
        String title = "Filter in " + direction + " direction";
        return new ImagePlus(title, ip2);
    }

    /**
     * filters.Bilateral filter separable
     * @param first: First direction to be considered
     * @return Filtered image in two directions.
     */
    public ImagePlus filterSeparable(char first) {

        char second;
        if (first == 'X') {
            second = 'Y';
        } else second = 'X';

        ImagePlus imfX = filter1D(this.im, first);

        return filter1D(imfX, second);
    }
}
