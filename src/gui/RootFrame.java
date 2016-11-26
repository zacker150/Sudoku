package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import soduku.SodukuSolver;

/**
 *
 * @author zacke
 */
public class RootFrame extends JFrame {

    private SodukuSolver solver;
    private SodukuBoardPanel p;

    public RootFrame() {

        initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setTitle("Soduku");
        p = new SodukuBoardPanel();
        JButton solveButton = new JButton("Slow Solve");
        JButton fastButton = new JButton("Fast Solve");
        JButton loadButton = new JButton("Load board");
        

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Open Board");
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                if (chooser.showOpenDialog(((JButton)e.getSource()).getParent()) == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(f));
                        int[][] arr = new int[9][9];
                        for (int r = 0; r < 9; r++) {
                            StringTokenizer tokens = new StringTokenizer(reader.readLine());
                            for (int c = 0; c < 9; c++) {
                                arr[r][c] = Integer.parseInt(tokens.nextToken());
                            }
                        }
                        p.reset();
                        solver = new SodukuSolver();
                        solver.addListener(p);                        
                        solver.readIn(arr);
                        solver.printGrid(System.out);
                        System.out.println("Successfully loaded board.");
                    } catch (Exception ex) {
                        Logger.getLogger(RootFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        );
        
        fastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(solver != null){
                    solver.setSlowSolve(false);
                    solver.start();
                }
            }
        });
        
        solveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(solver != null){
                    solver.setSlowSolve(true);
                    solver.start();
                }
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(p)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(loadButton)
                        .addComponent(fastButton)
                        .addComponent(solveButton)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(p)
                .addGroup(layout.createParallelGroup(Alignment.CENTER)
                        .addComponent(loadButton)
                        .addComponent(fastButton)
                        .addComponent(solveButton)
                )
        );
        this.setLayout(layout);

        this.setVisible(true);

    }

    public static void main(String[] args) {
        RootFrame frame = new RootFrame();
    }
}
