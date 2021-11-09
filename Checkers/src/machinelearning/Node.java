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
public class Node {

    private ArrayList<Node> decendent;
    private Cell[][] cells;
    private Entity color;
    private boolean minimax;
    private double eval;

    public Node(Cell[][] cells, Entity turn, boolean min) {
        this.decendent = new ArrayList<>();
        this.cells = cells;
        this.minimax = min;
        this.color = turn;
    }

    public Node() {
        this.decendent = new ArrayList<>();
    }

    public void addChild(Node n) {
        decendent.add(n);
    }

    public void setEval(double eval) {
        this.eval = eval;
    }

    public ArrayList<Node> getChildren() {
        return decendent;
    }

    public double getEval() {
        return eval;
    }

    public boolean isMinimax() {
        return minimax;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Entity getColor() {
        return color;
    }

    @Override
    public String toString() {
        return new State(cells, color).toString() + "\neval= " + this.eval; //To change body of generated methods, choose Tools | Templates.
    }

}
