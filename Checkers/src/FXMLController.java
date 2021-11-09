/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gameplay.Cell;
import gameplay.Checkers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import machinelearning.Agent;
import machinelearning.Player;
import machinelearning.Random;
import utility.Entity;
import utility.Pair;
import utility.ProgressForm;

/**
 *
 * @author Hamed Khashehchi
 */
public class FXMLController implements Initializable {

    private int id = 0;
    protected Checkers checkers = new Checkers();
    private final int SIZEPIECE = 100;
    private final int MARGINLEFT = 40;
    private final int MARGINTOP = 5;
    protected int epoch;
    private ArrayList<Double> weights1 = new ArrayList<>();
    private File file1 = null;
    private File file2 = null;
    private ArrayList<Double> weights2 = new ArrayList<>();
    private Label label;
    private ProgressForm form;
    int test1 = 100;
    int win_against_random_init1 = 0;
    int lose_against_random_init1 = 0;
    int win_against_random_init2 = 0;
    int lose_against_random_init2 = 0;
    int test2 = 100;
    int test3 = 0;
    int win_against_bot_init = 0;
    int lose_against_bot_init = 0;
    @FXML
    private AnchorPane board;
    @FXML
    private Button train;
    @FXML
    private Button player1;
    @FXML
    private Button player2;
    @FXML
    private Button bot;
    @FXML
    private Button exit;
    @FXML
    private ImageView icon;
    @FXML
    private Button accuracy;
    @FXML
    private Button random;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        weights1.add(22.0);
        weights1.add(1.0);
        weights1.add(1.0);
        weights1.add(3.0);
        weights1.add(3.0);
        weights1.add(3.0);
        weights1.add(3.0);
        weights2.add(22.0);
        weights2.add(1.0);
        weights2.add(1.0);
        weights2.add(3.0);
        weights2.add(3.0);
        weights2.add(3.0);
        weights2.add(3.0);
        icon.setImage(new Image("checkers.png"));
        this.checkers.showBoard(board, SIZEPIECE, MARGINLEFT, MARGINTOP);
        accuracy.setText("accuracy me against random");
    }

