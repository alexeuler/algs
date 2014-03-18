import java.util.TreeSet;

/**
 * Created by Алексей Карасев on 18.03.14.
 */
public class PointSET {
    TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D point) {
        points.add(point);
    }

    public boolean contains(Point2D point) {
        return points.contains(point);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p:points) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D p: points) {
            if (rect.contains(p))
                stack.push(p);
        }
        return  stack;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D point)  {
        double min = 20; // at 0,1 scale max squared distance is 2
        double dist;
        Point2D result = null;
        for (Point2D p: points) {
            if ((dist = point.distanceSquaredTo(p)) < min) {
                result = p;
                min = dist;
            }
        }
        return result;
    }
}
