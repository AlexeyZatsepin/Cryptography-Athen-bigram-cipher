package crypt.lab.two;


public class QuardMatrix{
    private int [][] M;

    public QuardMatrix(int a,int b,int c,int d) {
        M = new int[][]{{a,b},{c,d}};
    }

    public int[][] getM() {
        return M;
    }

    public void show() {
        for (int[] aM : M) {
            for (int j = 0; j < M[0].length; j++) {
                System.out.format("%6d ", aM[j]);
            }
            System.out.println();
        }
    }

    public QuardMatrix multiply(QuardMatrix matrix){
        int m = M.length;
        int n = M[0].length;
        int o = matrix.getM().length;
        int[][] res = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    res[i][j] += M[i][k] * matrix.getM()[k][j];
                }
            }
        }
        return new QuardMatrix(res[0][0],res[0][1],res[1][0],res[1][1]);
    }
}
