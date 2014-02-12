import static org.junit.Assert.*;

import org.junit.Test;

public class PercolationTest {
    @Test
    public void Fixture1() {
        Percolation p = new Percolation(2);
        assertFalse(p.percolates());
        p.open(1, 2);
        assertFalse(p.percolates());
        p.open(2, 1);
        assertFalse(p.percolates());
        p.open(2, 2);
        assertTrue(p.percolates());
    }

    @Test
    public void Fixture2() {
        Percolation p = new Percolation(2);
        assertFalse(p.percolates());
        p.open(1, 2);
        assertFalse(p.percolates());
        p.open(2, 2);
        assertTrue(p.percolates());
        p.open(1, 2);
        assertTrue(p.percolates());
    }

    @Test
    public void Fixture3() {
        Percolation p = new Percolation(1);
        assertFalse(p.percolates());
        p.open(1, 1);
        assertTrue(p.percolates());
    }

    @Test
    public void isFull() {
        Percolation p = new Percolation(3);
        assertFalse(p.isFull(2, 2));
        assertFalse(p.isFull(1, 1));
        p.open(1, 1);
        assertTrue(p.isFull(1, 1));
        p.open(2, 2);
        assertFalse(p.isFull(2, 2));
        p.open(1, 2);
        assertTrue(p.isFull(1, 2));
        assertTrue(p.isFull(2, 2));
    }

    @Test
    public void corner() {
    }

}
