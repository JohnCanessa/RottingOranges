import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


/**
 * 994. Rotting Oranges
 * https://leetcode.com/problems/rotting-oranges/
 */
public class Solution {


    /**
     * Infect adjacent oranges.
     */
    static HashSet<String> infectAdjacent0(int[][] grid, int r, int c, HashSet<String> fresh, HashSet<String> infected) {

        // **** loop once per direction (up, right, down and left) ****
        for (int i = 0; i < 4; i++) {

            // **** ****
            String coords = "";

            // **** infect up orange (if needed) ****
            if (i == 0 && r > 0 && grid[r - 1][c] == 1) {
                coords = "" + (r - 1) + c;
            }        

            // **** infect right orange ****
            if (i == 1 && c < grid[r].length - 1 && grid[r][c + 1] == 1) {
                coords = "" + r + (c + 1);
            }

            // **** infect down orange ****
            if (i == 2 && r < grid.length - 1 && grid[r + 1][c] == 1) {
                coords = "" + (r + 1) + c;
            }

            // **** infect left orange ****
            if (i == 3 && c > 0 && grid[r][c - 1] == 1) {
                coords = "" + r + (c - 1);
            }

            // **** skip this direction ****
            if (coords == "")
                continue;

            // **** move orange (if needed) ****
            if (fresh.contains(coords)) {

                // **** remove orange from the fresh set ****
                fresh.remove(coords);

                // **** add orange to the infected set ****
                infected.add(coords);
            }
        }

        // **** return set of infected oranges ****
        return infected;
    }


    // **** grid directions (up, right, down and left) ****
    static final int[][] dirs = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };


    /**
     * Infect grid adjacent oranges.
     */
    static HashSet<String> infectAdjacent(int[][] grid, int r, int c, HashSet<String> fresh, HashSet<String> infected) {

        // **** loop once per direction (up, right, down and left) ****
        for (int[] dir : dirs) {

            // **** ****
            String coords = "" + (r + dir[0]) + (c + dir[1]);

            // **** move current orange (if needed) ****
            if (fresh.contains(coords)) {

                // **** remove orange from the fresh set ****
                fresh.remove(coords);

                // **** add orange to the infected set ****
                infected.add(coords);
            }
        }

        // **** return set of infected oranges ****
        return infected;
    }


    /**
     * In a given grid, each cell can have one of three values:
     *
     * the value 0 representing an empty cell;
     * the value 1 representing a fresh orange;
     * the value 2 representing a rotten orange.
     *
     * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
     *
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange.
     * If this is impossible, return -1 instead.
     * 
     * Runtime: 12 ms, faster than 7.62% of Java online submissions.
     * Memory Usage: 38.7 MB, less than 91.13% of Java online submissions.
     */
    static int orangesRotting0(int[][] grid) {
        
        // **** initialization (fresh and rotten oranges) ****
        HashSet<String> fresh = new HashSet<String>();
        HashSet<String> rotten = new HashSet<String>();

        // **** populate hash sets with grid coordinates ****
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == 1)
                    fresh.add("" + r + c);
                else if (grid[r][c] == 2)
                    rotten.add("" + r + c);
            }
        }

        // **** initialize time ****
        int min = 0;

        // **** loop infecting oranges while we have fresh ones ****
        while (fresh.size() != 0) {

            // **** oranges that will get infected in this pass ****
            HashSet<String> infected = new HashSet<String>();

            // **** traverse all infected oranges ****
            for (String rot : rotten) {

                // **** extract grid coordinates ****
                int r = rot.charAt(0) - '0';
                int c = rot.charAt(1) - '0';

                // **** infect adjacent oranges ****
                infected = infectAdjacent0(grid, r, c, fresh, infected);
            }

            // **** did not infect new oranges ****
            if (infected.size() == 0)  {
                return -1;
            }

            // **** only consider the last infected oranges ****
            rotten = infected;

            // **** increment time ****
            min++;
        }

        // **** return time ****
        return min;
    }


    /**
     * In a given grid, each cell can have one of three values:
     *
     * the value 0 representing an empty cell;
     * the value 1 representing a fresh orange;
     * the value 2 representing a rotten orange.
     *
     * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
     *
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange.
     * If this is impossible, return -1 instead.
     * 
     * Runtime: 10 ms, faster than 9.22% of Java online submissions.
     * Memory Usage: 38.9 MB, less than 70.31% of Java online submissions.
     */
    static int orangesRotting(int[][] grid) {
        
        // **** initialization (fresh and rotten oranges) ****
        HashSet<String> fresh = new HashSet<String>();
        HashSet<String> rotten = new HashSet<String>();

        // **** populate hash sets with grid coordinates ****
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == 1)
                    fresh.add("" + r + c);
                else if (grid[r][c] == 2)
                    rotten.add("" + r + c);
            }
        }

        // **** initialize time ****
        int min = 0;

        // **** loop infecting oranges while we have fresh ones ****
        while (fresh.size() != 0) {

            // **** oranges that will get infected in this pass ****
            HashSet<String> infected = new HashSet<String>();

            // **** traverse all infected oranges ****
            for (String rot : rotten) {

                // **** extract grid coordinates ****
                int r = rot.charAt(0) - '0';
                int c = rot.charAt(1) - '0';

                // **** infect adjacent oranges ****
                for (int[] dir : dirs) {

                    // **** ****
                    String coords = "" + (r + dir[0]) + (c + dir[1]);

                    // **** move current orange (if needed) ****
                    if (fresh.contains(coords)) {

                        // **** remove orange from the fresh set ****
                        fresh.remove(coords);

                        // **** add orange to the infected set ****
                        infected.add(coords);
                    }
                }
            }

            // **** did not infect new oranges ****
            if (infected.size() == 0)  {
                return -1;
            }

            // **** only consider the last infected oranges ****
            rotten = infected;

            // **** increment time ****
            min++;
        }

        // **** return time ****
        return min;
    }


    /**
     * Test scaffolding.
     * NOT PART OF SOLUTION
     */
    public static void main(String[] args) {
        
        // **** open the scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read the number of rows ****
        int R = Integer.parseInt(sc.nextLine());

        // ???? ????
        System.out.println("main <<< R: " + R);

        // **** ****
        int[][] grid = new int[R][];

        // **** loop reading the rows for the grid ****
        for (int r = 0; r < R; r++) {

            // **** read the next row of data ****
            String[] dataRow = sc.nextLine().split(",");

            // **** create the grid row for this data ****
            grid[r] = new int[dataRow.length];

            // **** populate the grid row ****
            for (int c = 0; c < dataRow.length; c++) {
                grid[r][c] = Integer.parseInt(dataRow[c]);
            }
        }

        // **** close the scanner ****
        sc.close();

        // ???? ????
        System.out.println("main <<< grid:");
        for (int r = 0; r < R; r++)
            System.out.println(Arrays.toString(grid[r]));

        // **** compute and display the result ****
        System.out.println("main <<< orangesRotting0: " + orangesRotting0(grid));

        // **** compute and display the result ****
        System.out.println("main <<<  orangesRotting: " + orangesRotting(grid));
    }

}