import java.util.Iterator;

/**
 * Created by Алексей Карасев on 11.03.14.
 */
public class Board {

    private int[][] blocks;

    public Board(int[][] initBlocks) {
        if (initBlocks.length < 2) throw new IllegalArgumentException();
        int length = initBlocks.length;
        blocks = new int[length][length];
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++)
                blocks[i][j] = initBlocks[i][j];
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int res = 0;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++) {
                int val = blocks[i][j];
                if (val == 0) continue;
                if ((i != solutionPosition(val)[0]) || (j != solutionPosition(val)[1])) res++;
            }
        return res;
    }

    public int manhattan() {
        int res = 0;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++) {
                int val = blocks[i][j];
                if (val == 0) continue;
                res += Math.abs(i - solutionPosition(val)[0]) + Math.abs(j - solutionPosition(val)[1]);
            }
        return res;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        if ((blocks[0][0] != 0) && (blocks[0][1] != 0)) return swap(new int[]{0, 0}, new int[]{0, 1});
        return swap(new int[]{1, 0}, new int[]{1, 1});
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board board = (Board) y;
        return this.toString().equals(board.toString());
    }

    public Iterable<Board> neighbors() {
        return new Neighbors();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // val != 0 assumed
    private int[] solutionPosition(int val) {
        if (val == 0) throw new IllegalArgumentException();
        int[] result = new int[2];
        result[1] = (val - 1) % dimension();
        result[0] = (val - 1 - result[1]) / dimension();
        return result;
    }

    private Board swap(int[] x, int[] y) {
        int[][] newBlocks = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++) {
                newBlocks[i][j] = blocks[i][j];
            }
        newBlocks[x[0]][x[1]] = blocks[y[0]][y[1]];
        newBlocks[y[0]][y[1]] = blocks[x[0]][x[1]];
        Board newBoard = new Board(newBlocks);
        return newBoard;
    }

    private class Neighbors implements Iterable<Board> {
        private Board data[];
        private int size;

        public Neighbors() {
            data = new Board[4];
            size = 0;
            for (int i = 0; i < dimension(); i++)
                for (int j = 0; j < dimension(); j++) {
                    if (blocks[i][j] == 0) {
                        int[] x = new int[]{i, j};
                        if (i - 1 >= 0)
                            data[size++] = swap(x, new int[]{i - 1, j});
                        if (j + 1 < dimension())
                            data[size++] = swap(x, new int[]{i, j + 1});
                        if (i + 1 < dimension())
                            data[size++] = swap(x, new int[]{i + 1, j});
                        if (j - 1 >= 0)
                            data[size++] = swap(x, new int[]{i, j - 1});
                    }
                }
        }

        public Iterator<Board> iterator() {
            return new NeighborsIterator();
        }

        private class NeighborsIterator implements Iterator<Board> {
            private int pos;

            public NeighborsIterator() {
                pos = 0;
            }

            public boolean hasNext() {
                return pos < size;
            }

            public Board next() {
                return data[pos++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
}
