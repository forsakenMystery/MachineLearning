/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearning;

import gameplay.Cell;
import utility.Entity;
import java.util.ArrayList;

/**
 *
 * @author Hamed Khashehchi
 */
public class State {

    private Cell[][] cells;
    private Entity turn;

    public State(Cell[][] cells, Entity turn) {
        this.cells = cells;
        this.turn = turn;
    }

    /**
     * @return the cells
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * @param cells the cells to set
     */
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * @return the turn
     */
    public Entity getTurn() {
        return turn;
    }

    /**
     * @param turn the turn to set
     */
    public void setTurn(Entity turn) {
        this.turn = turn;
    }

    
    @Override
    public String toString() {
        String print = "\n&&&&&&&&&&&&&&&&&&&&&&\nIt was "+this.turn+" call:\n++++++++++++++++++\n++++++++++++++++++\n\n =================\n";
        print += "   1 2 3 4 5 6 7 8\n _________________\n";
        for (int i = 0; i < cells.length; i++) {
            print += (i + 1) + "| ";
            for (int j = 0; j < cells[i].length; j++) {
                print += cells[i][j]+" ";
            }
            print += "\n";
        }
        print+="&&&&&&&&&&&&&&&&&&&&&&";
        return print; //To change body of generated methods, choose Tools | Templates.
    }
    
}
