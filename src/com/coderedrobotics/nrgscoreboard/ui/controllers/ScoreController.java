package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule.Match;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author Michael
 */
public class ScoreController implements Initializable {
    
    @FXML
    private Label red1;
    @FXML
    private Label red2;
    @FXML
    private Label blue1;
    @FXML
    private Label blue2;
    @FXML
    private Label match;
    @FXML
    private Label red1info;
    @FXML
    private Label red2info;
    @FXML
    private Label blue1info;
    @FXML
    private Label blue2info;
    @FXML
    private Label redscore;
    @FXML
    private Label bluescore;    
    
    public void updateDisplay(Match match) {
        this.match.setText("Match " + match.getNumber());
        this.red1.setText(match.getRed1().getName());
        this.red2.setText(match.getRed2().getName());
        this.blue1.setText(match.getBlue1().getName());
        this.blue2.setText(match.getBlue2().getName());
        if (match.getRedScore() >= 100 || match.getBlueScore() >= 100) {
            redscore.setFont(new Font("System Bold", 150));
            bluescore.setFont(new Font("System Bold", 150));
        } else {
            redscore.setFont(new Font("System Bold", 175));
            bluescore.setFont(new Font("System Bold", 175));
        }
        redscore.setText(String.valueOf(match.getRedScore()));
        bluescore.setText(String.valueOf(match.getBlueScore()));
        red1info.setText(match.getRed1().getName() + ": Ranked " + match.getRed1().getRank());
        red2info.setText(match.getRed2().getName() + ": Ranked " + match.getRed2().getRank());
        blue1info.setText(match.getBlue1().getName() + ": Ranked " + match.getBlue1().getRank());
        blue2info.setText(match.getBlue2().getName() + ": Ranked " + match.getBlue2().getRank());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
    
}
