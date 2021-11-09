/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearning;

import gameplay.Board;
import gameplay.Cell;
import utility.Entity;
import utility.Pair;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Hamed Khashehchi
 */
public class Agent extends Player {

    private double previous = Double.MIN_VALUE;
    private String[] choices = {"bias", "oponent_soldiers", "my_soldiers", "oponent_kings", "my_kings", "oponent in attack", "me in attack"};
    private Board board;
    private int time = 0;
    private ArrayList<Pair> possibleOpponent;
    private ArrayList<Double> weights;
    private ArrayList<State> states;
    private Entity Enemy;
    private boolean learning;
    private double eta = 0.002;
    private boolean starting = true;

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public Agent(Entity color, Board board, boolean learning, ArrayList<Double> weights) {
        super(color, "Artificial Intelligence" + (int) (Math.floor(Math.random() * 10 + 1)));
        this.board = board;
        this.weights = weights;
        states = new ArrayList<>();
        if (super.getColor().equals(Entity.BLACK)) {
            this.Enemy = Entity.RED;
        } else if (super.getColor().equals(Entity.RED)) {
            this.Enemy = Entity.BLACK;
        }
        states.add(new State(this.board.getBoard(), color));
        this.learning = learning;
    }

    private double valueFunction(ArrayList<Double> weights, ArrayList<Integer> values) {
        double value = 0.0;
        for (int i = 0; i < values.size(); i++) {
            value += weights.get(i) * values.get(i);
        }
        return value;
    }

