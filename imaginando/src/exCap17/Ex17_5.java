package exCap17;

import grayFilters.Bilateral;
import grayFilters.Kuwahara;
import grayFilters.PeronaMalik;
import ij.IJ;
import ij.ImagePlus;

// Use the signal-to-noise ratio (SNR) to measure the
// effectiveness of noise suppression by edge-preserving smoothing filters
// on grayscale images
public class Ex17_5 {

    public static void main(String[] args) {

        ImagePlus im = IJ.openImage("coloreando/img/diagonal-test1-noise.png");
        ImagePlus noiseIm = tools.GrayTools.addGaussianNoise(im, 2);

        // Kuwahara
        Kuwahara kuw = new Kuwahara(noiseIm);
        ImagePlus imKuwahara = kuw.doIt();

        // Bilateral
        Bilateral bl = new Bilateral(noiseIm, 2, 20);
        ImagePlus imBilateral = bl.doIt();

        // Perona - Malik
        PeronaMalik pm = new PeronaMalik(noiseIm, 0.20, 40, 10, "g1");
        ImagePlus imPerona = pm.doIt();

        // Show filtered images
        noiseIm.show();
        imKuwahara.show();
        imBilateral.show();
        imPerona.show();

        double snrKuwahara = tools.GrayTools.snr(im, imKuwahara);
        double snrBilateral = tools.GrayTools.snr(im, imBilateral);
        double snrPerona = tools.GrayTools.snr(im, imPerona);

        double snrLogKuwahara = tools.GrayTools.snrLog(im, imKuwahara);
        double snrLogBilateral = tools.GrayTools.snrLog(im, imBilateral);
        double snrLogPerona = tools.GrayTools.snrLog(im, imPerona);

        System.out.println();
        System.out.println("SNR values:");
        System.out.println("Kuwahara = " + snrKuwahara);
        System.out.println("Bilateral = " + snrBilateral);
        System.out.println("Perona-Malik = " + snrPerona);

        System.out.println();

        System.out.println("SNRLog values:");
        System.out.println("Kuwahara = " + snrLogKuwahara + " dB");
        System.out.println("Bilateral = " + snrLogBilateral + " dB");
        System.out.println("Perona-Malik = " + snrLogPerona + " dB");
    }

}
