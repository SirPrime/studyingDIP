package exBnB2Ed.cap2;

/*
Exercise 2.2. Write a new ImageJ plugin that reflects a grayscale
image horizontally (or vertically) using My_Inverter.java (Prog.
2.1) as a template. Test your new plugin with appropriate images
of different sizes (odd, even, tiny) and inspect the results
carefully.
 */

import ij.IJ;
import ij.ImagePlus;
import grayFilters.ReflectGray;

public class ex2_2 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("imaginando/img/girl-g8b.png");
        ImagePlus im2 = ReflectGray.reflect(im, "vertical");
        im2.show();
    }


}
