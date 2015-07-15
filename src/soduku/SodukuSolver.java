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
 *
 * @author zacke
 */
public class SodukuSolver {

    private int[][] grid;
    private HashSet<Integer>[][] possibilities;

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

    public void insert(int n, int row, int col) {
        if (n < 1 || n > 9) {
            throw new IllegalArgumentException();
        }
        //System.out.println(row + ", " + col + " is " + n);
        possibilities[row][col] = null;
        grid[row][col] = n;
        clearRow(n, row);
        clearCol(n, col);
        clearBlock(n, row, col);
        ramify(row,col);
    }
    
    public void ramify(int row, int col){
        analyzeRowSinglePos(row);
        analyzeColSinglePos(col);
        analyzeBlocksSinglePos(row, col);
    }

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

    public void analyzeBlocksSinglePos(int row, int col) {
        for (int x = -2; x < 3; x++) {
            analyzeBlock(row + 3 * x, col);
            analyzeBlock(row, col + 3 * x);
        }
    }

    public void printGrid() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print("" + grid[r][c] + " ");
            }
            System.out.println();
        }
    }

    public void printPoss() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print("" + possibilities[r][c] + " ");
            }
            System.out.println();
        }
    }

    public int[][] getGrid() {
        int[][] arr = new int[9][];
        for (int x = 0; x < 9; x++) {
            arr[x] = Arrays.copyOf(grid[x], 9);
        }
        return arr;
    }
}
