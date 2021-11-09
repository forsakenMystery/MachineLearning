/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import gameplay.Cell;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Hamed Khashehchi
 */
public class Pair {

    private Cell from;
    private Cell to;
    private boolean attack;
    private Cell dead;
    
    public Pair(Cell from, Cell to) {
        this.from = from;
        this.to = to;
        this.dead = null;
        this.attack = false;
    }

    public Pair(Cell from, Cell to, boolean attack, Cell dead) {
        this.from = from;
        this.to = to;
        this.attack = attack;
        this.dead = dead;
    }

    public boolean isAttack() {
        return attack;
    }

    public Cell getDead() {
        return dead;
    }

    public Cell getFrom() {
        return from;
    }

    public Cell getTo() {
        return to;
    }

    @Override
    public String toString() {
        String print = "{" + from.debug() + "-->" + to.debug() + "}";
        return print; //To change body of generated methods, choose Tools | Templates.
    }

}
