package grayFilters;

import ij.ImagePlus;
import ij.process.FloatProcessor;
import tools.Conductivity;

public class PeronaMalik {

    // Attributes
    ImagePlus im;  // 8 bits gray image
    double alpha;  // update rate
    int kappa;  // smoothness parameter
    int T;  // number of iterations
    String conductivityFunction;

    // Constructor
    public PeronaMalik(ImagePlus im, double alpha, int kappa, int T, String conductivityFunction) {

        this.im = im;
        this.alpha = alpha;
        this.kappa = kappa;
        this.T = T;
        this.conductivityFunction = conductivityFunction;
    }

    public ImagePlus doIt() {
        FloatProcessor fp = (FloatProcessor) im.getProcessor().convertToFloat();
        float[][] fpAr = fp.getFloatArray();  // Transposed size
        int M = this.im.getWidth();
        int N = this.im.getHeight();

        double[][] Dx = new double[N][M];
        double[][] Dy = new double[N][M];

        double delta0, delta1, delta2, delta3;
        for (int n = 0; n < this.T; n++) {  // T iterations
            for (int v = 0; v < N - 1; v++) {
                for (int u = 0; u < M - 1; u++) {
                    double px1 = fp.getPixelValue(u + 1, v);
                    double px0 = fp.getPixelValue(u, v);
                    Dx[v][u] = px1 - px0;

                    double py1 = fp.getPixelValue(u, v + 1);
                    double py0 = fp.getPixelValue(u, v);
                    Dy[v][u] = py1 - py0;
                }
            }

            for (int v = 0; v < N; v++) {
                for (int u = 0; u < M; u++) {
                    delta0 = Dx[v][u];
                    delta1 = Dy[v][u];
                    delta2 = (u > 0) ? -Dx[v][u - 1] : 0F;
                    delta3 = (v > 0) ? -Dy[v - 1][u] : 0F;

                    double[] delta = {delta0, delta1, delta2, delta3};
                    double s = 0.0;
                    for (double d : delta) {
                        double absD = Math.abs(d);
                        s += Conductivity.function(absD, this.kappa, this.conductivityFunction)*d;
                    }
                    fpAr[u][v] += alpha*s;
                    }
                }
            }
        FloatProcessor fp2 = new FloatProcessor(fpAr);
        return new ImagePlus("Perona-Malik gray", fp2);
        }
    }

