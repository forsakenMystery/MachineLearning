/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearning;

import utility.Entity;
import utility.Pair;
import java.util.ArrayList;

/**
 *
 * @author Hamed Khashehchi
 */
public abstract class Player implements moveable {

    private Entity color;
    private String name;
    private ArrayList<Pair> possible;

    public void setPossible(ArrayList<Pair> possible) {
        this.possible = possible;
    }

    public ArrayList<Pair> getPossible() {
        return possible;
    }

    public Player(Entity color, String name) {
        this.color = color;
        this.name = name;
    }

    public boolean isMyTurn(Entity turn) {
        return turn.equals(color);
    }

    public Entity getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Entity getOponentColor() {
        if (color.equals(Entity.RED)) {
            return Entity.BLACK;
        } else if (color.equals(Entity.BLACK)) {
            return Entity.RED;
        }
        return null;
    }

}
