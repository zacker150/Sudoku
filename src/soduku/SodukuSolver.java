/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soduku;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This class solves a Soduku game to the best of it's ability. The numbers 1-9
 * designate a slot filled with that number, and the number 0 designates a slot
 * of unknown value
 *
 * @author Victor Zeng
 */
public class SodukuSolver {

    private int[][] grid;
    private HashSet<Integer>[][] possibilities;
    
    /**
     * Constructs a Soduku Solver with an empty map
     */
    public SodukuSolver() {
        grid = new int[9][9];
        possibilities = new HashSet[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                possibilities[row][col] = new HashSet<>();
                for (int x = 1; x < 10; x++) {
                    possibilities[row][col].add(x);
                }
            }
        }
    }

    /**
     * Constructs a SodukuSolver with a starting grid, and solves as far as it can
     *
     * @param arr
     */
    public SodukuSolver(int[][] arr) {
        this();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (arr[row][col] != 0) {
                    insert(arr[row][col], row, col);
                }
            }
        }
    }

    /**
     * Inserts a value into the grid and determines the ramifications of the
     * insertion.
     *
     * @param n The value to insert into the grid
     * @param row The row index of the slot n will be inserted into
     * @param col The column index of the slot n will be inserted into
     */
    public void insert(int n, int row, int col) {
        if (n < 1 || n > 9) {
            throw new IllegalArgumentException();
        }
        possibilities[row][col] = null;
        grid[row][col] = n;
        clearRow(n, row);
        clearCol(n, col);
        clearBlock(n, row, col);
        ramify(row, col);
    }

    /**
     * Analyzes the ramifications of any changes in the possibilities
     *
     * @param row The row index of the slot that was changed
     * @param col The column index of the slot that was changed
     */
    public void ramify(int row, int col) {
        analyzeRowSinglePos(row);
        analyzeColSinglePos(col);
        analyzeBlocksSinglePos(row, col);
    }

    /**
     * Clears a number from the possibilities of the rest of the row, and
     * inserts any squares that have only one possible value.
     *
     * @param n The number to clear the row of
     * @param row The index of the row to process
     */
    private void clearRow(int n, int row) {
        for (int c = 0; c < 9; c++) {
            HashSet<Integer> cell = possibilities[row][c];
            if (cell != null) {
                cell.remove(n);
                if (cell.size() == 1) {
                    int number = cell.iterator().next();
                    insert(number, row, c);
                }
            }
        }
    }

    /**
     * Clears a number from the possibilities of the rest of the column, and
     * inserts any squares that have only one possible value.
     * 
     * @param n The number to clear the column of
     * @param col The index of the column to clear
     */
    private void clearCol(int n, int col) {
        for (int r = 0; r < 9; r++) {
            HashSet<Integer> cell = possibilities[r][col];
            if (cell != null) {
                cell.remove(n);
                if (cell.size() == 1) {
                    int number = cell.iterator().next();
                    insert(number, r, col);
                }
            }
        }
    }
    /**
     * Clears a number from the possibilities of the rest of the block, and
     * inserts any squares that have only one possible value.
     * 
     * @param n The number to clear the block of
     * @param row The row index of a square in the block to clear
     * @param col The column index of a square in the block to clear
     */
    private void clearBlock(int n, int row, int col) {
        int blockR = row / 3;
        int blockC = col / 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int tranR = r + 3 * blockR;
                int tranC = c + 3 * blockC;
                HashSet<Integer> cell = possibilities[tranR][tranC];
                if (cell != null) {
                    cell.remove(n);
                    if (cell.size() == 1) {
                        int number = cell.iterator().next();
                        insert(number, tranR, tranC);
                    }
                }
            }
        }
    }
    /**
     * Analyzes a row for any numbers that are only possible in one location 
     * and inserts them into that location. 
     * 
     * @param row The index of the row to analyze
     */
    public void analyzeRowSinglePos(int row) {
        for (int num = 1; num < 10; num++) {
            HashSet<Integer> cols = new HashSet<Integer>();
            for (int c = 0; c < 9; c++) {
                if (grid[row][c] == num) {
                    cols.clear();
                    break;
                }
                if (possibilities[row][c] != null && possibilities[row][c].contains(num)) {
                    cols.add(c);
                }
            }
            if (cols.size() == 1) {
                int col = cols.iterator().next();
                insert(num, row, col);
            }
        }
    }
    /**
     * Analyzes a column for any numbers that are only possible in one location 
     * and inserts them into that location 
     * 
     * @param col The index of the column to analyze
     */
    public void analyzeColSinglePos(int col) {
        for (int num = 1; num < 10; num++) {
            HashSet<Integer> rows = new HashSet<Integer>();
            for (int r = 0; r < 9; r++) {
                if (grid[r][col] == num) {
                    rows.clear();
                    break;
                }
                if (possibilities[r][col] != null && possibilities[r][col].contains(num)) {
                    rows.add(r);
                }
            }
            if (rows.size() == 1) {
                int row = rows.iterator().next();
                insert(num, row, col);
            }
        }
    }

    /**
     * Analyzes a block for any numbers that are only possible in one location 
     * and inserts them into that location 
     *
     * @param row The row index of a square inside the block to analyze
     * @param col The column index of a square inside the block to analyze
     */
    public void analyzeBlock(int row, int col) {
        if (row > 8 || row < 0) {
            return;
        }
        if (col > 8 || col < 0) {
            return;
        }

        int blockR = row / 3;
        int blockC = col / 3;
        for (int num = 1; num < 10; num++) {
            HashSet<Point> locs = new HashSet<>();
            outer:
            for (int rDiv = 0; rDiv < 3; rDiv++) {
                for (int cDiv = 0; cDiv < 3; cDiv++) {
                    int r = blockR * 3 + rDiv;
                    int c = blockC * 3 + cDiv;
                    if (grid[r][c] == num) {
                        locs.clear();
                        break outer;
                    }
                    if (possibilities[r][c] != null && possibilities[r][c].contains(num)) {
                        //x == row && y == col
                        locs.add(new Point(r, c));
                    }
                }
            }
            if (locs.size() == 1) {
                Point p = locs.iterator().next();
                //System.out.println("Found" + num + " at " + p.x + " " + p.y);
                insert(num, p.x, p.y);
            }
        }

    }

    /**
     * Analyzes all blocks affected by an insertion for numbers that can only be 
     * possible in one location
     * @param row The row index of the square where a change took place
     * @param col The column index of the square where a change took place
     */
    public void analyzeBlocksSinglePos(int row, int col) {
        for (int x = -2; x < 3; x++) {
            analyzeBlock(row + 3 * x, col);
            analyzeBlock(row, col + 3 * x);
        }
    }

    /**
     * Prints the Soduku grid to the console
     */
    public void printGrid() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print("" + grid[r][c] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prints the possibilities for each grid to the console.
     */
    public void printPoss() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print("\t" + possibilities[r][c] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns the grid as an matrix of integers.
     *
     * @return
     */
    public int[][] getGrid() {
        int[][] arr = new int[9][];
        for (int x = 0; x < 9; x++) {
            arr[x] = Arrays.copyOf(grid[x], 9);
        }
        return arr;
    }
}
