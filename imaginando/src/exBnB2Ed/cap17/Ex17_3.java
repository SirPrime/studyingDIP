package exBnB2Ed.cap17;// Exercise 17.3 about Bilateral filters

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;

public class Ex17_3 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("imaginando/img/postcard2c-d1.png");

        // filters.Bilateral filter
        colorFilters.Bilateral bl = new colorFilters.Bilateral(im, 2, 20);
        ImagePlus im2 = bl.colorFilter();
        new FileSaver(im2).saveAsPng("imaginando/img/filters.Bilateral.png");

        // filters.Bilateral separable soloX
//        filters.Bilateral bl = new filters.Bilateral(im, 2, 20);
//        ImagePlus im2 = bl.filter1D(im, 'X');
//        new FileSaver(im2).saveAsPng("imaginando/img/BilateralSoloX.png");

        // filters.Bilateral separable soloX
//        filters.Bilateral bl = new filters.Bilateral(im, 2, 20);
//        ImagePlus im2 = bl.filter1D(im, 'Y');
//        new FileSaver(im2).saveAsPng("imaginando/img/BilateralSoloY.png");

        // filters.Bilateral separable
//        filters.Bilateral bl = new filters.Bilateral(im, 2, 20);
//        ImagePlus im2 = bl.filterSeparable('Y');
//        new FileSaver(im2).saveAsPng("imaginando/img/BilateralSeparable.png");

        im2.show();
    }
}
