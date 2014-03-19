/**
 * Created by Алексей Карасев on 19.03.14.
 */
public class KdTreeTest {
    @org.junit.Test
    public void testNearest() throws Exception {
        String filename = "Fixtures/5points.txt";
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        RectHV rect = new RectHV(0.160547, 0.313086, 0.530078, 0.502148);
        kdtree.range(rect);
    }
}
