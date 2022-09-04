package exBnB2Ed.cap17;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

public class Ex17_4 {

    public static void main(String[] args) {
        ImagePlus im = IJ.openImage("coloreando/img/postcard2c-d1.png");
        ImageConverter ic = new ImageConverter(im);
        ic.convertToGray8();
        ImageProcessor ip = im.getProcessor();

        // filters.Diffusion
        int n = 5;  // number of iterations
        double alpha = 0.20;  // time increment

        ImageProcessor ip2 = colorFilters.Diffusion.iter(ip, n, alpha); // iterating
        ImageProcessor ip3 = colorFilters.Diffusion.gaussianKernel(ip, n, alpha);  // gaussian
        ImagePlus im2 = new ImagePlus("filters.Diffusion iterating", ip2);
        ImagePlus im3 = new ImagePlus("filters.Diffusion with gaussian kernel", ip3);
        new FileSaver(im2).saveAsPng("imaginando/img/diffusionIter.png");
        new FileSaver(im3).saveAsPng("imaginando/img/diffusionGauss.png");

        // Impulse response for gaussian kernel
//        double sigma = Math.sqrt(2*n*alpha);
//        int r = (int) Math.ceil(sigma);
//        double[][] gaussianKernel = grayScaleFilters.gaussian2dKernel(sigma, r);
//        ImageProcessor ip = Diffusion.impulseResponse(11,11, gaussianKernel);
//        ImagePlus im4 = new ImagePlus("Impulse response. Sigma = " + sigma, ip);
//        new FileSaver(im4).saveAsPng("imaginando/img/impulseResp" + "N" + n + ".png");

//        im2.show();
    }
}
