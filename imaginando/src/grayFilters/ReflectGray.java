package grayFilters;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.util.Objects;

public class ReflectGray {

    public static ImagePlus reflect(ImagePlus im, String dir) {

        if (im.getType() != ImagePlus.GRAY8) {
            IJ.error("8 bit grayscale image required");
            return im;
        }

        ImageProcessor ip = im.getProcessor();
        ImageProcessor ip2 = ip.duplicate();

        int M = ip.getWidth();
        int N = ip.getHeight();

        if (Objects.equals(dir, "horizontal")) {
            for (int v = 0; v < N; v++) {
                for (int u = 0; u < M; u++) {
                    int p = ip.get(u, v);
                    ip2.putPixel(u, N - v, p);
                }
            }
        }

        else if (Objects.equals(dir, "vertical")) {
            for (int v = 0; v < N; v++) {
                for (int u = 0; u < M; u++) {
                    int p = ip.get(u, v);
                    ip2.putPixel(M - u, v, p);
                }
            }
        }
        return new ImagePlus(dir + " reflect", ip2);
    }
}
