package exCap17;

import colorFilters.RangeFilter;
import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

// Exercise 17.1 about pure range filters.
public class Ex17_1 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("coloreando/img/postcard2c-d1.png");
        ImageConverter ic = new ImageConverter(im);
        ic.convertToGray8();
        ImageProcessor ip = im.getProcessor();
        int r = 2;  // kernel's radii
        int s = 50; // standard deviation
        ImageProcessor ipf = RangeFilter.rangeF(ip, r, s);
        ImagePlus im2 = new ImagePlus("Pure range filter", ipf);
        new FileSaver(im2).saveAsPng("coloreando/img/postcard_" + r + s + ".png");

        im2.show();
    }
}
