package colorFilters;// Exercise 17.1 Burguer & Burge 2nd. Ed.

import ij.process.ImageProcessor;

public class RangeFilter {

//    public static void main(String[] args) {
//        ImagePlus im = IJ.openImage("img/girl.jpg");
//        ImageConverter ic = new ImageConverter(im);
//        ic.convertToGray8();
//        ImageProcessor ip = im.getProcessor();
//        int r = 2;  // kernel's radii
//        int s = 50; // standard deviation
//        ImageProcessor ipf = range(ip, r, s);
//
//        ImagePlus im2 = new ImagePlus("Pure range filter", ipf);
//        new FileSaver(im2).saveAsPng("img/girl_" + r + s + ".png");
//        im2.show();
//    }

    public static ImageProcessor rangeF(ImageProcessor ip, int r, int s) {
        int w = ip.getWidth();
        int h = ip.getHeight();
        ImageProcessor ip2 = ip.duplicate();

        for (int v = r-1; v < h - r + 1; v++) {  // goes over rows
            for (int u = r-1; u < w - r + 1; u++) {  // goes over columns
                double sp = 0;  // for sum of products \sum I*Hr;
                double sw = 0;  // for sum of weights \sum Hr
                for (int j = -r; j < r + 1 ; j++) {
                    for (int i = -r; i < r + 1; i++) {
                        int uv = ip.getPixel(u, v);
                        int ij = ip.getPixel(u+i, v+j);
                        double x = uv - ij; // range
                        // Gaussian expression:
                        var Hr = 1/(Math.sqrt(2*Math.PI)/s) * Math.exp(-x*x/2/s/s);
                        sp = sp + ij * Hr;
                        sw = sw + Hr;
                    }
                }
                double pp = sp / sw;
                int np = (int) pp;
                ip2.putPixel(u, v, np % 256);
            }
        }
        return ip2;
    }
}
