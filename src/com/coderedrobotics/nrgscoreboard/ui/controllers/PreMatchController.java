package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Team;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
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

    public void updateTeamDisplays(Schedule.Match m) {
        nextMatch.setText("Next Match: " + m.getNumber());
        this.red1.setText(m.getRed1().getName());
        this.red2.setText(m.getRed2().getName());
        this.blue1.setText(m.getBlue1().getName());
        this.blue2.setText(m.getBlue2().getName());
    }

    public void startRankingDisplay() {
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
            TreeMap<Integer, Integer> map = new TreeMap<>();
            Team[] teams = Schedule.getInstance().getTeams();
            for (int i = 0; i < teams.length; i++) {
                map.put(i, teams[i].getTotalScore());
            }
            SortedSet<Map.Entry<Integer, Integer>> sortedEntries = new TreeSet<>(
                    (Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) -> {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return -(res != 0 ? res : 1);
                    });
            sortedEntries.addAll(map.entrySet());
            String data = "";
            final Label[] rankDisplays = {rank1, rank2, rank3, rank4, rank5, rank6, rank7, rank8, rank9, rank10};
            new Thread(() -> {
                while (running) {
                    for (Label rankDisplay : rankDisplays) {
                        Platform.runLater(() -> {
                            rankDisplay.setText("");
                        });
                    }

                    Object[] array = sortedEntries.toArray();
                    for (int i = dislayingRankingsStartingWith - 1; i < dislayingRankingsStartingWith + 9 && i < array.length; i++) {
                        Map.Entry<Integer, Integer> e = (Map.Entry<Integer, Integer>) array[i];
                        final Team t = teams[e.getKey()];
                        final int index = i;
                        Platform.runLater(() -> {
                            rankDisplays[index % 10].setText("Rank " + String.valueOf(index + 1) + ": " + t.getName()
                                    + "   (" + t.getWins() + "-" + t.getLosses() + "-"
                                    + t.getTies() + ")   Total Score: " + t.getTotalScore());
                        });
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PreMatchController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (dislayingRankingsStartingWith + 9 < teams.length) {
                        dislayingRankingsStartingWith += 10;
                    } else {
                        dislayingRankingsStartingWith = 1;
                    }
                }
            }).start();
        }

    }
}
