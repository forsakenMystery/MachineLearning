/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import utility.Entity;

/**
 *
 * @author Hamed Khashehchi
 */
public class Board {

    private Cell[][] board = new Cell[8][8];

    public Cell[][] getBoard() {
        return board;
    }

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell();
                board[i][j].setPoint(i, j);
                board[i][j].setOccupied(Entity.EMPTY);
                board[i][j].setPieceColor(Entity.NONE);
                if(i==0||i==2){
                    if(j%2==0){
                        board[i][j].setOccupied(Entity.SOLDIER);
                        board[i][j].setPieceColor(Entity.RED);
                    }
                }
                else if(i==1){
                    if(j%2==1){
                        board[i][j].setOccupied(Entity.SOLDIER);
                        board[i][j].setPieceColor(Entity.RED);
                    }
                }
                else if(i==5||i==7){
                    if(j%2==1){
                        board[i][j].setOccupied(Entity.SOLDIER);
                        board[i][j].setPieceColor(Entity.BLACK);
                    }
                }
                else if(i==6){
                    if(j%2==0){
                        board[i][j].setOccupied(Entity.SOLDIER);
                        board[i][j].setPieceColor(Entity.BLACK);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        String print = "++++++++++++++++++\n++++++++++++++++++\n\n =================\n";
        print += "   1 2 3 4 5 6 7 8\n _________________\n";
        for (int i = 0; i < board.length; i++) {
            print += (i + 1) + "| ";
            for (int j = 0; j < board[i].length; j++) {
                print += board[i][j]+" ";
            }
            print += "\n";
        }
        return print; //To change body of generated methods, choose Tools | Templates.
    }

}
