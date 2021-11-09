/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearning;

import utility.Entity;
import utility.Pair;
import utility.Point;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hamed Khashehchi
 */
public class Human extends Player{

    public Human( String name, Entity Color) {
        super(Color, name);
    }

    public void setPossible(ArrayList<Pair> possible) {
        super.setPossible(possible);
    }

    public Pair move() {
        if (super.getPossible().size() != 0) {
            Scanner input = new Scanner(System.in);
            int fromRow, fromCollumn, toRow, toCollumn;
            boolean correct = false;
            Pair move = null;
            while (!correct) {
                System.out.println("choose your piece(row then collumn):");
                fromRow = input.nextInt();
                fromCollumn = input.nextInt();

                System.out.println("choose your play(row then collumn):");
                toRow = input.nextInt();
                toCollumn = input.nextInt();

                for (Pair pair : super.getPossible()) {
                    if (pair.getFrom().getPoint().equals(new Point(fromRow - 1, fromCollumn - 1)) && pair.getTo().getPoint().equals(new Point(toRow - 1, toCollumn - 1))) {
                        correct = true;
                        move = pair;
                    }
                }
                if(!correct){
                    System.out.println("Invalid Move!");
                }
            }
            return move;
        } else {
            return null;
        }
    }
}
