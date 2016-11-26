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
    
    /**
     * Called when a value is insterted into a cell
     * @param r The row of the cell changed
     * @param c The col t of the value changed
     * @param value The value insterted
     */
    public void onInsert(int r,int c,int value);
    
    /**
     * Called when a value is eliminated from the possibilities
     * @param r The row of the cell changed
     * @param c The col t of the value changed
     * @param value The value eliminated
     */
    public void onEliminate(int r,int c,int value);
    
    /**
     * Called when a guess is made during the backtracking algorithm
     * @param r The row the guess is made
     * @param c The col the guess is made
     * @param value The value guessed
     */
    public void onGuess(int r,int c,int value);
    
    
}
