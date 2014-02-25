//import java.io.IOException;
import java.util.Random;

public class PercolationStats {
    private Percolation per;
    private int[] counter;
    private double mean;
    private double stddev;
    private int size;
    //private int N;
    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1)
            throw new IllegalArgumentException();
        size = N;
        counter = new int[T];
        Random random = new Random();
        int i, j, t;
        t = T;
        while (t-- > 0) {
            per = new Percolation(N);
            while (!per.percolates()) {
                i = random.nextInt(N) + 1;
                j = random.nextInt(N) + 1;
                if (per.isOpen(i, j))
                    continue;
                per.open(i, j);
                counter[t]++;
                }
            }
        }
    public double mean() {
        for (int value: counter)
            mean += value;
        mean /= counter.length;
        return mean/(size * size);
        }
    public double stddev() {
        mean();
        for (int value: counter)
            stddev += (value - mean)*(value - mean);
        stddev /= (counter.length - 1);
        //stddev /= counter.length;
        stddev = Math.sqrt(stddev);
        //stddev /= (this.N*this.N);
        return stddev/(size * size);
        }
    public double confidenceLo() {
        stddev();
        return (mean - 1.96 * stddev/Math.sqrt(counter.length))/(size * size);
        }
    public double confidenceHi() {
        stddev();
        return (mean + 1.96 * stddev/Math.sqrt(counter.length))/(size * size);
        }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.print("Input format is: ");
            StdOut.print("java PercolationStats [int x] [int y]\n");
            StdOut.println("Please re-iniput the parameters!\n");
            return;
            }
        int N = Integer.valueOf(args[0]).intValue();
        int T = Integer.valueOf(args[1]).intValue();
        if (N < 1 || T < 1)
            throw new IllegalArgumentException();
        //StdOut.printf("%d %d", N, T);
        //StdIn.readChar();
        PercolationStats pers = new PercolationStats(N, T);
        //StdOut.println(pers.mean());
        //StdOut.println(pers.stddev());
        StdOut.println("mean                    = "+pers.mean());
        StdOut.println("stddev                  = "+pers.stddev());
        StdOut.print("95% confidence interval = "+pers.confidenceLo());
        StdOut.println(", "+pers.confidenceHi());
        }
}