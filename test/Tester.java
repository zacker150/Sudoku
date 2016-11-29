

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import soduku.SodukuSolver;

/**
 *
 * @author zacke
 */
public class Tester {
    
    public static void main(String[] args) throws FileNotFoundException{
        Scanner input = new Scanner(new File("Test.txt"));
        int test = input.nextInt();
        for(int t = 0;t<test;t++){
            int[][] puzzle = read(input);
            int[][] solution = read(input);
            SodukuSolver s = new SodukuSolver(puzzle);
            s.finishSolving();
            int[][] result = s.getGrid();
            if(aTest(result,solution)){
                System.out.println(s.isValidGrid());
                System.out.println("No wrong moves" + (t+1));
            }
            if(bTest(result,solution)){
                System.out.println("Completely solved puzzle " + (t+1));
                s.analyzeBlock(1, 5);
                s.printPoss();
            }
        }
    }
    
    public static int[][] read(Scanner input){
        int[][] arr = new int[9][9];
        for(int r = 0;r<9;r++){
            for(int c = 0;c<9;c++){
                arr[r][c] = input.nextInt();
            }
        }
        return arr;
    }

    private static boolean aTest(int[][] result, int[][] solution) {
        for(int r = 0;r<9;r++){
            for(int c = 0;c<9;c++){
                if(result[r][c] != 0 && result[r][c]!= solution[r][c]){
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean bTest(int[][] result, int[][] solution) {
        for(int r = 0;r<9;r++){
            for(int c = 0;c<9;c++){
                if(result[r][c] != solution[r][c]){
                    return false;
                }
            }
        }
        return true;
    }
}
