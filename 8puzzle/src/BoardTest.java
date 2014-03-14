import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Алексей Карасев on 13.03.14.
 */
public class BoardTest {

    @Before
    public void Setup() throws Exception {

    }


    @Test
    public void testDimension() throws Exception {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        assertTrue(board.dimension() == 3);
    }

    @Test
    public void testIsGoal() throws Exception {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        assertTrue(board.isGoal());

        blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        board = new Board(blocks);
        assertFalse(board.isGoal());

    }


    @Test
    public void testHamming() throws Exception {
        int[][] blocks = new int[][]{{2, 1, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        assertTrue(board.hamming() == 2);

        blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        board = new Board(blocks);
        assertTrue(board.hamming() == 0);

        blocks = new int[][]{{2, 1, 4}, {3, 6, 5}, {8, 7, 0}};
        board = new Board(blocks);
        assertTrue(board.hamming() == 8);

        blocks = new int[][]{{2, 1, 3}, {4, 0, 6}, {7, 8, 5}};
        board = new Board(blocks);
        assertTrue(board.hamming() == 3);


    }

    @Test
    public void testManhattan() throws Exception {
        int[][] blocks = new int[][]{{2, 1, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        assertTrue(board.manhattan() == 2);

        blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        board = new Board(blocks);
        assertTrue(board.manhattan() == 0);

        blocks = new int[][]{{2, 3, 4}, {5, 6, 7}, {8, 1, 0}};
        board = new Board(blocks);
        assertTrue(board.manhattan() == 14);

    }

    @Test
    public void testNeighbors() throws Exception {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 0, 5}, {6, 7, 8}};
        Board board = new Board(blocks);

        Board[] neighbors = new Board[4];
        int i = 0;
        for (Board b : board.neighbors()) {
            neighbors[i++] = b;
        }

        assertEquals(neighbors[0], new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {6, 7, 8}}));
        assertEquals(neighbors[1], new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {6, 7, 8}}));
        assertEquals(neighbors[2], new Board(new int[][]{{1, 2, 3}, {4, 7, 5}, {6, 0, 8}}));
        assertEquals(neighbors[3], new Board(new int[][]{{1, 2, 3}, {0, 4, 5}, {6, 7, 8}}));

        blocks = new int[][]{{1, 0, 2}, {3, 4, 5}, {6, 7, 8}};
        board = new Board(blocks);

        neighbors = new Board[3];
        i = 0;
        for (Board b : board.neighbors()) {
            neighbors[i++] = b;
        }

        assertEquals(neighbors[0], new Board(new int[][]{{1, 2, 0}, {3, 4, 5}, {6, 7, 8}}));
        assertEquals(neighbors[1], new Board(new int[][]{{1, 4, 2}, {3, 0, 5}, {6, 7, 8}}));
        assertEquals(neighbors[2], new Board(new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}}));

        blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};
        board = new Board(blocks);

        neighbors = new Board[2];
        i = 0;
        for (Board b : board.neighbors()) {
            neighbors[i++] = b;
        }

        assertEquals(neighbors[0], new Board(new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}}));
        assertEquals(neighbors[1], new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}}));

        blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, { 7, 8, 0}};
        board = new Board(blocks);

        neighbors = new Board[2];
        i = 0;
        for (Board b : board.neighbors()) {
            neighbors[i++] = b;
        }

        assertEquals(neighbors[0], new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}}));
        assertEquals(neighbors[1], new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}}));

        blocks = new int[][]{{1, 2, 0}, {4, 5, 6}, { 7, 8, 9}};
        board = new Board(blocks);

        neighbors = new Board[2];
        i = 0;
        for (Board b : board.neighbors()) {
            neighbors[i++] = b;
        }

        assertEquals(neighbors[0], new Board(new int[][]{{1, 2, 6}, {4, 5, 0}, {7, 8, 9}}));
        assertEquals(neighbors[1], new Board(new int[][]{{1, 0, 2}, {4, 5, 6}, {7, 8, 9}}));


    }

    @Test
    public void testTwin() throws Exception {
        Board b = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assertEquals(b.twin(), new Board(new int[][]{{2, 1, 3}, {4, 5, 6}, {7, 8, 0}}));

        b = new Board(new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}});
        assertEquals(b.twin(), new Board(new int[][]{{0, 1, 2}, {4, 3, 5}, {6, 7, 8}}));
    }

    @Test
    public void testEquals() throws Exception {
        Board b = new Board(new int[][]{{1, 2, 3}, {5, 4, 6}, {7, 8, 0}});
        Board c = new Board(new int[][]{{1, 2, 3}, {5, 4, 6}, {7, 8, 0}});
        Board d = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assertTrue(b.equals(c));
        assertFalse(b.equals(d));
    }

}
