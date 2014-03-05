import java.util.Comparator;
//import java.util.Iterator;
//import java.util.NoSuchElementException;

public class Board {
    private int[][] blocks;
    private int N;
    private int moves;
    private enum previousPosition { FROMUP, FROMDOWN, FROMLEFT, FROMRIGHT };
    //private static previousPosition direction;
    private previousPosition fromWhere;
    private static final Comparator<Board> HORDER = new HOrder();
    private static final Comparator<Board> MORDER = new MOrder();
 // construct a board from an N-by-N array of blocks
 // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException();
        N = blocks[0].length;
        this.blocks = new int [N][N]; //blocks;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                this.blocks[i][j] = blocks[i][j];
        fromWhere = null;
    }
 // board dimension N
    public int dimension() {
        return N;
    }
 // number of blocks out of place
    public int hamming() {
        int total = N * N -1;
        int wrongPosition = total;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == i * N + j + 1)
                    wrongPosition--;
                if (--total == 0)
                    break;
            }
        return wrongPosition + moves;
    }
    private static class HOrder implements Comparator<Board> {

        @Override
        public int compare(Board o1, Board o2) {
            // TODO Auto-generated method stub
            if (o1.hamming() < o2.hamming()) return -1;
            if (o1.hamming() > o2.hamming()) return 1;
            return 0;
        }
        
    }
 // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int movesToGo = 0;
        int pi, pj;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * N + j + 1) {
                    pi = (blocks[i][j] - 1) / N;
                    pj = (blocks[i][j] - 1) % N;
                    movesToGo += Math.abs(pi - i) + Math.abs(pj - j);
                }  
            }
        return movesToGo + moves;
    }
    private static class MOrder implements Comparator<Board> {

        @Override
        public int compare(Board o1, Board o2) {
            // TODO Auto-generated method stub
            if (o1.manhattan() < o2.manhattan()) return -1;
            if (o1.manhattan() > o2.manhattan()) return 1;
            return 0;
        }
        
    }
 // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != i * N + j + 1) {
                    return false;
                }
                if (i == N - 1 && j == N - 2)
                    break;
            }
        return true;
    }
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        boolean row0has0 = false;
        Board newBoard = new Board(blocks);
        for (int m = 0; m < N; m++) {
            if (newBoard.blocks[0][m] == 0)
                row0has0 = true;
        }
        if (row0has0) {
            int temp = newBoard.blocks[1][0];
            newBoard.blocks[1][0] = newBoard.blocks[1][1];
            newBoard.blocks[1][1] = temp;
        }
        else {
            int temp = newBoard.blocks[0][0];
            newBoard.blocks[0][0] = newBoard.blocks[0][1];
            newBoard.blocks[0][1] = temp;
        }
        return newBoard;
    }
 // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (this.getClass() != y.getClass()) return false;
        Board yy = (Board) y;
        if (this.dimension() != yy.dimension())
            return false;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != yy.blocks[i][j])
                    return false;
            }
        return true;
    }

 // all neighboring boards
    public Iterable<Board> neighbors() {
        MinPQ<Board> pq = new MinPQ<Board>(MORDER);
        int blanki = 0;
        int blankj = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    blanki = i;
                    blankj = j;
                }
            }
        //move blank up
        if (blanki > 0 && this.fromWhere != previousPosition.FROMDOWN) {
            Board adjacent = new Board(blocks);
            adjacent.blocks[blanki][blankj] = adjacent.blocks[blanki-1][blankj];
            adjacent.blocks[blanki-1][blankj] = 0;
            adjacent.fromWhere = previousPosition.FROMUP;
            adjacent.moves = this.moves + 1;
            pq.insert(adjacent);
        }
      //move blank down
        if (blanki < N - 1 && this.fromWhere != previousPosition.FROMUP) {
            Board adjacent = new Board(blocks);
            adjacent.blocks[blanki][blankj] = adjacent.blocks[blanki+1][blankj];
            adjacent.blocks[blanki+1][blankj] = 0;
            adjacent.fromWhere = previousPosition.FROMDOWN;
            adjacent.moves = this.moves + 1;
            pq.insert(adjacent);
        }
      //move blank left
        if (blankj > 0 && this.fromWhere != previousPosition.FROMRIGHT) {
            Board adjacent = new Board(blocks);
            adjacent.blocks[blanki][blankj] = adjacent.blocks[blanki][blankj-1];
            adjacent.blocks[blanki][blankj-1] = 0;
            adjacent.fromWhere = previousPosition.FROMLEFT;
            adjacent.moves = this.moves + 1;
            pq.insert(adjacent);
        }
      //move blank right
        if (blankj < N - 1 && this.fromWhere != previousPosition.FROMLEFT) {
            Board adjacent = new Board(blocks);
            adjacent.blocks[blanki][blankj] = adjacent.blocks[blanki][blankj+1];
            adjacent.blocks[blanki][blankj+1] = 0;
            adjacent.fromWhere = previousPosition.FROMRIGHT;
            adjacent.moves = this.moves + 1;
            pq.insert(adjacent);
        }
        //Board.direction = pq.min().fromWhere;
        //Board.moves++;
        return pq;
    }

// string representation of the board (in the output format specified below)
    public String toString() {
        String board;
        board = String.valueOf(N) + '\n';
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board += String.valueOf(blocks[i][j]) + " ";   
            }
            board += '\n';
        }
        return board;
    }
}