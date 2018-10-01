package com.coderedrobotics.nrgscoreboard.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class EnterScoresController implements Initializable {

    @FXML
    private TextField redScoreField;

    @FXML
    private TextField blueScoreField;

    @FXML
    private TextField redPenaltyField;

    @FXML
    private TextField bluePenaltyField;

    @FXML
    private TextField redRankingPoints;

    @FXML
    private TextField blueRankingPoints;

    @FXML
    private Label redTotalScore;

    @FXML
    private Label blueTotalScore;

    private Consumer8 callback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        redScoreField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
        blueScoreField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
        redPenaltyField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
        bluePenaltyField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
    }

    @FXML
    private void applyScoring(ActionEvent event) {
        int red, blue, redPenalty, redPoints, bluePoints, bluePenalty, redRankingPoints, blueRankingPoints;
        try {
            redPoints = Integer.parseInt(redScoreField.getText());
            bluePoints = Integer.parseInt(blueScoreField.getText());
            redPenalty = Integer.parseInt(redPenaltyField.getText());
            bluePenalty = Integer.parseInt(bluePenaltyField.getText());
            redRankingPoints = Integer.parseInt(this.redRankingPoints.getText());
            blueRankingPoints = Integer.parseInt(this.blueRankingPoints.getText());
            red = redPoints + bluePenalty;
            blue = bluePoints + redPenalty;
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Score");
            alert.setHeaderText(null);
            alert.setContentText("Enter a valid integer value for both red "
                    + "and blue scores before applying the scores.");

            alert.show();
            return;
        }

        if (callback != null) {
            callback.accept(red, blue, redPoints, redPenalty, bluePoints, bluePenalty, redRankingPoints, blueRankingPoints);
        }
        
        redScoreField.setText("");
        blueScoreField.setText("");
        redPenaltyField.setText("");
        bluePenaltyField.setText("");
        this.redRankingPoints.setText("");
        this.blueRankingPoints.setText("");
    }

    @FunctionalInterface
    interface Consumer8<A, B, C, D, E, F, G, H> {

        public void accept(A a, B b, C c, D d, E e, F f, G g, H h);
    }

    public void setSaveCallback(Consumer8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> callback) {
        this.callback = callback;
    }

    private void recalculateTotalScores() {
        int bluePoints, redPoints, bluePenalty, redPenalty;
        try {
            redPoints = Integer.parseInt(redScoreField.getText());
            bluePenalty = Integer.parseInt(bluePenaltyField.getText());
            int red = redPoints + bluePenalty;
            redTotalScore.setText("Total: " + red);
        } catch (NumberFormatException ex) {
            redTotalScore.setText("Total: ---");
        }

        try {
            bluePoints = Integer.parseInt(blueScoreField.getText());
            redPenalty = Integer.parseInt(redPenaltyField.getText());
            int blue = bluePoints + redPenalty;
            blueTotalScore.setText("Total: " + blue);
        } catch (NumberFormatException ex) {
            blueTotalScore.setText("Total: ---");
        }
    }
}
