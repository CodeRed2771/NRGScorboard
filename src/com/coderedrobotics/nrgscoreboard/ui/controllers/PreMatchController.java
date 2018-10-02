package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Rankings;
import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Settings;
import com.coderedrobotics.nrgscoreboard.Team;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class PreMatchController implements Initializable {

    @FXML
    private Label red1;
    @FXML
    private Label red2;
    @FXML
    private Label blue1;
    @FXML
    private Label blue2;
    @FXML
    private Label nextMatch;
    @FXML
    private Label rank1;
    @FXML
    private Label rank2;
    @FXML
    private Label rank3;
    @FXML
    private Label rank4;
    @FXML
    private Label rank5;
    @FXML
    private Label rank6;
    @FXML
    private Label rank7;
    @FXML
    private Label rank8;
    @FXML
    private Label rank9;
    @FXML
    private Label rank10;

    private UpdaterThread thread;
    private int dislayingRankingsStartingWith = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void updateTeamDisplays(Match m) {
        nextMatch.setText("Next Match: " + m.getNumber());
        this.red1.setText(m.getRed1().getName());
        this.red2.setText(m.getRed2().getName());
        this.blue1.setText(m.getBlue1().getName());
        this.blue2.setText(m.getBlue2().getName());
    }

    public void startRankingDisplay() {
        if (thread != null && thread.running) {
            thread.running = false; // this should probably throw an exception
        }
        thread = new UpdaterThread();
        thread.run();
    }

    public void stopRankingDisplay() {
        if (thread != null) {
            thread.running = false;
        }
        thread = null;
    }

    private class UpdaterThread {

        private boolean running;

        public void run() {
            running = true;
            dislayingRankingsStartingWith = 1;
            final Label[] rankDisplays = {rank1, rank2, rank3, rank4, rank5, rank6, rank7, rank8, rank9, rank10};
            new Thread(() -> {
                while (running) {
                    Team[] rankedTeams = Rankings.getInstance().getRankedTeams();
                    for (Label rankDisplay : rankDisplays) {
                        Platform.runLater(() -> {
                            rankDisplay.setText("");
                        });
                    }
                    for (int i = dislayingRankingsStartingWith - 1; i < dislayingRankingsStartingWith + 9 && i < rankedTeams.length; i++) {
                        final Team t = rankedTeams[i];
                        final int index = i;
                        Platform.runLater(() -> {
                            rankDisplays[index % 10].setText("Rank " + String.format("%02d", index + 1) + ": " 
                                    + String.format("%-" + (Schedule.getInstance().getLengthOfLongestTeamName() + 1) + "s", t.getName())
                                    + (Settings.enableRankingPoints ? "\tAvg. RP: " + String.format("%.3f", t.getAverageRankingPoints()) : "") 
                                    + "\t\t(" + t.getWins() + "-" + t.getLosses() + "-" + t.getTies() 
                                    + ")\tTotal Penalty Points: " + t.getTotalPenaltyPoints());
                        });
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PreMatchController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (dislayingRankingsStartingWith + 9 < rankedTeams.length) {
                        dislayingRankingsStartingWith += 10;
                    } else {
                        dislayingRankingsStartingWith = 1;
                    }
                }
            }).start();
        }
    }
}
