
public class Percolation {

    private boolean[] openSites;
    private WeightedQuickUnionUF connections;
    private WeightedQuickUnionUF fullConnections;
    private int count;

    public Percolation(int N) {
        if (N <= 0) {
            throw (new IllegalArgumentException());
        }
        count = N;
        connections = new WeightedQuickUnionUF(count * count + 2); //the last two elements are top and down dummies
        fullConnections = new WeightedQuickUnionUF(count * count + 1);
        for (int i = 1; i <= count; i++) {
            connections.union(topDummyIndex(), index(1, i));
            fullConnections.union(topDummyIndex(), index(1, i));
            if (count > 1) {
                connections.union(bottomDummyIndex(), index(count, i));
            }
        }
        openSites = new boolean[count * count];
    }

    public boolean isOpen(int i, int j) {
        if (isOutOfBounds(i, j)) {
            throw (new IndexOutOfBoundsException());
        }
        return openSites[index(i, j)];
    }

    public void open(int i, int j) {
        if (isOutOfBounds(i, j)) {
            throw (new IndexOutOfBoundsException());
        }
        if (count == 1) {
            connections.union(index(1, 1), bottomDummyIndex());
        }
        openSites[index(i, j)] = true;
        connectIfOpen(i, j, -1, 0);
        connectIfOpen(i, j, 1, 0);
        connectIfOpen(i, j, 0, 1);
        connectIfOpen(i, j, 0, -1);
    }

    public boolean isFull(int i, int j) {
        if (isOutOfBounds(i, j)) {
            throw (new IndexOutOfBoundsException());
        }
        return isOpen(i, j) && (fullConnections.find(index(i, j)) == fullConnections.find(topDummyIndex()));
    }

    public boolean percolates() {
        return connections.find(bottomDummyIndex()) == connections.find(topDummyIndex());
    }

    private void connectIfOpen(int i, int j, int offsetI, int offsetJ) {
        if (isOutOfBounds(i - offsetI, j - offsetJ)) {
            return;
        }
        if (isOpen(i - offsetI, j - offsetJ)) {
            connections.union(index(i, j), index(i - offsetI, j - offsetJ));
            fullConnections.union(index(i, j), index(i - offsetI, j - offsetJ));
        }
    }

    private boolean isOutOfBounds(int i, int j) {
        return ((i < 1) || (j < 1) || (i > count) || (j > count));
    }

    private int index(int i, int j) {
        return (i - 1) * count + j - 1;
    }

    private int topDummyIndex() {
        return count * count;
    }

    private int bottomDummyIndex() {
        return count * count + 1;
    }
}
