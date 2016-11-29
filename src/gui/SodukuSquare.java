package gui;

import java.awt.*;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author zacke
 */
public class SodukuSquare extends JPanel {

    private int value;
    private boolean[] possibilities;

    public static Font FILLFONT = new Font("Cambria", Font.BOLD, 72);
    public static Font POSSFONT = new Font("Cambria", Font.PLAIN, 12);

    public SodukuSquare() {
        reset();
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void reset(){
        value = 0;
        possibilities = new boolean[10];
        Arrays.fill(possibilities, true);
        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (value != 0) {
            g.setFont(FILLFONT);
            drawCenteredString(value,0,0,this.getWidth(),this.getHeight(),g);
        } else {
            int wid = this.getWidth()/3;
            int hig = this.getHeight()/3;
            for (int i = 1; i < 10; i++) {
                if (possibilities[i]) {
                    int x = i-1;
                    drawCenteredString(i,(x%3)*wid,(x/3)*wid,wid,hig,g);
                }
            }
        }
    }

    public void drawCenteredString(int i,int x,int y, int w, int h, Graphics g) {
        String s = "" + i;
        FontMetrics fm = g.getFontMetrics();
        x+= (w - fm.stringWidth(s)) / 2;
        y+= (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    public void setValue(int value) {
        if (value < 10 && value > -1) {
            this.value = value;
        }
        repaint();
    }

    public void markPossible(int x) {
        if (x > 9) {
            throw new IllegalArgumentException("x must be less than 9");
        }
        possibilities[x] = true;
        repaint();
    }

    public void markImpossible(int x) {
        if (x > 9) {
            throw new IllegalArgumentException("x must be less than 9");
        }
        possibilities[x] = false;
        repaint();
    }
}
