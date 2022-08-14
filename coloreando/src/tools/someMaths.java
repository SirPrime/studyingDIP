package tools;

public class someMaths {

    /**
     * Max element of a 3d array
     * @param array3d: 3d array
     * @return max element
     */
    public static int max3dArray(int[][][] array3d) {

        int max = 0;  //
        for (int[][] array2d : array3d) {
            for (int[] array1d : array2d ) {
                for (int i: array1d) {
                    if (i > max) {
                        max = i;
                    }
                }
            }
        }
        return max;
    }
}
