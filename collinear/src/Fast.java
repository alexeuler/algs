import java.security.InvalidParameterException;
import java.util.Arrays;

/**
 * Created by Алексей Карасев on 28.02.14.
 */
public class Fast {

    private static Point[] data;
    private static Point[] sorted_data;
    private static Node printed;

    private static class Node {
        private Node next;
        private String data;

        public Node (String init) {
            this.data = init;
        }

        public void push(String item) {
            Node pointer = this;
            while (pointer.next != null) {
                pointer = pointer.next;
            }
            pointer.next = new Node(item);
        }

        public boolean has(String item) {
            Node pointer = this;
            while (pointer != null) {
                if (pointer.data.equals(item)) {return true;}
                pointer = pointer.next;
            }
            return false;
        }
    }

    private static Point parse(String s) {
        String[] coords = s.split(" ");
        String[] refined = new String[2];
        int i=0;
        for (String str : coords) {
            if (!str.equals("")) {
                refined[i] = str;
                i++;
            }
        }
        Point p = new Point(Integer.parseInt(refined[0]), Integer.parseInt(refined[1]));
        return p;
    }

    private static void findAndPrint() {
        if (data.length < 4) {
            throw new InvalidParameterException();
        }
        printed = new Node("");
        for (Point p : data) {
            Arrays.sort(sorted_data, p.SLOPE_ORDER);
            printEqual(p);
        }
    }

    private static void printEqual(Point p) {
        int n = 1;
        double slope = p.slopeTo(sorted_data[0]);
        for (int i = 1; i < sorted_data.length; i++) {
            if (p.slopeTo(sorted_data[i]) == slope) {
                n++;
                continue;
            } else {
                if (n >= 3) {
                    Point[] collinear = new Point[n+1];
                    for (int j = 0; j < n; j++) {
                        collinear[j] = sorted_data[i-1-j];
                    }
                    collinear[n] = p;
                    print(collinear);
                }
                n = 1;
                slope = p.slopeTo(sorted_data[i]);
            }
        }
        if (n >= 4) {
            Point[] collinear = new Point[n];
            for (int j = 0; j < n; j++) {
                collinear[j] = sorted_data[sorted_data.length-1-j];
            }
            print(collinear);
        }

    }

    private static void print(Point[] points) {
        int i = 0;
        Arrays.sort(points);
        String result = "";
        Point prev = null;
        for (Point p : points) {
            p.draw();
            if (i!=0) {
                prev.drawTo(p);
            }
            i++;
            if (i != points.length) {
                result+= p.toString()+" -> ";
            }
            prev = p;
        }
        result+=points[points.length - 1].toString() + "\n";
        if (!printed.has(result)){
            StdOut.print(result);
            printed.push(result);
        }
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        In in = new In(args[0]);
        int n = Integer.parseInt(in.readLine());
        data = new Point[n];
        sorted_data = new Point[n];
        for (int i = 0; i < n; i++) {
            data[i] = parse(in.readLine());
            sorted_data[i] = data[i];
        }
        findAndPrint();
    }

}
