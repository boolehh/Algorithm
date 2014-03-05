import java.util.ArrayList;
import java.util.TreeSet;

/*
 * method1:use TreeSet<String> as the dictionary container
 * method2:use Tries as the dictionary container
 */
public class BoggleSolver {
    
    //TreeSet<String> dictionary;
    Tries dictionary;
    Digraph digraph;
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new NullPointerException();
        //this.dictionary = new TreeSet<String>();
        this.dictionary = new Tries();
        for(String s : dictionary) {
            //this.dictionary.add(s);
            this.dictionary.put(s);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        ArrayList<String> allValidWords = new ArrayList<String>();
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        for (int r = 0; r < board.rows(); r++)
            for (int c = 0; c < board.cols(); c++) {
                StringBuilder sb = new StringBuilder();
                check(sb, allValidWords, board, marked, r, c);
                marked[r][c] = false;
            }
        return allValidWords;
    }
    
    private void check(StringBuilder sb, ArrayList<String> al, BoggleBoard board, boolean[][] marked, int r, int c) {
        
        marked[r][c] = true;
        sb.append(board.getLetter(r, c));
        if (board.getLetter(r, c) == 'Q')
            sb.append("U");
        if (dictionary.contains(sb.toString())) {
            if (!al.contains(sb.toString()) && sb.length() > 2)
                al.add(sb.toString());
        }
        else if (!dictionary.isPrefix(sb.toString()))
            return;
        //up, left-up, right-up, right, down, down-left, down-right, left
        if (r > 0) {
            if(!marked[r-1][c]) {
                check(sb, al, board, marked, r-1, c);
                sb.deleteCharAt(sb.length()-1);
                marked[r-1][c] = false;
            }
            if(c > 0) {
                if(!marked[r-1][c-1]) {
                    check(sb, al, board, marked, r-1, c-1);
                    sb.deleteCharAt(sb.length()-1);
                    marked[r-1][c-1] = false;
                }
            }
            if(c < board.cols()-1) {
                if(!marked[r-1][c+1]) {
                    check(sb, al, board, marked, r-1, c+1);
                    sb.deleteCharAt(sb.length()-1);
                    marked[r-1][c+1] = false;
                }
            }
        }
        if (c < board.cols()-1) {
            if(!marked[r][c+1]) {
                check(sb, al, board, marked, r, c+1);
                sb.deleteCharAt(sb.length()-1);
                marked[r][c+1] = false;
            }
        }
        if (r < board.rows()-1) {
            if(!marked[r+1][c]) {
                check(sb, al, board, marked, r+1, c);
                sb.deleteCharAt(sb.length()-1);
                marked[r+1][c] = false;
            }
            if(c > 0) {
                if(!marked[r+1][c-1]) {
                    check(sb, al, board, marked, r+1, c-1);
                    sb.deleteCharAt(sb.length()-1);
                    marked[r+1][c-1] = false;
                }
            }
            if(c < board.cols()-1) {
                if(!marked[r+1][c+1]) {
                    check(sb, al, board, marked, r+1, c+1);
                    sb.deleteCharAt(sb.length()-1);
                    marked[r+1][c+1] = false;
                }
            }
        }
        if (c > 0) {
            if(!marked[r][c-1]) {
                check(sb, al, board, marked, r, c-1);
                sb.deleteCharAt(sb.length()-1);
                marked[r][c-1] = false;
            }
        }
    }

        
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null)
            throw new NullPointerException();
        if(dictionary.contains(word)) {
            switch (word.length()) {
                case 0:
                case 1:
                case 2: return 0;
                case 3:
                case 4: return 1;
                case 5: return 2;
                case 6: return 3;
                case 7: return 5;
                default: return 11;
            }
        }
        return 0;
    }
    
    public static void main(String[] args)
    {
        //In in = new In(args[0]);
        String args0 = "dictionary-algs4.txt";
        String args1 = "board4x4.txt";
        In in = new In(args0);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        //BoggleBoard board = new BoggleBoard(args[1]);
        BoggleBoard board = new BoggleBoard(args1);
        
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
