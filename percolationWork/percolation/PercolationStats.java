import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("index is out of boundary.");
        }
        double[] res = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int a = StdRandom.uniform(1, n + 1);
                int b = StdRandom.uniform(1, n + 1);
                // System.out.println(a + " " + b);
                p.open(a, b);
            }
            res[i] = (double) p.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(res);
        stddev = StdStats.stddev(res);
        confidenceLo = mean() - (CONFIDENCE * stddev() / Math.sqrt(res.length));
        confidenceHi = mean() + (CONFIDENCE * stddev() / Math.sqrt(res.length));
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        // System.out.println(Arrays.toString(ps.res));
        System.out.printf("%-25s %s %f \n", "mean", "\t= ", ps.mean());
        System.out.printf("%-25s %s %f \n", "stddev", "\t= ", ps.stddev());
        System.out.printf("95%% confidence interval \t=  [%f, %f]",
                ps.confidenceLo(), ps.confidenceHi());
    }
}
