package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule.Match;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
    @FXML
    private Label redMatchPoints;
    @FXML
    private Label blueMatchPoints;
    @FXML
    private Label redPenaltyPoints;
    @FXML
    private Label bluePenaltyPoints;
    @FXML
    private StackPane redWinsBanner;
    @FXML
    private StackPane blueWinsBanner;
    @FXML
    private Label redWinsLabel;
    @FXML
    private Label blueWinsLabel;

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
        redMatchPoints.setText("Red Points: " + match.getRedPoints());
        redPenaltyPoints.setText("Red Penalty: " + match.getRedPenalty());
        blueMatchPoints.setText("Blue Points: " + match.getBluePoints());
        bluePenaltyPoints.setText("Blue Penalty: " + match.getBluePenalty());
        if (match.getBlueScore() > match.getRedScore()) {
            redWinsBanner.setOpacity(0.0);
            blueWinsBanner.setOpacity(1.0);
            blueWinsLabel.setText("Blue Alliance Wins!");
        } else if (match.getBlueScore() < match.getRedScore()) {
            redWinsBanner.setOpacity(1.0);
            blueWinsBanner.setOpacity(0.0);
            redWinsLabel.setText("Red Alliance Wins!");
        } else {
            redWinsBanner.setOpacity(1.0);
            blueWinsBanner.setOpacity(1.0);
            redWinsLabel.setText("Tie!");
            blueWinsLabel.setText("Tie!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
    
}
