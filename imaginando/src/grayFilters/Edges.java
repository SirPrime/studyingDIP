package grayFilters;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import tools.GrayTools;
import tools.Operators;

import java.util.Objects;

public class Edges {

    ImagePlus im;

    public Edges(ImagePlus im) {
        this.im = im;
    }

    /**
     * Implementation of Sobel Edge Operator
     * @param ed_or: "edge" or "orientation"
     * @return image of the edge magnitud E(u, v)
     */
    public ImagePlus sobel(String ed_or) {

        // Sobel's filters
        double[][] Sx = Operators.sobelX();
        double[][] Sy = Operators.sobelY();

        int w = this.im.getWidth();
        int h = this.im.getHeight();
        ImageProcessor ip = this.im.getProcessor();
        ImageProcessor ip2 = ip.duplicate();

        ImageProcessor ipx = GrayTools.convolution(ip, Sx);  // Dx
        ImageProcessor ipy = GrayTools.convolution(ip, Sy);  // Dy

        int q;
        for (int v = 1; v < h - 1; v++) {
            for (int u = 1; u < w - 1; u++) {
                // compute filter result for position (u, v)
                int Dx = ipx.get(u, v);
                int Dy = ipy.get(u, v);
                if (Objects.equals(ed_or, "edge")) {
                    q = (int) Math.sqrt(Dx * Dx + Dy * Dy);
                }
                else {
                    q = (int) (255.0/(0.5*Math.PI) * Math.atan2(Dy, Dx));
                }
                if (q < 0)
                    q = 0;
                if (q > 255)
                    q = 255;
                ip2.putPixel(u, v, q);
            }
        }
        return new ImagePlus("Sobel " + ed_or, ip2);
    }

    public ImagePlus laplacian(boolean improved) {

        // Laplacian filter
        double[][] Lf = new double[3][3];
        Lf = improved ? Operators.impLaplacian() : Operators.laplacian();

        int w = this.im.getWidth();
        int h = this.im.getHeight();
        ImageProcessor ip = this.im.getProcessor();
        ImageProcessor ip2 = ip.duplicate();

        return im;  // temp

    }
}
