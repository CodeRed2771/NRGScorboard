package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Team;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class EditNamesController implements Initializable {

    @FXML
    private VBox vBox;
    
    private TextField[] fields;
    
    private Runnable callback;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void init() {
        Team[] teams = Schedule.getInstance().getTeams();
        fields = new TextField[teams.length];
        for (int i =0; i < teams.length; i++) {
            fields[i] = new TextField(teams[i].getName());
            vBox.getChildren().add(fields[i]);
        }
    }
    
    public void setCompletedCallback(Runnable callback) {
        this.callback = callback;
    }
    
    @FXML
    private void saveNames(ActionEvent event) {
        Team[] teams = Schedule.getInstance().getTeams();
        for (int i = 0; i < teams.length; i++) {
            if (fields[i].getText().equals("")) {
                // show an alert
                return;
            }
            teams[i].setName(fields[i].getText());
        }
        callback.run();
    }
    
}
