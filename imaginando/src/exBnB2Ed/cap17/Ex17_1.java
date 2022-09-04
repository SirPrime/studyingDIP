package exBnB2Ed.cap17;

import colorFilters.RangeFilter;
import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

/*
Exercise 17.1. Implement a pure range filter (Eqn. (17.17)) for
grayscale images, using a 1D Gaussian kernel
Hr(x) = 1 / √2π · σ · exp(−x2 / (2σ2) ).
Investigate the effects of this filter upon the image and its histogram
for σ = 10, 20, and 25.
 */
public class Ex17_1 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("imaginando/img/postcard2c-d1.png");
        ImageConverter ic = new ImageConverter(im);
        ic.convertToGray8();
        ImageProcessor ip = im.getProcessor();
        int r = 2;  // kernel's radii
        int s = 50; // standard deviation
        ImageProcessor ipf = RangeFilter.rangeF(ip, r, s);
        ImagePlus im2 = new ImagePlus("Pure range filter", ipf);
        new FileSaver(im2).saveAsPng("imaginando/img/postcard_" + r + s + ".png");

        im2.show();
    }
}
