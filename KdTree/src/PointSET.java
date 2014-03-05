import java.nio.file.Paths;
import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    
    TreeSet<Point2D> pointSet;
    
   // construct an empty set of points
   public PointSET() {
       pointSet = new TreeSet<Point2D>();
   }
   
   // is the set empty?
   public boolean isEmpty() { return pointSet.isEmpty(); }
   
   // number of points in the set
   public int size() { return pointSet.size(); }
   
   // add the point p to the set (if it is not already in the set)
   public void insert(Point2D p) {
       if (p == null)
           throw new NullPointerException();
       pointSet.add(p);
   }
   
   // does the set contain the point p?
   public boolean contains(Point2D p) {
       if (p == null)
           throw new NullPointerException();
       return pointSet.contains(p);
   }
   
   // draw all of the points to standard draw
   public void draw() {
       Iterator<Point2D> iter = pointSet.iterator();
       Point2D temp;
       while (iter.hasNext()) {
           temp = iter.next();
           StdDraw.point(temp.x(), temp.y());
       }
   }
   
   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
       return new IterableRang(rect);
   }
   
   private class IterableRang implements Iterable<Point2D> {
       
       RectHV rect;
       
       IterableRang (RectHV rect) {
           this.rect = rect;
       }
       @Override
       public Iterator<Point2D> iterator() {
           // TODO Auto-generated method stub
           return new IteratorRang(rect);
       }
       private class IteratorRang implements Iterator<Point2D> {
           private RectHV rectp;
           TreeSet<Point2D> pointInSet;
           Iterator<Point2D> it;
           
           IteratorRang (RectHV rect) {
               if (rect == null)
                   throw new NullPointerException();
               rectp = rect;
               pointInSet = new TreeSet<Point2D>();
               Iterator<Point2D> iter = pointSet.iterator();
               Point2D temp;
               while (iter.hasNext()) {
                   temp = iter.next();
                   if (rectp.contains(temp))
                       pointInSet.add(temp);   
               }
               it = pointInSet.iterator();
           }

            @Override
            public boolean hasNext() {
                // TODO Auto-generated method stub
                return it.hasNext();
            }

            @Override
            public Point2D next() {
                // TODO Auto-generated method stub
                return it.next();
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub
                it.remove();
            }
       }   
   }
   
   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p) {
       Iterator<Point2D> iter = pointSet.iterator();
       double mindistance = Double.MAX_VALUE;
       Point2D min = null;
       Point2D temp;
       while (iter.hasNext()) {
           temp = iter.next();
           if (p.distanceTo(temp) < mindistance) {
               mindistance = p.distanceTo(temp);
               min = temp;
           }
       }
       return min;
   }
   
   public static void main(String[] args) {
       String file = "/home/boolee/workspace/week5/100k.txt";
       In in = new In(file);
       StdOut.println(Paths.get(file).toString());
       
       PointSET pset = new PointSET();
       RectHV rect = new RectHV(0.2, 0.3, 0.6, 0.4);
       Point2D p;
       double x, y;
       
       for (int i = 0; i < 100; i++) {
           x = in.readDouble();
           y = in.readDouble();
           p = new Point2D(x, y);
           StdOut.println(p.toString());
           pset.insert(p);
       }
       StdDraw.setScale(0.0, 1.0);
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
       pset.draw();
       StdDraw.setPenColor(StdDraw.RED);
       StdDraw.setPenRadius(0.003);
       rect.draw();
       Iterator<Point2D> iter = pset.range(rect).iterator();
       StdDraw.setPenColor(StdDraw.GREEN);
       StdDraw.setPenRadius(0.02);
       while (iter.hasNext()) {
           p = iter.next();
           StdDraw.point(p.x(), p.y());
       }
       StdDraw.setPenColor(StdDraw.PINK);
       StdDraw.setPenRadius(0.02);
       p = new Point2D(0.7, 0.8);
       Point2D nearest = pset.nearest(p);
       StdOut.println();
       StdOut.println(nearest.toString());
       StdDraw.point(p.x(), p.y());
       StdDraw.point(nearest.x(), nearest.y());
   }
}