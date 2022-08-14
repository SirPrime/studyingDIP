package tools;

public class Conductivity {

    public static double function(double d, int kappa, String function) {

        double x = d / kappa;
        double r = 0.0;
        switch (function) {
            case "g1" -> r = Math.exp(-x * x);
            case "g2" -> r = 1.0 / (1.0 + x * x);
            case "g3" -> r = 1.0 / Math.sqrt(1.0 + x * x);
            case "g4" -> {
                double y = d / (2 * kappa);
                r = (d <= 2 * kappa) ? Math.pow(1 - y * y, 2) : 0.0;
            }
        }
        return r;
    }
}
