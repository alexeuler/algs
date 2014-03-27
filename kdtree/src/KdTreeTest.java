import static org.junit.Assert.*;

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

    @org.junit.Test
    public void testNearestGrid() throws Exception {
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        for (int i = 0; i < 100000; i++) {
            StdOut.println(i);
            double x, y;
            double coord = StdRandom.uniform();
            if (coord > 0.5) {
                x = StdRandom.uniform(0, 99999);
                x = x / 100000;
                y = StdRandom.uniform();
            } else {
                y = StdRandom.uniform(0, 99999);
                y = y / 100000;
                x = StdRandom.uniform();
            }
            Point2D point = new Point2D(x, y);
            Point2D test = new Point2D(StdRandom.uniform(), StdRandom.uniform());
            brute.insert(point);
            kdtree.insert(point);
            Point2D nBrute = brute.nearest(test);
            Point2D kBrute = kdtree.nearest(test);
            assertTrue(nBrute.equals(kBrute));
            System.out.println(nBrute);
            System.out.println(kBrute);
        }
    }

    @org.junit.Test
    public void testNearestGrid2() throws Exception {
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        for (int i = 0; i < 100000; i++) {
            StdOut.println(i);
            double x, y;
            x = (double) StdRandom.uniform(0, 99999) / 100000;
            y = (double) StdRandom.uniform(0, 99999) / 100000;
            Point2D point = new Point2D(x, y);

//            brute.insert(point);
            kdtree.insert(point);
            Point2D test = new Point2D(StdRandom.uniform(), StdRandom.uniform());
//            Point2D nBrute = brute.nearest(test);
            Point2D kBrute = kdtree.nearest(test);
//            assertTrue(nBrute.equals(kBrute));
//            System.out.println(nBrute);
//            System.out.println(kBrute);
        }
    }


}
