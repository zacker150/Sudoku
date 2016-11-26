package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import soduku.SodukuSolver;

/**
 *
 * @author zacke
 */
public class RootFrame extends JFrame {
    
    public SodukuSolver solver;

    public RootFrame() {
        initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        this.setTitle("Soduku");
        
        SodukuBoardPanel p = new SodukuBoardPanel();
        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JButton solveButton = new JButton("Solve");
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(p)
                .addComponent(solveButton));
        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(p).addComponent(solveButton));
        this.setLayout(layout);
        this.setVisible(true);
        
    }

    public static void main(String[] args){
        RootFrame frame = new RootFrame();
    }
}
