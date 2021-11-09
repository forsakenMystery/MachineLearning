/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminate;

import java.net.URL;
import java.time.chrono.IsoEra;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Hamed Khashehchi
 */
public class FXMLController implements Initializable {
    private CandidateEliminate ce;
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("ce = " + ce);
        System.out.println("======================\n=====================\n");
        ce.train();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String s="E:\\Code\\Java\\CandidateEliminate\\src\\trainingDataCandElim.csv";
        this.ce=new CandidateEliminate(s, "Enjoy Sport", "Do Not Enjoy");
    }    
    
}
