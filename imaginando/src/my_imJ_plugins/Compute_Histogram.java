package my_imJ_plugins;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Compute_Histogram implements PlugInFilter {

    @Override
    public int setup(String arg, ImagePlus img) {
        return DOES_8G + NO_CHANGES;
    }

    @Override
    public void run(ImageProcessor ip) {
        int[] hist = new int[256];  // histogram array
        int w = ip.getWidth();
        int h = ip.getHeight();

        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {
                int i = ip.getPixel(u, v);
                hist[i] = hist[i] + 1;
            }
        }
    }
}
