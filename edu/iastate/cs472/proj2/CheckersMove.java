package edu.iastate.cs472.proj2;

import java.util.ArrayList;

/**
 *
 * @author btnguyen Ben Nguyen
 *
 * A CheckersMove object represents a move in the game of Checkers.
 * It holds the row and column of the piece that is to be moved
 * and the row and column of the square to which it is to be moved.
 * (This class makes no guarantee that the move is legal.)
 *
 * Update 03/18: It represents an action in the game of Checkers.
 * There may be a single move or multiple jumps in an action.
 * It holds a sequence of the rows and columns of the piece
 * that is to be moved, for example:
 * a single move: (2, 0) -> (3, 1)
 * a sequnce of jumps: (2, 0) -> (4, 2) -> (6, 0)
 *
 */
public class CheckersMove {
    
    ArrayList<Integer> rows = new ArrayList<Integer>();
    ArrayList<Integer> cols = new ArrayList<Integer>();
    
    CheckersMove(int r1, int c1, int r2, int c2) {
        // Constructor, a single move from
        //(r1, c1) to (r2, c2)
        
        // move's start
        rows.add(r1);
        cols.add(c1);
        
        // move's destination
        rows.add(r2);
        cols.add(c2);
    }
    
    CheckersMove() {
        // Constructor, create an empty move
    }

    int getLastCol() {
        return cols.get(cols.size() - 1);
    }

    int getLastRow() {
        return rows.get(rows.size() - 1);
    }
    
    boolean isJump() {
        return (rows.get(0) - rows.get(1) == 2 || rows.get(0) - rows.get(1) == -2);
    }
    
    void addMove(int r, int c){
        // add another move (continuous jump), which goes from
        // (last ele in rows, last ele in cols) to (r, c)
        rows.add(r);
        cols.add(c);
    }
    
    //get a copy of this move
    @Override
    public CheckersMove clone() {
        CheckersMove move = new CheckersMove();
        
        move.rows.addAll(this.rows);
        move.cols.addAll(this.cols);
        
        return move;
        
    }

    //get a copy of this move
    @Override
    public String toString() {
        return getCoors();
    }

    public String getCoors() {
        String output = "\nMove:\n";
        for (int i = 0; i < rows.size(); i++) {
            output += "    (" + rows.get(i) + ", " + cols.get(i) + ")";
            output += "\n";
        }
        return output;
    }

}  // end class CheckersMove.
