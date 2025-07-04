import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class FireTest {
    @Test
    public void testTimeToBurnExample() {
        char[][] forest = {
            {'t','.','.','t','t','t','t','.','t'},
            {'.','.','t','t','.','.','.','.','t'},
            {'.','.','t','t','t','t','t','t','t'},
            {'t','t','t','t','.','.','.','.','.'}
        };

        int matchR = 2;
        int matchC = 6;

        int expected = 8;
        int actual = Fire.timeToBurn(forest, matchR, matchC);

        assertEquals(expected, actual);
    }

    // Match set not on a tree
    @Test
    public void testTimeToBurn_BadArgument()
    {
        char[][] forest = {
            {'.', '.', '.'},
            {'.', '.', '.'},
            {'.', '.', '.'}
        };

        int spreadR = 1;
        int spreadC = 1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Fire.timeToBurn(forest, spreadR, spreadC);
        });

        assertEquals("The match isn't lit at a tree.", exception.getMessage());

    }

    // Whole forest burns down
    @Test
    public void testTimeToBurn_EverythingGoes()
    {
        char[][] forest = {
            {'t', 't', 't'},
            {'t', 't', 't'},
            {'t', 't', 't'}
        };

        int spreadR = 1;
        int spreadC = 1;

        int expected = 2;
        int actual = Fire.timeToBurn(forest, spreadR, spreadC);

        assertEquals(expected, actual);

    }

    // Neighbors on All Sides
    @Test
    public void testNeighborTrees_AllSides()
    {
        char[][] forest = {
            {'t', 't', 't'},
            {'t', 't', 't'},
            {'t', 't', 't'}
        };

        int spreadR = 1;
        int spreadC = 1;

        int[] startingLocation = {spreadR, spreadC};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(startingLocation);

        List<int[]> neighbors = Fire.neighborTrees(forest, spreadR, spreadC, queue);
        Set<String> neighborsSet = toSet(neighbors);

        assertTrue(neighborsSet.contains("0,1"));
        assertTrue(neighborsSet.contains("1,0"));
        assertTrue(neighborsSet.contains("1,2"));
        assertTrue(neighborsSet.contains("2,1"));

    }

    // No Neighbors
    @Test
    public void testNeighborTrees_NoSides()
    {
        char[][] forest = {
            {'.', '.', '.'},
            {'.', 't', '.'},
            {'.', '.', '.'}
        };

        int spreadR = 1;
        int spreadC = 1;

        int[] startingLocation = {spreadR, spreadC};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(startingLocation);

        List<int[]> neighbors = Fire.neighborTrees(forest, spreadR, spreadC, queue);
        Set<String> neighborsSet = toSet(neighbors);

        assertTrue(neighborsSet.contains("-1,0"));
        assertFalse(neighborsSet.contains("0,1"));
        assertFalse(neighborsSet.contains("1,0"));
        assertFalse(neighborsSet.contains("1,2"));
        assertFalse(neighborsSet.contains("2,1"));

    }

    // Corner Neighbors
    @Test
    public void testNeighborTrees_CornerTrees()
    {
        char[][] forest = {
            {'t', 't', '.'},
            {'t', '.', '.'},
            {'.', '.', '.'}
        };

        int spreadR = 0;
        int spreadC = 0;

        int[] startingLocation = {spreadR, spreadC};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(startingLocation);

        List<int[]> neighbors = Fire.neighborTrees(forest, spreadR, spreadC, queue);
        Set<String> neighborSet = toSet(neighbors);

        assertTrue(neighborSet.contains("0,1"));
        assertTrue(neighborSet.contains("1,0"));

    }



    private Set<String> toSet(List<int[]> list)
    {
        Set<String> set = new HashSet<>();
        for (int[] arr : list) set.add(arr[0] + "," + arr[1]);
        return set;
    }

}
