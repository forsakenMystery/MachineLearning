/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import utility.Pair;
import utility.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Hamed Khashehchi
 */
public class Checkers {

    private Board board = new Board();
    private Entity turnColor;
    private Entity Winner;
    private int peace;
    private boolean end;

    public Checkers() {
        turnColor = Entity.RED;
        Winner = null;
        peace = 0;
        end = false;
    }
    
    public void reset(){
        board = new Board();
        turnColor = Entity.RED;
        Winner = null;
        peace = 0;
        end = false;
    }

    public boolean isEnd() {
        return end;
    }

    public Board getBoard() {
        return board;
    }

    private boolean isValid(int i) {
        return i < 8 && i >= 0;
    }

    private ArrayList<Cell> turnPieces() {
        ArrayList<Cell> turn = new ArrayList<>();
        Cell[][] cells = getBoard().getBoard();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.getOccupied().equals(Entity.EMPTY)) {
                    if (cell1.getPieceColor().equals(turnColor)) {
                        turn.add(cell1);
//                        System.out.println(cell1.debug());
                    }
                }
            }
        }
        return turn;
    }

    private ArrayList<Cell> oppositeTurnPieces() {
        changeTurn();
        ArrayList<Cell> oppositeTurn = new ArrayList<>();
        Cell[][] cells = getBoard().getBoard();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.getOccupied().equals(Entity.EMPTY)) {
                    if (cell1.getPieceColor().equals(turnColor)) {
                        oppositeTurn.add(cell1);
//                        System.out.println(cell1.debug());
                    }
                }
            }
        }
        changeTurn();
        return oppositeTurn;
    }

    public ArrayList<Pair> possibleMovesRed(Cell[][] cells) {
        ArrayList<Pair> possible = new ArrayList<>();
        ArrayList<Cell> Red = turnPieces();
        for (Cell cell : Red) {
            if (cell.getOccupied().equals(Entity.SOLDIER)) {
                if (isValid(cell.getPoint().getX() + 1)) {
                    if (isValid(cell.getPoint().getY() - 1)) {
//                        System.out.println(cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].debug());
                        if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1]));
                        } else if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].getPieceColor().equals(Entity.BLACK)) {
                            if (isValid(cell.getPoint().getY() - 2) && isValid(cell.getPoint().getX() + 2)) {
                                if (cells[cell.getPoint().getX() + 2][cell.getPoint().getY() - 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() + 2][cell.getPoint().getY() - 2], true, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1]));
                                }
                            }
                        }
                    }
                    if (isValid(cell.getPoint().getY() + 1)) {
//                        System.out.println(cell.debug());
                        if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1]));
                        } else if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1].getPieceColor().equals(Entity.BLACK)) {
                            if (isValid(cell.getPoint().getY() + 2) && isValid(cell.getPoint().getX() + 2)) {
                                if (cells[cell.getPoint().getX() + 2][cell.getPoint().getY() + 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() + 2][cell.getPoint().getY() + 2], true, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1]));
                                }
                            }
                        }
                    }
                }
            } else if (cell.getOccupied().equals(Entity.KING)) {
                if (isValid(cell.getPoint().getX() + 1)) {
                    if (isValid(cell.getPoint().getY() - 1)) {
//                        System.out.println(cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].debug());
                        if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1]));
                        } else if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].getPieceColor().equals(Entity.BLACK)) {
                            if (isValid(cell.getPoint().getY() - 2) && isValid(cell.getPoint().getX() + 2)) {
                                if (cells[cell.getPoint().getX() + 2][cell.getPoint().getY() - 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() + 2][cell.getPoint().getY() - 2], true, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1]));
                                }
                            }
                        }
                    }
                    if (isValid(cell.getPoint().getY() + 1)) {
//                        System.out.println(cell.debug());
                        if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1]));
                        } else if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1].getPieceColor().equals(Entity.BLACK)) {
                            if (isValid(cell.getPoint().getY() + 2) && isValid(cell.getPoint().getX() + 2)) {
                                if (cells[cell.getPoint().getX() + 2][cell.getPoint().getY() + 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() + 2][cell.getPoint().getY() + 2], true, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1]));
                                }
                            }
                        }
                    }
                }
                if (isValid(cell.getPoint().getX() - 1)) {
                    if (isValid(cell.getPoint().getY() - 1)) {
//                        System.out.println(cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].debug());
                        if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1]));
                        } else if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1].getPieceColor().equals(Entity.BLACK)) {
                            if (isValid(cell.getPoint().getY() - 2) && isValid(cell.getPoint().getX() - 2)) {
                                if (cells[cell.getPoint().getX() - 2][cell.getPoint().getY() - 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() - 2][cell.getPoint().getY() - 2], true, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1]));
                                }
                            }
                        }
                    }
                    if (isValid(cell.getPoint().getY() + 1)) {
                        if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1]));
                        } else if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1].getPieceColor().equals(Entity.BLACK)) {
                            if (isValid(cell.getPoint().getY() + 2) && isValid(cell.getPoint().getX() - 2)) {
                                if (cells[cell.getPoint().getX() - 2][cell.getPoint().getY() + 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() - 2][cell.getPoint().getY() + 2], true, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1]));
                                }
                            }
                        }
                    }
                }
            }
        }
