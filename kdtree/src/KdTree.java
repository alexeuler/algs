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
        boolean horizontal = false;

        while (current != null) {
            horizontal = !horizontal;
            prev = current;
            if (less(point, current.point, horizontal))
                current = current.lb;
            else
                current = current.rt;
        }

        if (prev == null)
            top = new Node(point, new RectHV(0, 0, 1, 1), false);
        else {
            if (less(point, prev.point, horizontal))
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
        Stack<Point2D> result = new Stack<Point2D>();
        findRange(top, rect, result);
        return result;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D point) {
        return findNearest(top, point).node.point;
    }

    private OptimalNode findNearest(Node current, Point2D subject) {
        if (current == null) return new OptimalNode(null, 20); //1x1 coords assumed
        double dist = current.point.distanceSquaredTo(subject);
        OptimalNode minNode = less(current.point, subject, current.horizontal) ? findNearest(current.lb, subject) : findNearest(current.rt, subject);
        double distToLine = current.horizontal ? Math.pow(subject.y() - current.point.y(), 2) : Math.pow(subject.x() - current.point.x(), 2);
        if (distToLine < minNode.distance) {
            OptimalNode twinNode = !less(current.point, subject, current.horizontal) ? findNearest(current.lb, subject) : findNearest(current.rt, subject);
            if (twinNode.distance < minNode.distance) minNode = twinNode;
        }
        if (dist < minNode.distance)
            return new OptimalNode(current, dist);
        else
            return minNode;
    }

    private void findRange(Node current, RectHV rect, Stack<Point2D> result) {
        if (current == null) return;
        Point2D point = current.point;
        if (rect.contains(point))
            result.push(point);
        if (current.horizontal) {
            if (rect.ymax() >= point.y()) findRange(current.rt, rect, result);
            if (rect.ymin() <= point.y()) findRange(current.lb, rect, result);
        } else {
            if (rect.xmax() >= point.x()) findRange(current.rt, rect, result);
            if (rect.xmin() <= point.x()) findRange(current.lb, rect, result);
        }
    }

    private boolean less(Point2D p1, Point2D p2, boolean horizontal) {
        if (horizontal) {
            if (p1.x() < p2.x()) return true;
            else return false;
        } else {
            if (p1.y() < p2.y()) return true;
            else return false;
        }
    }

    private void drawTree(Node n) {
        if (n == null) return;
        n.draw();
        drawTree(n.lb);
        drawTree(n.rt);
    }

    private class OptimalNode {
        public Node node;
        public double distance;

        public OptimalNode(Node node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        public String toString() {
            return "Point: "+node.toString()+"   Distance: "+distance;
        }
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

        public String toString() {
            return point.toString();
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
                StdDraw.setPenRadius(.005);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(rect.xmin(), point.y(), rect.xmax(), point.y());
            } else {
                StdDraw.setPenRadius(.005);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(point.x(), rect.ymin(), point.x(), rect.ymax());
            }
        }
    }
}
