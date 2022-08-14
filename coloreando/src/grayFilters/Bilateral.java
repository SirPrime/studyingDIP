package grayFilters;

import ij.ImagePlus;
import ij.process.ImageProcessor;


public class Bilateral {

    // Attributes
    private final ImagePlus im;  // Gray image
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
    public ImagePlus doIt() {
        ImageProcessor ip = this.im.getProcessor();
        ImageProcessor ip2 = ip.duplicate();
        int D = this.domainWidth();
        int M = this.im.getWidth();
        int N = this.im.getHeight();

        for (int v = D; v < N - D; v++) {  // rows
            for (int u = D; u < M - D; u++) {  // columns
                // Sum of wighted pixel vectors
                double S = 0.0;  // sum of wighted pixel values
                double W = 0.0;  // sum of pixels weights
                int a = ip.getPixel(u, v);

                for (int n = -D; n < D + 1; n++) {
                    for (int m = -D; m < D + 1; m++) {
                        int b = ip.getPixel(u+m, v+n);  // off-center pixel value
                        double wd = Math.exp(-(m*m + n*n)/2.0/sd/sd);  // domain coefficient
                        double d = a - b;
                        double wr = Math.exp(-d*d/2.0/sr/sr);  // range coefficient
                        double w = wd * wr;  // composite coefficient

                        S += w*b;
                        W += w;
                    }
                }
                int newPixel = (int) (S / W);
                ip2.putPixel(u, v, newPixel);
            }
        }
        return new ImagePlus("Bilateral gray filter", ip2);
    }

}
