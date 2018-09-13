package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Settings;
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
    private Label redRP;
    @FXML
    private Label blueRP;
    @FXML
    private StackPane redRankingPointsDisplay;
    @FXML
    private StackPane blueRankingPointsDisplay;
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
    @FXML
    private StackPane redNewHighScore;
    @FXML
    private StackPane blueNewHighScore;

    public void updateDisplay(Match match) {
         if (match.isTieBreaker()) {
            if (null != match.getType()) switch (match.getType()) {
                case QUARTERFINAL:
                    this.match.setText("QF " + match.getNumberInSeries() + " Tiebreaker");
                    break;
                case SEMIFINAL:
                    this.match.setText("SF " + match.getNumberInSeries() + " Tiebreaker");
                    break;
                case FINAL:
                    this.match.setText("Finals Tiebreaker");
                    break;
                default:
                    break;
            }
        } else if (match.getType() == Match.MatchType.NORMAL) {
            this.match.setText("Match " + match.getNumber());
        } else if (match.getType() == Match.MatchType.QUARTERFINAL) {
            this.match.setText("Quarterfinal " + match.getNumberInSeries() + " of " + match.getTotalInSeries());
        } else if (match.getType() == Match.MatchType.SEMIFINAL) {
            this.match.setText("Semifinal " + match.getNumberInSeries() + " of " + match.getTotalInSeries());
        } else if (match.getType() == Match.MatchType.FINAL) {
            this.match.setText("Final " + match.getNumberInSeries() + " of " + match.getTotalInSeries());
        }
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
        redRP.setText(String.valueOf(match.getRedRankingPoints()));
        blueRP.setText(String.valueOf(match.getBlueRankingPoints()));
        redRankingPointsDisplay.setVisible(Settings.enableRankingPoints);
        blueRankingPointsDisplay.setVisible(Settings.enableRankingPoints);
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
        int highScore = Schedule.getInstance().calculateHighScore(match);
        if (match.getRedScore() > highScore && match.getRedScore() > match.getBlueScore()) {
            redNewHighScore.setVisible(true);
        } else {
            redNewHighScore.setVisible(false);
        }
        if (match.getBlueScore() > highScore && match.getBlueScore() > match.getRedScore()) {
            blueNewHighScore.setVisible(true);
        } else {
            blueNewHighScore.setVisible(false);
        }
        if (match.getBlueScore() > highScore && match.getRedScore() == match.getBlueScore()) {
            blueNewHighScore.setVisible(true);
            redNewHighScore.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

}