//        System.out.println("possible = " + possible);
//        System.out.println(possible.size());
        return possible;
    }

    protected int count_turn() {
        return turnPieces().size();
    }

    protected int count_opposite() {
        return oppositeTurnPieces().size();
    }

    public ArrayList<Pair> possibleMoves() {
        ArrayList<Pair> possible;
        if (turnColor.equals(Entity.BLACK)) {
            possible = possibleMovesBlack(this.getBoard().getBoard());
        } else if (turnColor.equals(Entity.RED)) {
            possible = possibleMovesRed(this.getBoard().getBoard());
        } else {
            possible = null;
        }
        return possible;
    }

    
    public ArrayList<Pair> possibleMovesOpponent() {
        ArrayList<Pair> possible;
        if (turnColor.equals(Entity.BLACK)) {
            possible = possibleMovesRed(this.getBoard().getBoard());
        } else if (turnColor.equals(Entity.RED)) {
            possible = possibleMovesBlack(this.getBoard().getBoard());
        } else {
            possible = null;
        }
        return possible;
    }

    private void changeTurn() {
        if (turnColor.equals(Entity.BLACK)) {
            turnColor = Entity.RED;
        } else if (turnColor.equals(Entity.RED)) {
            turnColor = Entity.BLACK;
        }
    }

    public Entity getTurnColor() {
        return turnColor;
    }

    private void play(int fromRow, int fromColumn, int toRow, int toColumn) {
        System.out.println("start playing:");
        System.out.println("++++++++++++++++++\n++++++++++++++++++");
        System.out.println("Turn for " + turnColor + ":");
        ArrayList<Pair> possibleMoves = possibleMoves();
        boolean done = false;
        if (!possibleMoves.isEmpty()) {
            for (Pair possibleMove : possibleMoves) {
//                System.out.println("possibleMove = " + possibleMove);
                Cell from = possibleMove.getFrom();
                Cell to = possibleMove.getTo();
                if (fromRow == from.getPoint().getRow() && fromColumn == from.getPoint().getCollumn() && toRow == to.getPoint().getRow() && toColumn == to.getPoint().getCollumn()) {
                    done = true;
                    board.getBoard()[to.getPoint().getX()][to.getPoint().getY()].setOccupied(board.getBoard()[from.getPoint().getX()][from.getPoint().getY()].getOccupied());
                    if (to.getPoint().getRow() == 1 || to.getPoint().getRow() == 8) {
                        board.getBoard()[to.getPoint().getX()][to.getPoint().getY()].setOccupied(Entity.KING);
                    }
                    board.getBoard()[to.getPoint().getX()][to.getPoint().getY()].setPieceColor(board.getBoard()[from.getPoint().getX()][from.getPoint().getY()].getPieceColor());
                    board.getBoard()[from.getPoint().getX()][from.getPoint().getY()].setOccupied(Entity.EMPTY);
                    board.getBoard()[from.getPoint().getX()][from.getPoint().getY()].setPieceColor(Entity.NONE);
                    if (possibleMove.isAttack()) {
                        board.getBoard()[possibleMove.getDead().getPoint().getX()][possibleMove.getDead().getPoint().getY()].setOccupied(Entity.EMPTY);
                        board.getBoard()[possibleMove.getDead().getPoint().getX()][possibleMove.getDead().getPoint().getY()].setPieceColor(Entity.NONE);
                        peace = 0;
                    } else {
                        peace++;
                    }
                    break;
                }
            }
        } else {
            if (turnColor.equals(Entity.RED)) {
                Winner = Entity.BLACK;
            } else if (turnColor.equals(Entity.BLACK)) {
                Winner = Entity.RED;
            }
            end = true;
        }
//        System.out.println("count_opposite() = " + count_opposite());
        if (count_opposite() == 0) {
            end = true;
            Winner = turnColor;
        }
//        System.out.println(getBoard());
        if (done) {
            changeTurn();
        } else {
            System.out.println("invalid move!");
        }
        if (peace >= 500) {
            end = true;
        }
    }

    public ArrayList<Pair> possibleMovesBlack(Cell[][] cells) {
        ArrayList<Pair> possible = new ArrayList<>();
        ArrayList<Cell> Black = turnPieces();
        for (Cell cell : Black) {
            if (cell.getOccupied().equals(Entity.SOLDIER)) {
                if (isValid(cell.getPoint().getX() - 1)) {
                    if (isValid(cell.getPoint().getY() - 1)) {
//                        System.out.println(cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].debug());
                        if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1]));
                        } else if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1].getPieceColor().equals(Entity.RED)) {
                            if (isValid(cell.getPoint().getY() - 2) && isValid(cell.getPoint().getX() - 2)) {
                                if (cells[cell.getPoint().getX() - 2][cell.getPoint().getY() - 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() - 2][cell.getPoint().getY() - 2], true, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1]));
                                }
                            }
                        }
                    }
                    if (isValid(cell.getPoint().getY() + 1)) {
//                        System.out.println(cell.debug());
                        if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1]));
                        } else if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1].getPieceColor().equals(Entity.RED)) {
                            if (isValid(cell.getPoint().getY() + 2) && isValid(cell.getPoint().getX() - 2)) {
                                if (cells[cell.getPoint().getX() - 2][cell.getPoint().getY() + 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() - 2][cell.getPoint().getY() + 2], true, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1]));
                                }
                            }
                        }
                    }
                }
            } else if (cell.getOccupied().equals(Entity.KING)) {
                if (isValid(cell.getPoint().getX() + 1)) {
                    if (isValid(cell.getPoint().getY() - 1)) {
//                        System.out.println(cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].debug());
                        if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1]));
                        } else if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].getPieceColor().equals(Entity.RED)) {
                            if (isValid(cell.getPoint().getY() - 2) && isValid(cell.getPoint().getX() + 2)) {
                                if (cells[cell.getPoint().getX() + 2][cell.getPoint().getY() - 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() + 2][cell.getPoint().getY() - 2], true, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1]));
                                }
                            }
                        }
                    }
                    if (isValid(cell.getPoint().getY() + 1)) {
//                        System.out.println(cell.debug());
                        if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1]));
                        } else if (cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1].getPieceColor().equals(Entity.RED)) {
                            if (isValid(cell.getPoint().getY() + 2) && isValid(cell.getPoint().getX() + 2)) {
                                if (cells[cell.getPoint().getX() + 2][cell.getPoint().getY() + 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() + 2][cell.getPoint().getY() + 2], true, cells[cell.getPoint().getX() + 1][cell.getPoint().getY() + 1]));
                                }
                            }
                        }
                    }
                }
                if (isValid(cell.getPoint().getX() - 1)) {
                    if (isValid(cell.getPoint().getY() - 1)) {
//                        System.out.println(cells[cell.getPoint().getX() + 1][cell.getPoint().getY() - 1].debug());
                        if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1]));
                        } else if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1].getPieceColor().equals(Entity.RED)) {
                            if (isValid(cell.getPoint().getY() - 2) && isValid(cell.getPoint().getX() - 2)) {
                                if (cells[cell.getPoint().getX() - 2][cell.getPoint().getY() - 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() - 2][cell.getPoint().getY() - 2], true, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() - 1]));
                                }
                            }
                        }
                    }
                    if (isValid(cell.getPoint().getY() + 1)) {
                        if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1].getOccupied().equals(Entity.EMPTY)) {
                            possible.add(new Pair(cell, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1]));
                        } else if (cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1].getPieceColor().equals(Entity.RED)) {
                            if (isValid(cell.getPoint().getY() + 2) && isValid(cell.getPoint().getX() - 2)) {
                                if (cells[cell.getPoint().getX() - 2][cell.getPoint().getY() + 2].getOccupied().equals(Entity.EMPTY)) {
                                    possible.add(new Pair(cell, cells[cell.getPoint().getX() - 2][cell.getPoint().getY() + 2], true, cells[cell.getPoint().getX() - 1][cell.getPoint().getY() + 1]));
                                }
                            }
                        }
                    }
                }
            }
        }
