import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONST_DB  = 1.96;
    private final int t;
    private final double[] lst;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
         if (n <= 0 || trials <= 0)
         {
             throw new IllegalArgumentException("arguments should be positive");
         }
         t = trials;
         lst = new double[t];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates())
            {
                int rX = StdRandom.uniform(1, n+1);
                int rY = StdRandom.uniform(1, n+1);
                p.open(rX, rY);
            }
            long n2 = ((long) n)*((long) n);
            lst[i] = (double) (p.numberOfOpenSites())/n2;
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(lst);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(lst);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return  mean() - (CONST_DB*stddev()/Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + (CONST_DB*stddev()/Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean\t\t\t\t    = "+ ps.mean());
        StdOut.println("stddev\t\t\t\t    = "+ps.stddev());
        StdOut.println("95% confidence interval = ["+ps.confidenceLo()+", "+ps.confidenceHi()+"]");
    }
}