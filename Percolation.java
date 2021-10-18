import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int nS;
    private int count;
    private boolean percolated = false;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("n should be positive");
        }
        else
        {
            nS = n;
            count = 0;
            grid = new boolean[nS][nS];
            for (int i = 0; i < nS; i++) {
                for (int j = 0; j < nS; j++)
                {
                    grid[i][j] = false;
                }
            }
            int nS2 = nS * nS;
            uf = new WeightedQuickUnionUF(nS2 + 2);
            for (int i = 0; i < nS; i++) {
                uf.union(nS2, i);
            }
            for (int i = nS2 - 1; i >= nS2 - n; i--) {
                uf.union(nS2+1, i);
            }
        }
    }
    private int getnS2()
    {
        return nS*nS;
    }
    private void validate(int row, int col)
    {
        if (row < 1 || row > nS)
        {
            throw new IllegalArgumentException("row index "+row+" is not between 1 and "+nS);
        }
        else if (col < 1 || col > nS)
        {
            throw new IllegalArgumentException("column index "+col+" is not between 1 and "+nS);
        }
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        validate(row, col);
        int rX = row - 1;
        int rY = col - 1;
        if (!grid[rX][rY])
            {
                int nS2 = getnS2();
                grid[rX][rY] = true;
                count++;
                if (rY < nS - 1 && grid[rX][rY+1]) {
                    uf.union(nS*rX+rY, nS*rX+ rY + 1);
                }
                if (rY > 0 && grid[rX][rY-1]) {
                    uf.union(nS*rX+rY - 1, nS*rX+rY);
                }
                if (rX < nS-1 && grid[rX+1][rY]) {
                    uf.union(nS*(rX+1)+rY, nS*rX+rY);
                }
                if (rX > 0 && grid[rX-1][rY]) {
                    uf.union(nS*(rX-1)+rY, nS*rX+rY);
                }
                if (!percolated && uf.find(nS2) == uf.find(nS2+1))
                {
                    percolated = true;
                }
            }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        validate(row, col);
        return grid[row-1][col-1];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        validate(row, col);
        if (row == 1)
        {
            return isOpen(row, col);
        }
        else
        {
            return uf.find(nS*(row-1)+col-1) == uf.find(getnS2());
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return count;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return percolated;
    }

}
