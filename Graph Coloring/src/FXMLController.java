/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Hamed Khashehchi
 */
public class FXMLController implements Initializable {
    
    @FXML
    private AnchorPane pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Color red = new Color("red");
        Color black = new Color("black");
        ArrayList<Color> colors=new ArrayList<>();
        colors.add(red);
        colors.add(black);
        Node n0=new Node(0, "x1", colors);
        Node n1=new Node(1, "x2", colors);
        Node n2=new Node(2, "x3", colors);
        n0.addAdjacent(n1);
        n0.addAdjacent(n2);
        
    }    
    
}
