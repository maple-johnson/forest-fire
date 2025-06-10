import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Fire {
    /**
     * Returns how long it takes for all vulnerable trees to be set on fire if a
     * match is lit at a given location.
     * 
     * The forest is represented via a rectangular 2d char array where t represents a tree
     * and . represents an empty space.
     * 
     * At time 0, the tree at location [matchR, matchC] is set on fire. At every subsequent
     * time step, any tree that is adjacent (up/down/left/right) to a burning tree is also
     * set on fire. 
     * 
     * 
     * EXAMPLE 
     * forest:
     * 
     * t..tttt.t
     * ..tt....t
     * ..ttttttt
     * tttt.....
     * 
     * matchR: 2
     * matchC: 6
     * 
     * Result: 8
     * 
     * Explanation:
     * At time 0, the tree at (2, 6) is set on fire. At time 1, its adjacent trees also catch on fire
     * At time 2, the trees adjacent to those catch as well. At time 8, the last tree to catch is at
     * (0,6). In this example, there is one tree that never burns, so it is not included in the time calculation.
     * 
     * 
     * @param forest a 2d array where t represents a tree and . represents the ground
     * @param matchR The row the match is lit at
     * @param matchC The column the match is lit at
     * @return the time at which the final tree to be incinerated starts burning
     */
    public static int timeToBurn(char[][] forest, int matchR, int matchC)
     {
        if (forest[matchR][matchC] == '.') throw new IllegalArgumentException("The match isn't lit at a tree.");

        boolean[][] visited = new boolean[forest.length][forest[0].length];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{matchR, matchC});
        int count = -1;

        while (!queue.isEmpty())
        {
            int[] current = queue.poll();
            int curR = current[0];
            int curC = current[1];

            if (curR == -1)
            {
                count++;
                continue;
            }

            if (visited[curR][curC]) continue;

            visited[curR][curC] = true;

            queue.addAll(neighborTrees(forest, curR, curC, queue));
            
        }

        return count;
    }

    public static List<int[]> neighborTrees(char[][] forest, int spreadR, int spreadC, Queue<int[]> queue)
    {
        int[][] directions = {
            {-1, 0}, // North
            {1, 0}, // South
            {0, 1}, // East
            {0, -1} // West
        };

        List<int[]> possibleMoves =  new ArrayList<>();

        int check = 0;
        for (int[] findBurnTurn : queue)
        {
            if (findBurnTurn[0] == -1) check++;
        }

        if (check == 0) possibleMoves.add(new int[]{-1, 0});
        else check = 0;

        for (int[] direction : directions)
        {
            int changeR = direction[0];
            int changeC = direction[1];

            int newR = spreadR + changeR;
            int newC = spreadC + changeC;

            if (newR >= 0 &&
                newR < forest.length &&
                newC >= 0 && 
                newC < forest[newR].length &&
                forest[newR][newC] != '.')
            {
                possibleMoves.add(new int[]{newR, newC});
            }
        }

        return possibleMoves;
    }

}