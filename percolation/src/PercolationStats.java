public class PercolationStats {

    private double[] results;

    public PercolationStats(int N, int T) {
        if ((N <= 0) || (T <= 0)) {
            throw (new IllegalArgumentException());
        }
        results = new double[T];
        for (int i = 0; i < T; i++) {
            results[i] = getPercolationShare(N);
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    private double getPercolationShare(int count) {
        Percolation p = new Percolation(count);
        int opened = 0;
        while (!p.percolates()) {
            int i, j;
            do {
                i = StdRandom.uniform(count) + 1;
                j = StdRandom.uniform(count) + 1;
            } while (p.isOpen(i, j));
            p.open(i, j);
            opened++;
        }
        return (double) opened / (count * count);
    }

    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        int iterations = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(count, iterations);
        StdOut.printf("Count:%d\n", count);
        StdOut.printf("Iterations:%d\n", iterations);
        StdOut.printf("Mean:%f\n", p.mean());
        StdOut.printf("Stddev:%f\n", p.stddev());
        StdOut.printf("Confidence interval:%f - %f\n", p.confidenceLo(), p.confidenceHi());
    }


}
