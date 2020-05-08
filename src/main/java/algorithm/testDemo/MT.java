package algorithm.testDemo;

import java.util.HashSet;
import java.util.Set;

public class MT {
    public static void main(String[] args) {
        Set<int[]> set = new HashSet<>();
        set.add(new int[]{1, 1});
        set.add(new int[]{1, 1});
        int[] a = new int[] {1, 1};
        System.out.println(a.equals(new int[]{1, 1}));
        System.out.println(set.size());
    }

    public static double estimate(double[] x, double[] y, int i) {
        double a = getXc(x, y);
        double b = getC(x, y, a);
        return a * i + b;
    }

    public static double getXc(double[] x, double[] y) {
        int n = x.length;
        return (n * pSum(x, y) - sum(x) * sum(y))
                / (n * sqSum(x) - Math.pow(sum(x), 2));
    }

    public static double getC(double[] x, double[] y, double a) {
        int n = x.length;
        return sum(y) / n - a * sum(x) / n;
    }

    private static double sum(double[] ds) {
        double s = 0;
        for (double d : ds) s = s + d;
        return s;
    }

    private static double sqSum(double[] ds) {
        double s = 0;
        for (double d : ds) s = s + Math.pow(d, 2);
        return s;
    }

    private static double pSum(double[] x, double[] y) {
        double s = 0;
        for (int i = 0; i < x.length; i++) s = s + x[i] * y[i];
        return s;
    }
}
