import java.sql.Struct;
import java.util.LinkedList;

/**
 * Created by Алексей Карасев on 28.03.14.
 */
public class SAP {
    private Digraph graph;
    private static final int INFINITY = Integer.MAX_VALUE;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        LinkedList<Integer> vs = new LinkedList<Integer>();
        vs.add(v);
        LinkedList<Integer> ws = new LinkedList<Integer>();
        ws.add(w);
        return length(vs, ws);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        LinkedList<Integer> vs = new LinkedList<Integer>();
        vs.add(v);
        LinkedList<Integer> ws = new LinkedList<Integer>();
        ws.add(w);
        return ancestor(vs, ws);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return opt(v,w).min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return opt(v,w).arg_min;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private Optimum opt(Iterable<Integer> v, Iterable<Integer> w) {
        for (int j : v)
            if ((j < 0) || (j > graph.V() - 1)) throw new IndexOutOfBoundsException();
        for (int j : w)
            if ((j < 0) || (j > graph.V() - 1)) throw new IndexOutOfBoundsException();
        BreadthFirstDirectedPaths bfsA = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsB = new BreadthFirstDirectedPaths(graph, w);
        int min = INFINITY;
        int arg_min = INFINITY;
        for (int i = 0; i < graph.V(); i++) {
            int dist = distHash(bfsA.distTo(i), bfsB.distTo(i));
            if (dist < min) {
                min = dist;
                arg_min = i;
            }
        }
        if (min == INFINITY) {
            min = -1;
            arg_min = -1;
        }
        return new Optimum(min, arg_min);
    }

    private int distHash(int d1, int d2) {
        if ((d1 == INFINITY) || (d2 == INFINITY)) return INFINITY;
        return d1 + d2;
    }

    private class Optimum {
        public int min;
        public int arg_min;

        public Optimum(int min, int arg_min) {
            this.min = min;
            this.arg_min = arg_min;
        }
    }

}
