package exCap17;// Exercise 17.2 about Kuwahara color filter.

import colorFilters.Kuwahara;
import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;

public class Ex17_2 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("coloreando/img/postcard2c-d1.png");
        ColorProcessor cp = im.getProcessor().convertToColorProcessor();
        ColorProcessor cp2 = Kuwahara.colorFilter(cp);
        ImagePlus im2 = new ImagePlus("filters.Kuwahara per variance", cp2);
        new FileSaver(im2).saveAsPng("coloreando/img/kuwahara_apache.png");

        im2.show();
    }
}
