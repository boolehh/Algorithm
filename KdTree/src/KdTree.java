import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class KdTree {
   
    private int size;
    private int axis;
    private Node root;
    private class Node{
        private Point2D point;
        private int deepth;
        private Node left;
        private Node right;
    }
    private static final int D = 2;
    private static final int X_AXIS = 0;
    private static final int Y_AXIS = 1;
    private static final Comparator<Point2D> XORDER = new XOrder();
    private static final Comparator<Point2D> YORDER = new YOrder();
    //private static final int Z_AXIS = 2;
    // construct an empty set of points
    public KdTree() {
        size = 0;
        root = null;
        axis = 0;
    }
    private static class XOrder implements Comparator<Point2D> {

        @Override
        public int compare(Point2D arg0, Point2D arg1) {
            // TODO Auto-generated method stub
            if (arg0.x() < arg1.x()) return -1;
            else if (arg0.x() > arg1.x()) return 1;
            return 0;
        }
        
    }
    private static class YOrder implements Comparator<Point2D> {

        @Override
        public int compare(Point2D o1, Point2D o2) {
            // TODO Auto-generated method stub
            if (o1.y() < o2.y()) return -1;
            else if (o1.y() > o2.y()) return 1;
            return 0;
        }
        
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // number of points in the set
    public int size() {
        return size;
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        if (root == null) {
            root.point = p; //new Point2D(p.x(), p.y());
            root.deepth = 0;
            return;
        }
        Node node = root;
        while (true) {
            axis = node.deepth % D;
            if (axis == X_AXIS) {
                if (XORDER.compare(p, node.point) < 0) {
                    if (node.left == null) {
                        node.left = new Node();
                        node.left.deepth = node.deepth + 1;
                        node.left.point = p;
                        break;
                    }
                    else
                        node = node.left;
                }
                else {
                    if (node.right == null) {
                        node.right = new Node();
                        node.right.deepth = node.deepth + 1;
                        node.right.point = p;
                        break;
                    }
                    else
                        node = node.right;
                }
            }
            else {
                if (YORDER.compare(p, node.point) < 0) {
                    if (node.left == null) {
                        node.left = new Node();
                        node.left.deepth = node.deepth + 1;
                        node.left.point = p;
                        break;
                    }
                    else
                        node = node.left;
                }
                else {
                    if (node.right == null) {
                        node.right = new Node();
                        node.right.deepth = node.deepth + 1;
                        node.right.point = p;
                        break;
                    }
                    else
                        node = node.right;
                }
            }
        }
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        Node node = root;
        while (true) {
            axis = node.deepth % D;
            if (axis == X_AXIS) {
                if (XORDER.compare(p, node.point) < 0) {
                    if (node.left == null) return false;
                    node = node.left;
                }
                else if (XORDER.compare(p, node.point) > 0) {
                    if (node.right == null) return false;
                    node = node.right;
                }
                else {
                    if (p.equals(node.point)) return true;
                    node = node.right;
                }
            }
            else {
                if (YORDER.compare(p, node.point) < 0) {
                    if (node.left == null) return false;
                    node = node.left;
                }
                else if (YORDER.compare(p, node.point) > 0) {
                    if (node.right == null) return false;
                    node = node.right;
                }
                else {
                    if (p.equals(node.point)) return true;
                    node = node.right;
                }
            }
        }
    }
    
    // draw all of the points to standard draw
    public void draw() {
        travelDraw(root);
    }
    private void travelDraw(Node p) {
        if(p != null)
            travelDraw(p.left);
        StdDraw.point(p.point.x(), p.point.y());
        if(p != null)
            travelDraw(p.right);
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        LinkedList<Point2D> ranged = new LinkedList<Point2D>();
        Node node = root;
        rangePoint(node, ranged, rect);
        return ranged;
    }
    private void rangePoint(Node node, LinkedList<Point2D> arraynode, RectHV rect) {
        if (node == null) return;
        axis = node.deepth % D;
        if (rect.contains(node.point)) {
            rangePoint(node.left, arraynode, rect);
            rangePoint(node.right, arraynode, rect);
        }
        else {
            if (axis == X_AXIS) {
                if (node.point.x() > rect.xmax()) {
                    rangePoint(node.left, arraynode, rect);
                }
                else 
                    rangePoint(node.right, arraynode, rect);
            }
            else {
                if (node.point.y() > rect.ymax()) {
                    rangePoint(node.left, arraynode, rect);
                }
                else 
                    rangePoint(node.right, arraynode, rect);
            }
        }
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        //
        Point2D np = null;
        if (root == null) return null;
        Node node = root;
        
        return np;
    }
}