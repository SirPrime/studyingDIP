package tools;

import ij.ImagePlus;
import ij.process.ColorProcessor;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.Arrays;

public class RGB {

    /**
     * Convert an RGB color image to a 3D array
     * @param cp: Image
     * @return 3D array
     */
    public static int[][][] imToArray(ColorProcessor cp) {
        int w = cp.getWidth();
        int h = cp.getHeight();
        int[][][] pxArray = new int[h][w][3];

        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {
                int c = cp.getPixel(u, v); // get int-packed color pixel

                int[] rgb = pixelToRGBVector(c); // rgb components

                // rgb components into a 3D array
                pxArray[v][u][0] = rgb[0];  // red
                pxArray[v][u][1] = rgb[1];  // green
                pxArray[v][u][2] = rgb[2];  // blue
            }
        }
        return pxArray;
    }

    /**
     * Convert a 3D array to an RGB color image
     * @param array3D: 3D array
     * @return RGB color image
     */
    public static ColorProcessor arrayToIm(int[][][] array3D) {

        int w = array3D[0].length;  // width
        int h = array3D.length;  // height
        ColorProcessor cp = new ColorProcessor(w, h);  // new RGB color image

        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {
                int r = array3D[v][u][0];  // red component
                int g = array3D[v][u][1];  // green component
                int b = array3D[v][u][2];  //  blue component

                // Assembly of an RGB pixel
                int c = colorPixel(r, g, b);
                cp.putPixel(u, v, c);
            }
        }
        return cp;
    }

    /**
     * Assembly of an RGB pixel
     * @param r: red component
     * @param g: green component
     * @param b: blue component
     * @return color pixel
     */
    public static int colorPixel(int r, int g, int b) {
        // Assembly of an RGB pixel
        return ((r & 0xff) << 16) | ((g & 0xff) << 8) | b & 0xff;
    }

    /**
     * Convert an int of 32 bits in an array of 3 rgb values
     * @param c: pixel
     * @return rgb values
     */
    public static int[] pixelToRGBVector(int c) {
        int[] rgbVector = {0,0,0};

        // split color pixel into rgb-components
        rgbVector[0] = (c & 0xff0000) >> 16;  // red
        rgbVector[1] = (c & 0x00ff00) >> 8;  // green
        rgbVector[2] = c & 0x0000ff;  // blue

        return rgbVector;
    }

    /**
     * Convert a rgb color pixel in a RealVector type.
     * @param imp: Image
     * @param u: horizontal coordinate of the pixel
     * @param v: vertical coordinate of the pixel
     * @return RealVector
     */
    public static RealVector pixelToRealVector(ImagePlus imp, int u, int v) {
        int[] px = imp.getPixel(u, v);  // r, g, b, idx
        int[] aa = Arrays.stream(px, 0, 3).toArray();
        double[] a = Arrays.stream(aa).asDoubleStream().toArray();
        return new ArrayRealVector(a);
    }

    /**
     * Convert a RealVector to a 32 bits int color pixel.
     * @param RGB: RealVector
     * @return 32 bits int color pixel
     */
    public static int realVectorToPixel (RealVector RGB) {
        int R = (int) RGB.getEntry(0);
        int G = (int) RGB.getEntry(1);
        int B = (int) RGB.getEntry(2);
        return colorPixel(R, G, B);  // assembly
    }
}
