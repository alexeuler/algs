import org.omg.CORBA._PolicyStub;

import java.util.TreeSet;

/**
 * Created by Алексей Карасев on 18.03.14.
 */
public class KdTree {

    private Node top;
    private int size;

    public KdTree() {
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        Node current = top;
        Node prev = null;
        boolean horizontal = true;
        int level = 0;
        
        while (current != null) {
            horizontal = !horizontal;
            prev = current;
            if (less(point, current.point, horizontal))
                current = current.lb;
            else
                current = current.rt;
        }

        if (prev == null)
            top = new Node(point, new RectHV(0,0,1,1), false);
        else {
            if (less(point, current.point, horizontal))
                prev.lb = new Node(point, prev.getLBRect(), horizontal);
            else
                prev.rt = new Node(point, prev.getRTRect(), horizontal);
        }
    }

    public boolean contains(Point2D point) {
        return true;
    }

    // draw all of the points to standard draw
    public void draw() {
        drawTree(top);
    }


    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D point) {
        return null;
    }

    private boolean less(Point2D p1, Point2D p2, boolean horizontal) {
        if (horizontal == true) {
            if (p1.x() < p2.x()) return true;
            else return false;
        }
        else {
            if (p1.y() < p2.y()) return true;
            else return false;
        }
    }

    private void drawTree(Node n) {
        if (n==null) return;
        n.draw();
        drawTree(n.lb);
        drawTree(n.rt);
    }

    private class Node {

        private Point2D point;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private boolean horizontal;

        public Node(Point2D p, RectHV r, boolean h) {
            point = p;
            rect = r;
            horizontal = h;
        }

        public RectHV getLBRect() {
            if (!horizontal)
                return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
            else
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
        }

        public RectHV getRTRect() {
            if (!horizontal)
                return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
        }

        public void draw() {
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            point.draw();

            if (horizontal) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(rect.xmin(), point.y(), rect.xmax(), point.y());
            }
            else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(point.x(), rect.ymin(), point.x(), rect.ymax());
            }
        }
    }
}
