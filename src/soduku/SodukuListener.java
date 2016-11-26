/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soduku;

/**
 *
 * @author zacke
 */
public interface SodukuListener {
    
    public void onInsert(int r,int c,int value);
    
    public void onEliminate(int r,int c,int value);
    
    public void onGuess(int r,int c,int value);
    
    
}
