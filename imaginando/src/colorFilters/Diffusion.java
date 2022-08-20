package colorFilters;

import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import tools.GrayTools;
import tools.Operators;

public class Diffusion {

    public static ImageProcessor iter(ImageProcessor ip, int n, double alpha) {

        double[][] HL = Operators.laplacian();
        int r = GrayTools.kernelRadius(HL);
        int M = ip.getWidth();
        int N = ip.getHeight();

        if (n == 0) {
            return ip;
        }
        if (n == 1) {
            ImageProcessor filIm = GrayTools.convolution(ip, HL);
            ImageProcessor ip2 = ip.duplicate();
            for (int v = r; v < N-r; v++) {
                for (int u = r; u < M-r; u++) {
                    int origPixel = ip.getPixel(u, v);  // pixel of original image
                    int laplacePixel = filIm.getPixel(u, v);  // pixel of laplacian image
                    int newPixel = (int) (origPixel + alpha * laplacePixel);
                    ip2.putPixel(u, v, newPixel);
                }
            }
            return ip2;
        }
        else {
            return iter(ip, n-1, alpha);
        }
    }

    public static ImageProcessor gaussianKernel(ImageProcessor ip, int n, double alpha) {

        double sn = Math.sqrt(2*n*alpha);
        int radius = (int) Math.ceil(sn);
        double[][] kernel = GrayTools.gaussian2dKernel(sn, radius);

        return GrayTools.convolution(ip, kernel);
    }

    public static ImageProcessor impulseResponse(int width, int height, double[][] kernel) {

        ImageProcessor ip = new ByteProcessor(width, height);
        ip.setValue(0);
        ip.fill();

        ip.putPixel(width/2, height/2, 255);

        ImageProcessor ip2 = GrayTools.convolution(ip, kernel);
        double maxPixel =  ip2.getMax();
        double factor = 255.0 / maxPixel;

        for (int v = 0; v < height; v++) {
            for (int u = 0; u < width; u++) {
                int p = (int) (ip.getPixel(u, v) * factor);
                ip.putPixel(u, v, p);
            }
        }
        return ip;
    }
}
