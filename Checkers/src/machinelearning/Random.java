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
public class Random extends Player{
    private final static String[] names={"Hamed", "Ali", "Amoo Hassan", "Codtiger", "Forsaken Mystery", "Hax Lord", "Mystery", "Doctor", "Mamali", "Ghasem"};
    
    public Random(Entity color) {
        super(color, names[(int)(Math.floor(Math.random()*names.length))]);
    }

    public void setPossible(ArrayList<Pair> possible) {
        super.setPossible(possible);
    }
    
    
    
    public Pair move(){
        if(super.getPossible().size()!=0){
            return super.getPossible().get((int)(Math.floor(Math.random()*super.getPossible().size())));
        }else{
            return null;
        }
    }
    
}
