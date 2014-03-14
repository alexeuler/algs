import java.util.Iterator;

/**
 * Created by Алексей Карасев on 13.03.14.
 */
public class Solver {

    private Node lastNode;
    private boolean solvable;
    private MinPQ<Node> queue;
    private MinPQ<Node> queueTwin;
    private SET<String> boardSet;
    private SET<String> boardSetTwin;

    public Solver(Board current) {
        Node currentNode = new Node(current, null, 0);
        Node currentTwin = new Node(current.twin(), null, 0);
        queue = new MinPQ<Node>();
        queueTwin = new MinPQ<Node>();
        boardSet = new SET<String>();
        boardSetTwin = new SET<String>();
        while ((!currentNode.board.isGoal()) && (!currentTwin.board.isGoal())) {
            makeMove(currentNode, queue, boardSet);
            makeMove(currentTwin, queueTwin, boardSetTwin);
            currentNode = queue.delMin();
            currentTwin = queueTwin.delMin();
        }
        if (currentNode.board.isGoal()) {
            solvable = true;
            lastNode = currentNode;
        } else {
            solvable = false;
            lastNode = null;
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!solvable) return -1;
        return lastNode.moves;
    }

    public Iterable<Board> solution() {
        if (!solvable) return null;
        return new Solution();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private void makeMove(Node node, MinPQ<Node> queue, SET<String> set) {
        for (Board neighbor : node.board.neighbors()) {
            if (set.contains(neighbor.toString()))
                continue;
            if ((node.prev == null) || (!neighbor.equals(node.prev.board))) {
                Node newNode = new Node(neighbor, node, node.moves + 1);
                queue.insert(newNode);
                set.add(neighbor.toString());
            }
        }
    }

    private class Solution implements Iterable<Board> {

        public BoardIterator iterator() {
            return new BoardIterator();
        }

        private class BoardIterator implements Iterator<Board> {
            private Board[] boards;
            private int size;
            private int pos;

            public BoardIterator() {
                size = moves() + 1;
                if (size == 0) return;
                boards = new Board[size];
                Node current = lastNode;
                for (int i = size - 1; i >= 0; i--) {
                    boards[i] = current.board;
                    current = current.prev;
                }
                pos = 0;
            }

            public boolean hasNext() {
                return pos < size;
            }

            public Board next() {
                return boards[pos++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }

    private class Node implements Comparable<Node> {
        public Board board;
        public Node prev;
        public int moves;
        private int priority;

        public Node(Board initBoard, Node initPrev, int initMoves) {
            moves = initMoves;
            prev = initPrev;
            board = initBoard;
            priority = -1;
        }

        public String toString() {
            return board.toString() + "Priority: " + priority() + " Moves:" + moves;
        }

        public int priority() {
            if (priority == -1)
                priority = moves + board.manhattan();
            return priority;
        }

        public int compareTo(Node that) {
            if (this.priority() < that.priority()) return -1;
            if (this.priority() == that.priority()) return 0;
            return 1;
        }
    }
}
