package tools;

import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class GrayTools {

    public static int kernelRadius(double[][] kernel) {
        return kernel.length / 2;
    }

    /**
     * Application of a filter to a gray scale image
     * @param kernel: must be normalized
     * @return Filtered image
     */
    public static ImageProcessor convolution(ImageProcessor ip, double[][] kernel) {

        ImageProcessor ip2 = ip.duplicate();

        int w = ip.getWidth();
        int h = ip.getHeight();
        int r = kernel.length/2;  // kernel's radius

        // image environment
        for (int v = 0; v < h; v++) {  // rows
            for (int u = 0; u < w; u++) {  // columns
                double s = 0;  // sum of px.kr

                // kernel environment
                for (int j = -r; j < r + 1; j++) {
                    for (int i = -r; i < r + 1; i++) {
                        int x = u + i;
                        int y = v + j;
                        if (x < 0) {  // left edge
                            x = 0;
                        } else if (x >= w) {
                            x = w - 1;  // right edge
                        }
                        if (y < 0) {  // top edge
                            y = 0;
                        } else if (y >= h) {  // bottom edge
                            y = h - 1;
                        }
                        int px = ip.getPixel(x, y);
                        double kr = kernel[i + r][j + r];
                        s += px * kr;
                    }
                }
                ip2.putPixel(u, v, (int) s);
            }
        }
        return ip2;
    }

    public static double[][] gaussian2dKernel(double sigma, int radius) {

        int kernelWidth = 2*radius + 1;
        double[][] kernel = new double[kernelWidth][kernelWidth];

        double H;
        double W = 0.0;
        for (int x = -radius; x < radius + 1; x++) {
            for (int y = -radius; y < radius + 1; y++) {
                if (sigma == 0.0) {
                    H = 1.0;
                }
                else {
                    H = Math.exp(-((double) x * x + y * y) / 2 / sigma / sigma);  // gaussian expression
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
     * Signal-to-noise ratio
     * @param origIm: Original gray image
     * @param noiseIm: Filtered gray image
     * @return SNR
     */
    public static double snr(ImagePlus origIm, ImagePlus noiseIm) {

        int M = origIm.getWidth();
        int N = origIm.getHeight();
        ImageProcessor origP = origIm.getProcessor();
        ImageProcessor noiseP = noiseIm.getProcessor();

        BigDecimal Ps = new BigDecimal(String.valueOf(0));
        BigDecimal Pn = new BigDecimal(String.valueOf(0));
        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                int s = origP.getPixel(u, v);
                int d = s - noiseP.getPixel(u, v);

                BigDecimal s2 = new BigDecimal(String.valueOf(s*s));
                BigDecimal d2 = new BigDecimal(String.valueOf(d*d));
                s2 = s2.abs();
                d2 = d2.abs();
                Ps = Ps.add(s2);
                Pn = Pn.add(d2);
            }
        }
        BigDecimal newPs = Ps.setScale(12,RoundingMode.HALF_EVEN);
        BigDecimal newPn = Pn.setScale(12, RoundingMode.HALF_EVEN);
        double snr = newPs.divide(newPn, RoundingMode.HALF_EVEN).doubleValue();
        System.out.println("Ps = " + Ps);
        System.out.println("Pn = " + Pn);
        return snr;
    }

    /**
     * Signal-to-noise ratio in dB
     * @param origIm: Original gray image
     * @param noiseIm: Filtered gray image
     * @return SNR log (dB)
     */
    public static double snrLog(ImagePlus origIm, ImagePlus noiseIm) {

        double snr = snr(origIm, noiseIm);
        return 10 * Math.log10(snr);
    }

    /**
     * Add Gaussian noise with zero mean, to a grayscale image
     * @param im: gray image
     * @param sigma: standard deviation
     * @return Noised image.
     */
    public static ImagePlus addGaussianNoise (ImagePlus im, double sigma) {
        FloatProcessor fp = im.getProcessor().convertToFloatProcessor();
        FloatProcessor fp2 = (FloatProcessor) fp.duplicate();

        int w = im.getWidth();
        int h = im.getHeight();

        Random rnd = new Random();
        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {
                float val = fp.getf(u, v);
                float noise = (float) (rnd.nextGaussian() * sigma);
                fp2.setf(u, v, val + noise);
            }
        }
        return new ImagePlus("Noised image", fp2);
    }
}
