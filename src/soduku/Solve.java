/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soduku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author zacke
 */
public class Solve {
    public static void main(String[] args) throws FileNotFoundException{
        SodukuSolver s = new SodukuSolver(readIn());
        s.printGrid();
        s.printPoss();
    }

    private static int[][] readIn() throws FileNotFoundException {
        Scanner input = new Scanner(new File("Puzzle.txt"));
        int[][] arr = new int[9][9];
        for(int r = 0;r<9;r++){
            for(int c = 0;c<9;c++){
                arr[r][c] = input.nextInt();
            }
        }
        return arr;
    }
}
