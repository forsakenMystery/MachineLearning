/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author Hamed Khashehchi
 */
public class Point {
    private int x;
    private int y;
    private int row;
    private int collumn;

    @Override
    public String toString() {
        return "([x, y] = ["+this.x+", "+this.y+"], in row = "+this.row+", in column = "+this.collumn+")"; //To change body of generated methods, choose Tools | Templates.
    }

    public Point(int x, int y, int row, int collumn) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.collumn = collumn;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.row = x+1;
        this.collumn = y+1;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.collumn != other.collumn) {
            return false;
        }
        return true;
    }

    private Point() {
    }
    
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the collumn
     */
    public int getCollumn() {
        return collumn;
    }

    /**
     * @param collumn the collumn to set
     */
    public void setCollumn(int collumn) {
        this.collumn = collumn;
    }
    
}
