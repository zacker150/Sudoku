package gui;

import java.awt.GridLayout;
import javax.swing.JPanel;
import soduku.SodukuListener;

/**
 *
 * @author zacke
 */
public class SodukuBoardPanel extends JPanel implements SodukuListener{
    
    public SodukuSquare[][] grid = new SodukuSquare[9][9];

    public SodukuBoardPanel(){
        initComponents();
    }
    
    public void initComponents(){
        this.setLayout(new GridLayout(9,9,0,0));
        for(int r = 0;r<9;r++){
            for(int c = 0;c<9;c++){
                grid[r][c] = new SodukuSquare();
                this.add(grid[r][c]);
            }
        }
    }

    @Override
    public void onInsert(int r, int c, int value) {
        grid[r][c].setValue(value);
        for(int i = 1;i<10;i++){
            if(i != value){
                grid[r][c].markImpossible(i);
            }
        }
    }

    @Override
    public void onEliminate(int r, int c, int value) {
        grid[r][c].markImpossible(value);
    }

    @Override
    public void onGuess(int r, int c, int value) {
        grid[r][c].setValue(value);
    }
}
