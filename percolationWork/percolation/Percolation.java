import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openNum;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("index is out of boundary.");
        }
        grid = new boolean[n][n];
        openNum = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        uTopBottem();
    }

    private void uTopBottem() {
        if (grid.length == 1) {
            return;
        }
        for (int i = 0; i < grid.length; i++) {
            uf.union(grid.length * grid.length, i);
        }
        for (int i = grid.length * grid.length - 1; i >= ufindex(grid.length, 1); i--) {
            uf.union(grid.length * grid.length + 1, i);
        }
    }

    private int ufindex(int row, int col) { // row and col begin from 1
        return grid.length * (row - 1) + col - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row - 1 >= grid.length || col - 1 >= grid.length || row - 1 < 0 || col - 1 < 0) {
            throw new IllegalArgumentException("index is out of boundary.");
        }
        row = row - 1;
        col = col - 1;
        if (grid.length == 1 && !grid[row][col]) {
            grid[row][col] = true;
            openNum += 1;
            return;
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            openNum += 1;
            if (row - 1 >= 0 && isOpen(row, col + 1)) {
                uf.union(ufindex(row, col + 1), ufindex(row + 1, col + 1));
            }
            if (row + 1 < grid.length && isOpen(row + 2, col + 1)) {
                uf.union(ufindex(row + 2, col + 1), ufindex(row + 1, col + 1));
            }
            if (col - 1 >= 0 && isOpen(row + 1, col)) {
                uf.union(ufindex(row + 1, col), ufindex(row + 1, col + 1));
            }
            if (col + 1 < grid.length && isOpen(row + 1, col + 2)) {
                uf.union(ufindex(row + 1, col + 2), ufindex(row + 1, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row - 1 >= grid.length || col - 1 >= grid.length || row - 1 < 0 || col - 1 < 0) {
            throw new IllegalArgumentException("index is out of boundary.");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row - 1 >= grid.length || col - 1 >= grid.length || row - 1 < 0 || col - 1 < 0) {
            throw new IllegalArgumentException("index is out of boundary.");
        }
        if (grid.length == 1) {
            return isOpen(row, col);
        }
        return uf.find(ufindex(row, col)) == uf.find(grid.length * grid.length) && isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        if (grid.length == 1) {
            return isOpen(1, 1);
        }
        return uf.find(grid.length * grid.length) == uf.find(grid.length * grid.length + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(1);
        System.out.println(p.percolates());
        System.out.println(p.isFull(1, 1));
        System.out.println(p.isOpen(1, 1));
        for (int i = 1; i <= 1; i++) {
            p.open(i, 1);
        }
        System.out.println(p.percolates());
    }
}
