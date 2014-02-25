public class Percolation {
    private int N;
    private WeightedQuickUnionUF perco;
    private boolean[][] openStatus;
    public Percolation(int N) {
        this.N = N + 2;
        //construct a (N+2)*(N+2)grid
        perco = new WeightedQuickUnionUF(this.N*this.N);
        openStatus = new boolean[this.N][this.N];
        //union the first and last line respectively
        int bottomline = this.N*(this.N - 1);
        for (int i = 0; i < this.N; i++) {
            perco.union(0, i);
            openStatus[0][i] = true;
            perco.union(bottomline, bottomline + i);
            openStatus[this.N-1][i] = true;
            }
        }
    public void open(int i, int j) {
        if (i >= this.N-1 || j >= this.N-1 || i < 1 || j < 1)
            throw new IndexOutOfBoundsException();
        if (openStatus[i][j])
            return;
        openStatus[i][j] = true;
        int current = i*this.N + j;
        if (openStatus[i][j-1]) {        //left side
            perco.union(current - 1, current);
            }
        if (openStatus[i-1][j]) {        //up side
            perco.union(current - this.N, current);
            }
        if (openStatus[i][j+1]) {        //right side
            perco.union(current + 1, current);
            }
        if (openStatus[i+1][j]) {        //down side
            perco.union(current + this.N, current);
            }
        }
    public boolean isOpen(int i, int j) { //original position
        if ((i < this.N-1) && (j < this.N-1) && i > 0 && j > 0)
            return openStatus[i][j];
        else
            throw new IndexOutOfBoundsException();
        }
    public boolean isFull(int i, int j) { //original position
        if (i > this.N-2 || j > this.N-2 || i < 1 || j < 1)
            throw new IndexOutOfBoundsException();
        return perco.connected(0, i*this.N + j);
        }
    public boolean percolates() {
        if (perco.connected(0, this.N*this.N-1))
            return true;
        return false;
        }
}