//        System.out.println("possible = " + possible);
//        System.out.println(possible.size());
        return possible;
    }

    public int getPeace() {
        return peace;
    }

    public Entity getWinner() {
        return Winner;
    }

    public void play(Pair move) {
        if (move != null) {
//            System.out.println("move = " + move);
            this.play(move.getFrom().getPoint().getRow(), move.getFrom().getPoint().getCollumn(), move.getTo().getPoint().getRow(), move.getTo().getPoint().getCollumn());
        } else {
            end = true;
        }
        System.out.println("playing ended!");
        System.out.println("===================\n");
    }
    
    public void showBoard(AnchorPane board, int SIZEPIECE, int MARGINLEFT, int MARGINTOP) {
        board.getChildren().removeAll(board.getChildren());
        Cell[][] board1 = this.getBoard().getBoard();
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                if ((board1[i][j].getPoint().getRow() + board1[i][j].getPoint().getCollumn()) % 2 == 0) {
                    Rectangle r = new Rectangle(SIZEPIECE, SIZEPIECE, Paint.valueOf("RED"));
                    r.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
                    r.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
                    board.getChildren().add(r);
                } else {
                    Rectangle r = new Rectangle(SIZEPIECE, SIZEPIECE, Paint.valueOf("WHITE"));
                    r.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
                    r.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
                    board.getChildren().add(r);
                }

                if (board1[i][j].getPieceColor().equals(Entity.RED)) {
                    ImageView v = null;
                    if (board1[i][j].getOccupied().equals(Entity.SOLDIER)) {
                        v = new ImageView(new Image("white.png"));
                    } else if (board1[i][j].getOccupied().equals(Entity.KING)) {
                        v = new ImageView(new Image("whiteking.png"));
                    }
                    v.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
                    v.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
                    v.setFitWidth(SIZEPIECE);
                    v.setFitHeight(SIZEPIECE);
                    board.getChildren().add(v);
                } else if (board1[i][j].getPieceColor().equals(Entity.BLACK)) {
                    ImageView v = null;
                    if (board1[i][j].getOccupied().equals(Entity.SOLDIER)) {
                        v = new ImageView(new Image("black.png"));
                    } else if (board1[i][j].getOccupied().equals(Entity.KING)) {
                        v = new ImageView(new Image("blackking.png"));
                    }
                    v.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
                    v.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
                    v.setFitWidth(SIZEPIECE);
                    v.setFitHeight(SIZEPIECE);
                    board.getChildren().add(v);
                }
            }
        }
    }
}
