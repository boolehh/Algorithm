//import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.NoSuchElementException;
//import java.util.Scanner;

public class Solver {
    
    private MinPQ<Board> solution;
    private Board board;
    private int moves;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();
        solution = new MinPQ<Board>();
        board = initial;
        moves = 0;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        Board twinBoard = board.twin();
        Board copyBoard = board;
        //Solver twinSolver = new Solver(twinBoard);
        MinPQ<Board> pq1;
        MinPQ<Board> pq2;
        while ((!copyBoard.isGoal()) && (!twinBoard.isGoal())) {
            pq1 = (MinPQ<Board>) copyBoard.neighbors();
            //for (Board b : pq1) {
                //StdOut.print(b.toString());
                //StdOut.println(b.manhattan());
            //}
            //StdOut.println();
            copyBoard = pq1.min();
            moves++;
            //StdOut.println(copyBoard.toString());
            pq2 = (MinPQ<Board>) twinBoard.neighbors();
            twinBoard = pq2.delMin();
            //StdOut.println(twinBoard.toString());
            //solution.add(twinBoard);
        }
        if (copyBoard.isGoal()) return true;
        return false;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (moves == 0)
            solution();
        return moves;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        //if (!this.isSolvable()) return null;
        moves = 0;
        MinPQ<Board> pq;
        if (board.isGoal()) return null;
        solution.insert(board);
        while (!board.isGoal()) {
            pq = (MinPQ<Board>) board.neighbors();
            board = pq.delMin();
            solution.insert(board);
            moves++;
        }
        return solution;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        // create initial board from file
        //String file1 = "puzzle-unsolvable3x3.txt";
        //String file2 = "puzzle04.txt";
        //Scanner file = new Scanner(new File(file2));
        In in = new In(args[0]);
        //In in = new In(file1);
        int N = in.readInt();
        //int N = file.nextInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                //blocks[i][j] = file.nextInt();
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
    
        // solve the puzzle
        Solver solver = new Solver(initial);
    
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}