import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Fast {
   public static void main(String[] args) throws FileNotFoundException {
       int i, j, N;
       int p, q, r;
       if (args.length != 1) {
           StdOut.println("Please input the filename to read!\n");
           return;
           }
       //Scanner file = new Scanner(new File("//input8.txt"));
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
       int flag = 0;
       double qq, qq1;
       for (p = 0; p < N - 2;) {
           sortPoint(points, p, N);
           for (q = p + 1; q < N - 1; q++) {
               int counter = 0;
               qq = points[p].slopeTo(points[q]);
               while (q < N - 1) {
                   qq1 = points[p].slopeTo(points[q+1]);
                   if (qq == qq1) {
                       qq = qq1;
                       q++;
                       counter++;
                   }
                   else
                       break;
               }
                   //StdOut.printf("%d %d %d", p, q, q+1);
                   //StdOut.printf("%f %f \n", points[p].slopeTo(points[q]), 
                   //points[p].slopeTo(points[q + 1]));
               if (counter > 1) {
                   //StdOut.println(counter);
                   StdOut.print(points[p].toString());
                   StdOut.print(" -> ");
                   points[p].drawTo(points[q - counter]);
                   for (r = q - counter; r < q; r++) {
                       StdOut.print(points[r].toString());
                       StdOut.print(" -> ");
                       points[r].drawTo(points[r + 1]);
                       }
                   StdOut.print(points[r].toString());
                   StdOut.println();
                   int gap = q - counter - p;
                   if (gap > 1)
                       for (; gap > 1; gap--) {
                           p++;
                           temp = points[p];
                           points[p] = points[q];
                           points[q] = temp;
                           q--;
                           }
                   p = p + counter + 2;
                   counter = 0;
                   flag = 1;    //indicates that there is a line this loop
                   }
               }
           if (flag == 0) {
               p++;
               flag = 0;
           }
       }
   }
   private static void sortPoint(Point[] points, int S, int E) {
       int i, j;
       Point temp;
       for (i = S + 1; i < E; i++)
           for (j = i+1; j < E; j++) {
               if (points[S].SLOPE_ORDER.compare(points[i], points[j]) < 0) {
                   temp = points[j];
                   points[j] = points[i];
                   points[i] = temp;
                   }
               }
       }
}
