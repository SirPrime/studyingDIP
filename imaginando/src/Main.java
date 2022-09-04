import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import tools.KernelGen;

import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        ImagePlus im = IJ.openImage("imaginando/img/postcard_250.png");

        var imAr = im.getStack().getImageArray();

        // For testing things...
        for (int i = 0; i < 5; i++) {
            System.out.println(imAr[i]);
        }
    }

}
