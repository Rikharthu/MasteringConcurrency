package com.example.uberv.threads.matrixmulitplication;

public class RowMultiplierTask implements Runnable {

    private final double[][] result;
    private final double[][] matrix1;
    private final double[][] matrix2;

    private final int row;

    public RowMultiplierTask(double[][] result, double[][] matrix1,
                             double[][] matrix2, int i) {
        this.result = result;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.row = i;
    }

    @Override
    public void run() {
        for (int column = 0; column < matrix2[0].length; column++) {
            result[row][column] = 0;
            for (int column2 = 0; column2 < matrix1[row].length; column2++) {
                result[row][column] += matrix1[row][column2] * matrix2[column2][column];
            }
        }
    }
}