    public void setPossible(ArrayList<Pair> possible) {
        super.setPossible(possible);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setOpponentPossible(ArrayList<Pair> possible) {
        this.possibleOpponent = possible;
    }

    private int countOponent(Cell[][] cells) {
        ArrayList<Cell> turn = new ArrayList<>();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.getOccupied().equals(Entity.EMPTY)) {
                    if (cell1.getPieceColor().equals(super.getOponentColor()) && cell1.getOccupied().equals(Entity.SOLDIER)) {
                        turn.add(cell1);
//                        System.out.println(cell1.debug());
                    }
                }
            }
        }
        return turn.size();
    }

    private int countMe(Cell[][] cells) {
        ArrayList<Cell> turn = new ArrayList<>();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.getOccupied().equals(Entity.EMPTY)) {
                    if (cell1.getPieceColor().equals(super.getColor()) && cell1.getOccupied().equals(Entity.SOLDIER)) {
                        turn.add(cell1);
//                        System.out.println(cell1.debug());
                    }
                }
            }
        }
        return turn.size();
    }

    public void setState(State n) {
        states.add(n);
        System.out.println(super.getName());
        System.out.println(states.get(states.size() - 1));
        System.out.println("\n\n\nall states:\n");
        System.out.println("states = \n" + states);
        System.out.println("\n\nend of all states\n\n\n");
        if (learning) {
            System.out.println("It's training time:");
        } else {
            System.out.println("It's game time:");
        }
    }

    private int countOponentKings(Cell[][] cells) {
        ArrayList<Cell> turn = new ArrayList<>();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.getOccupied().equals(Entity.EMPTY)) {
                    if (cell1.getPieceColor().equals(super.getOponentColor()) && cell1.getOccupied().equals(Entity.KING)) {
                        turn.add(cell1);
//                        System.out.println(cell1.debug());
                    }
                }
            }
        }
        return turn.size();
    }

    private int countMyKings(Cell[][] cells) {

        ArrayList<Cell> turn = new ArrayList<>();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.getOccupied().equals(Entity.EMPTY)) {
                    if (cell1.getPieceColor().equals(super.getColor()) && cell1.getOccupied().equals(Entity.KING)) {
                        turn.add(cell1);
//                        System.out.println(cell1.debug());
                    }
                }
            }
        }
        return turn.size();
    }

    private int countMeInDanger(Cell[][] cells) {
        int count = 0;
        if (super.getColor().equals(Entity.RED)) {
            this.possibleOpponent = possibleMovesBlack(cells);
        } else if (super.getColor().equals(Entity.BLACK)) {
            this.possibleOpponent = possibleMovesRed(cells);
        }
        count = possibleOpponent.stream().filter((p) -> (p.isAttack())).map((_item) -> 1).reduce(count, Integer::sum);
        return count;
    }

    public ArrayList<Pair> possibleMovesRed(Cell[][] cells) {
        ArrayList<Pair> possible = new ArrayList<>();
        ArrayList<Cell> Red = turnPieces(cells, Entity.RED);
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
        return possible;
    }

    private ArrayList<Cell> turnPieces(Cell[][] cells, Entity c) {
        ArrayList<Cell> turn = new ArrayList<>();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.getOccupied().equals(Entity.EMPTY)) {
                    if (cell1.getPieceColor().equals(c)) {
                        turn.add(cell1);
//                        System.out.println(cell1.debug());
                    }
                }
            }
        }
        return turn;
    }

    public ArrayList<Pair> possibleMovesBlack(Cell[][] cells) {
        ArrayList<Pair> possible = new ArrayList<>();
        ArrayList<Cell> Black = turnPieces(cells, Entity.BLACK);
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
        return possible;
    }

    private boolean isValid(int i) {
        return i < 8 && i >= 0;
    }

    private int countOponentInDanger(Cell[][] cells) {
        int count = 0;
        if (super.getColor().equals(Entity.BLACK)) {
            super.setPossible(possibleMovesBlack(cells));
        } else if (super.getColor().equals(Entity.RED)) {
            super.setPossible(possibleMovesRed(cells));
        }
        count = super.getPossible().stream().filter((p) -> (p.isAttack())).map((_item) -> 1).reduce(count, Integer::sum);
        return count;
    }

    private double Mov(Cell[][] board, Pair mov) {
        Cell from = mov.getFrom();
        Cell to = mov.getTo();
        board[to.getPoint().getX()][to.getPoint().getY()].setOccupied(board[from.getPoint().getX()][from.getPoint().getY()].getOccupied());
        if (to.getPoint().getRow() == 1 || to.getPoint().getRow() == 8) {
            board[to.getPoint().getX()][to.getPoint().getY()].setOccupied(Entity.KING);
        }
        board[to.getPoint().getX()][to.getPoint().getY()].setPieceColor(board[from.getPoint().getX()][from.getPoint().getY()].getPieceColor());
        board[from.getPoint().getX()][from.getPoint().getY()].setOccupied(Entity.EMPTY);
        board[from.getPoint().getX()][from.getPoint().getY()].setPieceColor(Entity.NONE);
        if (mov.isAttack()) {
            board[mov.getDead().getPoint().getX()][mov.getDead().getPoint().getY()].setOccupied(Entity.EMPTY);
            board[mov.getDead().getPoint().getX()][mov.getDead().getPoint().getY()].setPieceColor(Entity.NONE);
        }
        ArrayList<Integer> value = new ArrayList<>();
        value.add(1);
        value.add(countOponent(board));
        value.add(countMe(board));
        value.add(countOponentKings(board));
        value.add(countMyKings(board));
        value.add(countMeInDanger(board));
        value.add(countOponentInDanger(board));
        double valueFunction = valueFunction(weights, value);
//        System.out.println(new State(board, super.getColor()));
        System.out.println("valueFunction = " + valueFunction);
        System.out.println("choices = " + Arrays.toString(choices));
        System.out.println("value = " + value);
        return valueFunction;

    }

    private Cell[][] MO(Cell[][] board, Pair mov) {
        Cell from = mov.getFrom();
        Cell to = mov.getTo();
        board[to.getPoint().getX()][to.getPoint().getY()].setOccupied(board[from.getPoint().getX()][from.getPoint().getY()].getOccupied());
        if (to.getPoint().getRow() == 1 || to.getPoint().getRow() == 8) {
            board[to.getPoint().getX()][to.getPoint().getY()].setOccupied(Entity.KING);
        }
        board[to.getPoint().getX()][to.getPoint().getY()].setPieceColor(board[from.getPoint().getX()][from.getPoint().getY()].getPieceColor());
        board[from.getPoint().getX()][from.getPoint().getY()].setOccupied(Entity.EMPTY);
        board[from.getPoint().getX()][from.getPoint().getY()].setPieceColor(Entity.NONE);
        if (mov.isAttack()) {
            board[mov.getDead().getPoint().getX()][mov.getDead().getPoint().getY()].setOccupied(Entity.EMPTY);
            board[mov.getDead().getPoint().getX()][mov.getDead().getPoint().getY()].setPieceColor(Entity.NONE);
        }
        ArrayList<Integer> value = new ArrayList<>();
        value.add(1);
        value.add(countOponent(board));
        value.add(countMe(board));
        value.add(countOponentKings(board));
        value.add(countMyKings(board));
        double valueFunction = valueFunction(weights, value);
//        System.out.println(new State(board, super.getColor()));
        System.out.println("valueFunction = " + valueFunction);
        System.out.println("choices = " + Arrays.toString(choices));
        System.out.println("value = " + value);
        return board;

    }

    @Override
    public Pair move() {
        if (super.getPossible().size() != 0) {
//            System.out.println("minimax!!!");
//            Tree minimax = new Tree();
//            Node first = new Node(this.board.getBoard(), super.getColor(), true);//true means max
//            ArrayList<Integer> valuess = new ArrayList<>();
//            valuess.add(1);
//            valuess.add(countOponent(this.board.getBoard()));
//            valuess.add(countMe(this.board.getBoard()));
//            valuess.add(countOponentKings(this.board.getBoard()));
//            valuess.add(countMyKings(this.board.getBoard()));
//
//            value.add(countMeInDanger(board));
//            value.add(countOponentInDanger(board));
//            double valu = valueFunction(weights, valuess);
//            first.setEval(valu);
//            minimax.getRoot().addChild(first);
//            System.out.println("start of move:");
//            ArrayList<Pair> psbl = null;
//            int layer = 3;
//            Node root = minimax.getRoot();
//            Node nn=root;
//            while (layer-- != 0) {
//                System.out.println("layer = " + layer);
//                for (int l = 0; l < nn.getChildren().size(); l++) {
//                    Node ss = nn.getChildren().get(l);
//                    System.out.println("ss = " + ss);
//                    Cell[][] temp = ss.getCells();
//                    if (ss.getColor().equals(Entity.BLACK)) {
//                        psbl = this.possibleMovesBlack(temp);
//                    } else if (ss.getColor().equals(Entity.RED)) {
//                        psbl = this.possibleMovesRed(temp);
//                    }
//                    for (int k = 0; k < psbl.size(); k++) {
//                        Cell[][] a = new Cell[temp.length][temp[0].length];
//                        for (int i = 0; i < a.length; i++) {
//                            for (int j = 0; j < a[0].length; j++) {
//                                a[i][j] = new Cell();
//                                a[i][j].setOccupied(temp[i][j].getOccupied());
//                                a[i][j].setPieceColor(temp[i][j].getPieceColor());
//                                a[i][j].setPoint(temp[i][j].getPoint());
//                            }
//                        }
//                        Pair get = psbl.get(k);
//                        double Mov = Mov(a, get);
//                        Cell[][] MO = MO(a, get);
//                        Node m = new Node(MO, Enemy, false);
//                        m.setEval(Mov);
//                        first.addChild(m);
//                    }
//                }
//            }

            time++;
            if (time % 11 != 0) {
                if (!starting && learning) {
                    System.out.println("");
                    System.out.println("learning on:");
                    ArrayList<Integer> value = new ArrayList<>();
                    value.add(1);
                    value.add(countOponent(this.board.getBoard()));
                    value.add(countMe(this.board.getBoard()));
                    value.add(countOponentKings(this.board.getBoard()));
                    value.add(countMyKings(this.board.getBoard()));
                    value.add(countMeInDanger(this.board.getBoard()));
                    value.add(countOponentInDanger(this.board.getBoard()));
                    double valueFunction = valueFunction(weights, value);
                    System.out.println("valve = " + valueFunction);
                    double error = valueFunction - previous;
                    System.out.println("weights = " + weights);
                    System.out.println("value = " + value);
                    System.out.println("error = " + error);
                    for (int o = 0; o < weights.size() - 1; o++) {
                        weights.set(o, weights.get(o) + eta * value.get(o) * error);
                    }
                    System.out.println("choices = " + Arrays.toString(choices));
                    System.out.println("weights = " + weights);
                    System.out.println("end learning");
                    System.out.println("");
                }
                starting = false;
                System.out.println("previous = " + previous);
                double max = Double.MIN_VALUE;
                int choice = 0;
                System.out.println("start of move:");
                ArrayList<Pair> possible = super.getPossible();
                for (int k = 0; k < possible.size(); k++) {
                    Cell[][] a = new Cell[board.getBoard().length][board.getBoard()[0].length];
                    for (int i = 0; i < a.length; i++) {
                        for (int j = 0; j < a[0].length; j++) {
                            a[i][j] = new Cell();
                            a[i][j].setOccupied(board.getBoard()[i][j].getOccupied());
                            a[i][j].setPieceColor(board.getBoard()[i][j].getPieceColor());
                            a[i][j].setPoint(board.getBoard()[i][j].getPoint());
                        }
                    }
                    Pair get = possible.get(k);
                    double Mov = Mov(a, get);
                    if (Mov > max) {
                        max = Mov;
                        choice = k;
                    }
                }
                System.out.println("end");
                ArrayList<Integer> value = new ArrayList<>();
                value.add(1);
                value.add(countOponent(this.board.getBoard()));
                value.add(countMe(this.board.getBoard()));
                value.add(countOponentKings(this.board.getBoard()));
                value.add(countMyKings(this.board.getBoard()));
                value.add(countMeInDanger(this.board.getBoard()));
                value.add(countOponentInDanger(this.board.getBoard()));
                double valueFunction = valueFunction(weights, value);
                previous = valueFunction;
                return possible.get(choice);
            } else {
                System.out.println("random time:");
                int choice = (int) (Math.floor(Math.random() * super.getPossible().size()));
                return super.getPossible().get((int) (Math.floor(Math.random() * super.getPossible().size())));
            }
        } else {
            return null;
        }
    }

    public void nice(int err) {
        ArrayList<Integer> value = new ArrayList<>();
        value.add(1);
        value.add(countOponent(this.board.getBoard()));
        value.add(countMe(this.board.getBoard()));
        value.add(countOponentKings(this.board.getBoard()));
        value.add(countMyKings(this.board.getBoard()));
        value.add(countMeInDanger(this.board.getBoard()));
        value.add(countOponentInDanger(this.board.getBoard()));
        for (int o = 0; o < weights.size(); o++) {
            weights.set(o, weights.get(o) + eta * value.get(o) * err);
        }
    }

}
