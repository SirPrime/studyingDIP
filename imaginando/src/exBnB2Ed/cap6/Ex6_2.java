package exBnB2Ed.cap6;

/*
Exercise 6.2. Implement the Sobel edge operator as defined in Eqn.
(6.10) (and illustrated in Fig. 6.6) as an ImageJ plugin. The plugin
should generate two new images for the edge magnitude E(u, v) and
the edge orientation Φ(u, v). Come up with a suitable way to display
local edge orientation.
 */

import grayFilters.Edges;
import ij.IJ;
import ij.ImagePlus;

public class Ex6_2 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("imaginando/img/building.jpg");

        Edges imEd = new Edges(im);
        ImagePlus im2 = imEd.sobel("edge");
        im2.show();
    }
}
