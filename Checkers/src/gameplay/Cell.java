/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import utility.Entity;
import utility.Point;

/**
 *
 * @author Hamed Khashehchi
 */
public class Cell {

    private Point point;
    private Entity occupied;
    private Entity pieceColor;

    /**
     * @return the point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    public void setPoint(int x, int y) {
        this.point = new Point(x, y);
    }

    /**
     * @return the occupied
     */
    public Entity getOccupied() {
        return occupied;
    }

    /**
     * @param occupied the occupied to set
     */
    public void setOccupied(Entity occupied) {
        this.occupied = occupied;
    }

    /**
     * @return the pieceColor
     */
    public Entity getPieceColor() {
        return pieceColor;
    }

    /**
     * @param pieceColor the pieceColor to set
     */
    public void setPieceColor(Entity pieceColor) {
        this.pieceColor = pieceColor;
    }

    public String debug(){
        String print="(";
        print += this.point+", "+this.occupied+", "+this.pieceColor+")";
        return print;
    }
    @Override
    public String toString() {
        String print = "";
//        System.out.println("this.occupied = " + this.occupied);
        if (occupied.equals(Entity.EMPTY)) {
            print += "0";
        } else if (occupied.equals(Entity.SOLDIER)) {
            if (pieceColor.equals(Entity.RED)) {
                print += "r";

            } else if (pieceColor.equals(Entity.BLACK)) {
                print += "b";

            }
        } else if (occupied.equals(Entity.KING)) {
            if (pieceColor.equals(Entity.RED)) {
                print += "1";

            } else if (pieceColor.equals(Entity.BLACK)) {
                print += "2";
            }
        }

        return print; //To change body of generated methods, choose Tools | Templates.
    }

}
