package exBnB2Ed.cap17;// Exercise 17.2 about Kuwahara color filter.

import colorFilters.Kuwahara;
import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;

/*
Exercise 17.2. Modify the Kuwahara-type filter for color images in
Alg. 17.3 to use the norm of the color covariance matrix (as defined
in Eqn. (17.12)) for quantifying the amount of variation in each
subregion. Estimate the number of additional calculations required
for processing each image pixel. Implement the modified algorithm,
compare the results and execution times.
 */

public class Ex17_2 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("imaginando/img/postcard2c-d1.png");
        ColorProcessor cp = im.getProcessor().convertToColorProcessor();
        ColorProcessor cp2 = Kuwahara.colorFilter(cp);
        ImagePlus im2 = new ImagePlus("filters.Kuwahara per variance", cp2);
        new FileSaver(im2).saveAsPng("imaginando/img/kuwahara_apache.png");

        im2.show();
    }
}