//    private void showBoard() {
//        board.getChildren().removeAll(board.getChildren());
//        Cell[][] board1 = checkers.getBoard().getBoard();
//        for (int i = 0; i < board1.length; i++) {
//            for (int j = 0; j < board1[i].length; j++) {
//                if ((board1[i][j].getPoint().getRow() + board1[i][j].getPoint().getCollumn()) % 2 == 0) {
//                    Rectangle r = new Rectangle(SIZEPIECE, SIZEPIECE, Paint.valueOf("RED"));
//                    r.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
//                    r.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
//                    board.getChildren().add(r);
//                } else {
//                    Rectangle r = new Rectangle(SIZEPIECE, SIZEPIECE, Paint.valueOf("WHITE"));
//                    r.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
//                    r.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
//                    board.getChildren().add(r);
//                }
//
//                if (board1[i][j].getPieceColor().equals(Entity.RED)) {
//                    ImageView v = null;
//                    if (board1[i][j].getOccupied().equals(Entity.SOLDIER)) {
//                        v = new ImageView(new Image("white.png"));
//                    } else if (board1[i][j].getOccupied().equals(Entity.KING)) {
//                        v = new ImageView(new Image("whiteking.png"));
//                    }
//                    v.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
//                    v.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
//                    v.setFitWidth(SIZEPIECE);
//                    v.setFitHeight(SIZEPIECE);
//                    board.getChildren().add(v);
//                } else if (board1[i][j].getPieceColor().equals(Entity.BLACK)) {
//                    ImageView v = null;
//                    if (board1[i][j].getOccupied().equals(Entity.SOLDIER)) {
//                        v = new ImageView(new Image("black.png"));
//                    } else if (board1[i][j].getOccupied().equals(Entity.KING)) {
//                        v = new ImageView(new Image("blackking.png"));
//                    }
//                    v.setX(board1[i][j].getPoint().getX() * SIZEPIECE + MARGINLEFT);
//                    v.setY(board1[i][j].getPoint().getY() * SIZEPIECE + MARGINTOP);
//                    v.setFitWidth(SIZEPIECE);
//                    v.setFitHeight(SIZEPIECE);
//                    board.getChildren().add(v);
//                }
//            }
//        }
//    }
    //5 epoch
    //22.10654397222217 0.6080220199691796 2.18480371994939 -3.4993230874538552 2.9711950681970456 -4.019369511776858 2.98 w1
    //21.477562301172824 -0.8610389155382779 2.31910114876252 -0.4065727137969254 2.3952812331988182 3.731385442256129 3.0 w2
    @FXML
    private void trainClicked(MouseEvent event) {
        try {
            TextInputDialog dialog = new TextInputDialog("500");
            dialog.setTitle("Epoch number");
            dialog.setHeaderText("Epoch");
            dialog.setContentText("How many Epoch:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                this.epoch = Integer.parseInt(result.get());
            }

            result.ifPresent(epoch -> System.out.println("Epoch: " + epoch));
            ProgressForm pForm = new ProgressForm();

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    int temp = 0;
                    while (temp++ < epoch) {
                        updateProgress(temp - 1, epoch);
                        System.out.println("epoch = " + temp);
                        checkers.reset();
                        Player player1 = new Agent(Entity.RED, checkers.getBoard(), true, weights1);
                        Player player2 = new Agent(Entity.BLACK, checkers.getBoard(), true, weights2);
                        Pair move = null;

                        while (!checkers.isEnd()) {
                            if (player1.isMyTurn(checkers.getTurnColor())) {
                                player1.setPossible(checkers.possibleMoves());
                                move = ((Agent) player1).move();
                            } else if (player2.isMyTurn(checkers.getTurnColor())) {
                                player2.setPossible(checkers.possibleMoves());
                                move = ((Agent) player2).move();
                            }
                            checkers.play(move);
                            ((Agent) player1).setBoard(checkers.getBoard());
                            ((Agent) player2).setBoard(checkers.getBoard());
                            if (player1.isMyTurn(checkers.getTurnColor())) {
                            } else if (player2.isMyTurn(checkers.getTurnColor())) {
                            }
//                            System.out.println("peace = " + checkers.getPeace());
                        }
                        if (checkers.getWinner() != null) {
                            System.out.println(checkers.getWinner());
                            if (checkers.getWinner().equals(Entity.RED)) {
                                ((Agent) player1).nice(20);
                            } else if (checkers.getWinner().equals(Entity.BLACK)) {
                                ((Agent) player2).nice(20);
                            }
                        } else {
                            ((Agent) player1).nice(-4);
                            ((Agent) player2).nice(-4);
                            System.out.println("game ended in a draw!");
                        }
                        System.out.println("player1:");
                        System.out.println(((Agent) player1).getWeights());
                        System.out.println("player2:");
                        System.out.println(((Agent) player2).getWeights());
                        weights1 = ((Agent) player1).getWeights();
                        weights2 = ((Agent) player2).getWeights();
                        System.out.println("=============================\n\n\n\n\n\n\n\n\n\n\n\n\n===============================\n\n\n\n\n\n\n");
                    }
                    updateProgress(epoch, epoch);
                    FileWriter fooWriter;
                    try {
                        fooWriter = new FileWriter(file1, false);
                        String print = "";
                        for (int i = 0; i < weights1.size() - 1; i++) {
                            print += weights1.get(i) + " ";
                        }
                        print += weights1.get(weights1.size() - 1) + "\n";
                        fooWriter.write(print);
                        fooWriter.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("no writing on file");
                    }
                    try {
                        fooWriter = new FileWriter(file2, false);
                        String print = "";
                        for (int i = 0; i < weights2.size() - 1; i++) {
                            print += weights2.get(i) + " ";
                        }
                        print += weights2.get(weights1.size() - 1) + "\n";
                        fooWriter.write(print);
                        fooWriter.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("no writing on file");
                    }

                    Thread.sleep(1000);
                    return null;
                }
            };

            pForm.activateProgressBar(task);

            task.setOnSucceeded(eve -> {
                pForm.getDialogStage().close();
            });

            pForm.getDialogStage().show();

            Thread thread = new Thread(task);
            thread.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private File fileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Forsaken files (*.forsaken)", "*.forsaken");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(Program.stage);
    }

    private ArrayList<Double> parseWeights(int id) {
        BufferedReader br = null;
        try {
            File file = fileChooser();
            if (id == 1) {
                file1 = file;
            } else if (id == 2) {
                file2 = file;
            } else {
                return null;
            }
            br = new BufferedReader(new FileReader(file));
            String st;
            ArrayList<Double> weights = new ArrayList<>();
            while ((st = br.readLine()) != null) {
                System.out.println(st);
                String[] split = st.split(" ");
                for (String s : split) {
                    weights.add(Double.parseDouble(s));
                }
            }
            return weights;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @FXML
    private void player1File(MouseEvent event) {
        ArrayList<Double> parseWeights = parseWeights(1);
        System.out.println("parseWeights = " + parseWeights);
        this.weights1 = parseWeights;
    }

    @FXML
    private void player2file(MouseEvent event) {
        ArrayList<Double> parseWeights = parseWeights(2);
        System.out.println("parseWeights = " + parseWeights);
        this.weights2 = parseWeights;
    }

    @FXML
    private void startBotGame(MouseEvent event) {
        Player player1 = new Agent(Entity.RED, checkers.getBoard(), false, weights1);
        Player player2 = new Agent(Entity.BLACK, checkers.getBoard(), false, weights2);
        checkers.reset();
        Pair move = null;
        while (!checkers.isEnd()) {
            if (player1.isMyTurn(checkers.getTurnColor())) {
                player1.setPossible(checkers.possibleMoves());
                move = ((Agent) player1).move();
            } else if (player2.isMyTurn(checkers.getTurnColor())) {
                player2.setPossible(checkers.possibleMoves());
                move = ((Agent) player1).move();
            }
            checkers.play(move);
            ((Agent) player1).setBoard(checkers.getBoard());
            ((Agent) player2).setBoard(checkers.getBoard());
//            this.showBoard();
            checkers.showBoard(board, SIZEPIECE, MARGINLEFT, MARGINTOP);
        }
        if (checkers.getWinner() != null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("And the winner is");
            a.setHeaderText("");
            a.setContentText("" + checkers.getWinner());
            a.showAndWait();
        } else {
            System.out.println("game ended in a draw!");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("The game ended in ...");
            a.setContentText("DRAW\nNow, Nobody wins!");
            a.showAndWait();
        }
        System.out.println("=============================\n\n\n\n\n\n\n\n\n\n\n\n\n===============================\n\n\n\n\n\n\n");
    }

    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void accuracyClicked(MouseEvent event) {

        if (id == 0) {
            win_against_random_init1 = 0;
            lose_against_random_init1 = 0;
            form = new ProgressForm();

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    int temp = 0;

                    while (temp++ < test1) {
                        updateProgress(temp - 1, test1);
                        System.out.println("epoch = " + temp);
                        checkers.reset();
                        Player player1 = new Agent(Entity.RED, checkers.getBoard(), false, weights1);
                        Player player2 = new Random(Entity.BLACK);
                        Pair move = null;

                        while (!checkers.isEnd()) {
                            if (player1.isMyTurn(checkers.getTurnColor())) {
                                player1.setPossible(checkers.possibleMoves());
                                move = ((Agent) player1).move();
                            } else if (player2.isMyTurn(checkers.getTurnColor())) {
                                player2.setPossible(checkers.possibleMoves());
                                move = ((Random) player2).move();
                            }
                            checkers.play(move);
                            ((Agent) player1).setBoard(checkers.getBoard());
//                        System.out.println("peace = " + checkers.getPeace());
//                System.out.println("\n\n\n\n");
                        }
                        if (checkers.getWinner() != null) {
                            System.out.println(checkers.getWinner());
                            if (checkers.getWinner().equals(Entity.RED)) {
                                win_against_random_init1++;
                            } else if (checkers.getWinner().equals(Entity.BLACK)) {
                                lose_against_random_init1++;
                            }
                        } else {
                            System.out.println("game ended in a draw!");
                        }
                        System.out.println("=============================\n\n\n\n\n\n\n\n\n\n\n\n\n===============================\n\n\n\n\n\n\n");
                    }
                    updateProgress(test1, test1);
                    return null;
                }
            };

            try {
                form.activateProgressBar(task);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            task.setOnSucceeded(eve -> {
                form.getDialogStage().close();
            });

            form.getDialogStage().show();

            Thread thread = new Thread(task);
            thread.start();
            accuracy.setText("accuracy you against random");
            id++;
        } else if (id == 1) {

            win_against_random_init2 = 0;
            lose_against_random_init2 = 0;
            form = new ProgressForm();

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    int temp = 0;

                    while (temp++ < test2) {
                        updateProgress(temp - 1, test2);
                        System.out.println("epoch = " + temp);
                        checkers.reset();
                        Player player1 = new Agent(Entity.RED, checkers.getBoard(), false, weights2);
                        Player player2 = new Random(Entity.BLACK);
                        Pair move = null;

                        while (!checkers.isEnd()) {
                            if (player1.isMyTurn(checkers.getTurnColor())) {
                                player1.setPossible(checkers.possibleMoves());
                                move = ((Agent) player1).move();
                            } else if (player2.isMyTurn(checkers.getTurnColor())) {
                                player2.setPossible(checkers.possibleMoves());
                                move = ((Random) player2).move();
                            }
                            checkers.play(move);
                            ((Agent) player1).setBoard(checkers.getBoard());
//                        System.out.println("peace = " + checkers.getPeace());
//                System.out.println("\n\n\n\n");
                        }
                        if (checkers.getWinner() != null) {
                            System.out.println(checkers.getWinner());
                            if (checkers.getWinner().equals(Entity.RED)) {
                                win_against_random_init2++;
                            } else if (checkers.getWinner().equals(Entity.BLACK)) {
                                lose_against_random_init2++;
                            }
                        } else {
                            System.out.println("game ended in a draw!");
                        }
                        System.out.println("=============================\n\n\n\n\n\n\n\n\n\n\n\n\n===============================\n\n\n\n\n\n\n");
                    }
                    updateProgress(test2, test2);
                    return null;
                }
            };

            try {
                form.activateProgressBar(task);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            task.setOnSucceeded(eve -> {
                form.getDialogStage().close();
            });

            form.getDialogStage().show();

            Thread thread = new Thread(task);
            thread.start();
            accuracy.setText("accuracy of fight");
            id++;
        } else {

            win_against_bot_init = 0;
            lose_against_bot_init = 0;
            form = new ProgressForm();

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    int temp = 0;

                    while (temp++ < test3) {
                        updateProgress(temp - 1, test3);
                        System.out.println("epoch = " + temp);
                        checkers.reset();
                        Player player1 = new Agent(Entity.RED, checkers.getBoard(), false, weights1);
                        Player player2 = new Agent(Entity.RED, checkers.getBoard(), false, weights2);
                        Pair move = null;

                        while (!checkers.isEnd()) {
                            if (player1.isMyTurn(checkers.getTurnColor())) {
                                player1.setPossible(checkers.possibleMoves());
                                move = ((Agent) player1).move();
                            } else if (player2.isMyTurn(checkers.getTurnColor())) {
                                player2.setPossible(checkers.possibleMoves());
                                move = ((Agent) player1).move();
                            }
                            checkers.play(move);
                            ((Agent) player1).setBoard(checkers.getBoard());
                            ((Agent) player2).setBoard(checkers.getBoard());
                            System.out.println("peace = " + checkers.getPeace());
//                System.out.println("\n\n\n\n");
                        }
                        if (checkers.getWinner() != null) {
                            System.out.println(checkers.getWinner());
                            if (checkers.getWinner().equals(Entity.RED)) {
                                win_against_bot_init++;
                            } else if (checkers.getWinner().equals(Entity.BLACK)) {
                                lose_against_bot_init++;
                            }
                        } else {
                            System.out.println("game ended in a draw!");
                        }
                        System.out.println("=============================\n\n\n\n\n\n\n\n\n\n\n\n\n===============================\n\n\n\n\n\n\n");
                    }
                    updateProgress(test3, test3);
                    return null;
                }
            };

            try {
                form.activateProgressBar(task);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            task.setOnSucceeded(eve -> {
                form.getDialogStage().close();
            });

            form.getDialogStage().show();

            Thread thread = new Thread(task);
            thread.start();
            accuracy.setText("accuracy me against random");
            id = 0;

        }
    }

    @FXML
    private void randomClicked(MouseEvent event) {
        Player player1 = new Agent(Entity.RED, checkers.getBoard(), false, weights1);
        Player player2 = new Random(Entity.BLACK);
        checkers.reset();
        Pair move = null;
        while (!checkers.isEnd()) {
            if (player1.isMyTurn(checkers.getTurnColor())) {
                player1.setPossible(checkers.possibleMoves());
                move = ((Agent) player1).move();
            } else if (player2.isMyTurn(checkers.getTurnColor())) {
                player2.setPossible(checkers.possibleMoves());
                move = ((Random) player2).move();
            }
            checkers.play(move);
            ((Agent) player1).setBoard(checkers.getBoard());
//            this.showBoard();
            checkers.showBoard(board, SIZEPIECE, MARGINLEFT, MARGINTOP);
        }
        if (checkers.getWinner() != null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("And the winner is");
            a.setHeaderText("");
            a.setContentText("" + checkers.getWinner());
            a.showAndWait();
        } else {
            System.out.println("game ended in a draw!");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("The game ended in ...");
            a.setContentText("DRAW\nNow, Nobody wins!");
            a.showAndWait();
        }
        System.out.println("=============================\n\n\n\n\n\n\n\n\n\n\n\n\n===============================\n\n\n\n\n\n\n");
    }

    @FXML
    private void checkerscheckers(MouseEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("stats");
        a.setHeaderText("Out of " + test1 + " games:");
        a.setContentText(win_against_random_init1 + " games we have won, " + lose_against_random_init1 + " games we have lost!");
        a.showAndWait();
        a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("stats");
        a.setHeaderText("Out of " + test2 + " games:");
        a.setContentText(win_against_random_init2 + " games you have won, " + lose_against_random_init2 + " games you have lost!");
        a.showAndWait();
        a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("stats");
        a.setHeaderText("Out of " + test3 + " games:");
        a.setContentText(win_against_bot_init + " games we have won against you, " + lose_against_bot_init + " games we have lost against you!");
        a.showAndWait();
    }

    @FXML
    private void ext(MouseEvent event) {
        this.icon.setOpacity(1);
    }

    @FXML
    private void enter(MouseEvent event) {
        this.icon.setOpacity(0.1);
    }

}
