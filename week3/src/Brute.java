import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Brute {
   public static void main(String[] args) throws FileNotFoundException {
       int i, j, N;
       int p, q, r, s;
       if (args.length != 1) {
           StdOut.println("Please input the filename to read!\n");
           return;
           }
       Scanner file = new Scanner(new File(args[0]));
       if (file.hasNextInt()) {
           N = file.nextInt();
           }
       else {
           StdOut.println("No number found int the file!\n");
           file.close();
           return;
           }
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       StdDraw.setPenRadius(.02);
       StdDraw.setPenColor(StdDraw.BLUE);
       Point[] points = new Point[N];
       for (i = 0; i < N; i++) {
           points[i] = new Point(file.nextInt(), file.nextInt());
           points[i].draw();
           }
       file.close();
       Point temp;
       for (i = 0; i < N-1; i++)
           for (j = i+1; j < N; j++) {
               if (points[i].compareTo(points[j]) > 0) {
                   temp = points[j];
                   points[j] = points[i];
                   points[i] = temp;
                   }
               }
       StdDraw.setPenRadius(.005);
       StdDraw.setPenColor(StdDraw.BLACK);
       double pp, rr, ss;
       for (p = 0; p < (N-3); p++)
           for (q = p + 1; q < (N-2); q++)
               for (r = q + 1; r < (N-1); r++)
                   for (s = r + 1; s < N; s++) {
                       pp = points[p].slopeTo(points[q]);
                       rr = points[p].slopeTo(points[r]);
                       if (pp == rr) {
                           ss = points[p].slopeTo(points[s]);
                           if (rr == ss) {
                               StdOut.print(points[p].toString());
                               StdOut.print(" -> ");
                               StdOut.print(points[q].toString());
                               StdOut.print(" -> ");
                               StdOut.print(points[r].toString());
                               StdOut.print(" -> ");
                               StdOut.print(points[s].toString());
                               StdOut.print("\n");
                               points[p].drawTo(points[q]);
                               points[q].drawTo(points[r]);
                               points[r].drawTo(points[s]);
                               }
                       }
                   }
       }
}
/*
 *  int x0 = Integer.parseInt(args[0]);
        int y0 = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[2]);

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(.005);
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point2D(x, y);
            points[i].draw();
        }

        // draw p = (x0, x1) in red
        Point2D p = new Point2D(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.02);
        p.draw();


        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.POLAR_ORDER);
        for (int i = 0; i < N; i++) {
            p.drawTo(points[i]);
            StdDraw.show(100);
        }
*/
