package tools;

public class LinAlg {

    /**
     * Sum of two vectors
     * @param vector1: first vector
     * @param vector2: second vector
     * @return Sum of the vectors
     */
    public static double[] add(double[] vector1, double[] vector2) {

        int n = vector1.length;
        double[] s = new double[n];
        for (int i = 0; i < n; i++) {
            s[i] = vector1[i] + vector2[i];
        }
        return s;
    }

    /**
     * Dot product
     * @param vector1: first vector
     * @param vector2: second vector
     * @return A real number
     */
    public static double dot(double[] vector1, double[] vector2) {

        int n = vector1.length;
        double p = 0;
        for (int i = 0; i < n; i++) {
            p += vector1[i]*vector2[i];
        }
        return p;
    }

    /**
     * L2 norm
     * @param vector: vector
     * @return A real number
     */
    public static double norm(double[] vector) {
        return Math.sqrt(dot(vector, vector));
    }

    /**
     * Sum of a scalar to each component of a vector
     * @param vector: vector
     * @param real: scalar
     * @return Vector
     */
    public static double[] mapMultiply(double[] vector, double real) {
        int n = vector.length;
        double[] p = new double[n];

        for (int i = 0; i < n; i++) {
            p[i] = real * vector[i];
        }
        return p;
    }

    /**
     * Sum of a scalar to each component of a vector
     * @param vector: vector
     * @param real: scalar
     * @return Vector
     */
    public static double[] mapAdd(double[] vector, double real) {
        int n = vector.length;
        double[] s = new double[n];

        for (int i = 0; i < n; i++) {
            s[i] = real + vector[i];
        }
        return s;
    }

    public static double[] ebeMultiply(double[] vector) {
        int n = vector.length;
        double[] p = new double[n];

        for (int i = 0; i < n; i++) {
            p[i] = vector[i]*vector[i];
        }
        return p;
    }


}
