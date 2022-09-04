package tools;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class GrayImStats {

    ImagePlus im;

    public GrayImStats(ImagePlus im) {
        this.im = im;
    }

    public int mean() {
        int M = this.im.getWidth();
        int N = this.im.getHeight();
        ImageProcessor ip = this.im.getProcessor();

        int A = 0;
        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                A += ip.get(u,v );
            }
        }
        return A / (M * N);
    }

    public int variance() {
        int M = this.im.getWidth();
        int N = this.im.getHeight();
        ImageProcessor ip = this.im.getProcessor();

        int A = 0;
        int B = 0;
        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                int p = ip.get(u, v);
                A += p;
                B += p * p;
            }
        }
        return (B - A*A / (M*N)) / (M*N);
    }
}
