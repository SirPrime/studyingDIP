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
     * Application of a given filter to a gray scale image
     * @param kernel: must be normalized
     * @return Filtered image
     */
    public static ImageProcessor convolution(ImageProcessor ip, double[][] kernel) {

        ImageProcessor ip2 = ip.duplicate();

        int w = ip.getWidth();
        int h = ip.getHeight();
        int rx = kernel[0].length/2;  // kernel's horizontal radius
        int ry = kernel.length/2;  // kernel's vertical radius

        // image environment
        for (int v = 0; v < h; v++) {  // rows
            for (int u = 0; u < w; u++) {  // columns
                double s = 0;  // sum of px.kr

                // kernel environment
                for (int i = -ry; i < ry + 1; i++) {  // rows
                    for (int j = -rx; j < rx + 1; j++) {  // columns
                        int x = u + j;  // pixel x coordinate
                        int y = v + i;  // pixel y coordinate
                        if (x < 0) {  // beyond left edge
                            x = 0;  // repeats left edge pixel
                        } else if (x >= w) { // beyond right pixel
                            x = w - 1;  // repeats right edge pixel
                        }
                        if (y < 0) {  // beyond top edge
                            y = 0;  // repeats top edge pixel
                        } else if (y >= h) {  // beyond bottom edge
                            y = h - 1;  // repeats bottom edge pixel
                        }
                        int px = ip.getPixel(x, y);  // get pixel value
                        double kr = kernel[i + ry][j + rx];  // get kernel value
                        s += px * kr;
                    }
                }
                ip2.putPixel(u, v, (int) s);
            }
        }
        return ip2;
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

    /**
     * Add a constant number tu the pixel values of an image
     * @param im: image
     * @param constant: value to be added
     * @return Image as an array
     */
    public static int[][] addConstant (ImagePlus im, int constant) {
        ImageProcessor ip = im.getProcessor();

        int w = im.getWidth();
        int h = im.getHeight();

        int[][] imArray = new int[h][w];  // image as array

        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {
                int p = ip.get(u, v);
                imArray[v][u] = p + constant;
            }
        }
        return imArray;
    }
}
