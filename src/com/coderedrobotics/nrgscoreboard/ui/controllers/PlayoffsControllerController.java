package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Schedule.Match;
import com.coderedrobotics.nrgscoreboard.Team;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class PlayoffsControllerController implements Initializable {

    @FXML
    private ComboBox rank1Pick;
    @FXML
    private ComboBox rank2Pick;
    @FXML
    private ComboBox rank3Pick;
    @FXML
    private ComboBox rank4Pick;
    @FXML
    private ComboBox rank5Pick;
    @FXML
    private ComboBox rank6Pick;
    @FXML
    private ComboBox rank7Pick;
    @FXML
    private ComboBox rank8Pick;
    @FXML
    private Button applyPicksButton;

    private ControllerController controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void init(ControllerController controller) {
        Team[] teams = Schedule.getInstance().getTeams();
        ComboBox[] boxes = {rank1Pick, rank2Pick, rank3Pick, rank4Pick, rank5Pick, rank6Pick, rank7Pick, rank8Pick};
        String[] teamNames = new String[teams.length];
        for (int i = 0; i < teams.length; i++) {
            teamNames[i] = teams[i].getName();
        }
        for (ComboBox box : boxes) {
            box.getItems().addAll((Object[]) teamNames);
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < teams.length; i++) {
            map.put(i, teams[i].getTotalScore());
        }
        SortedSet<Map.Entry<Integer, Integer>> sortedEntries = new TreeSet<>(
                (Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return -(res != 0 ? res : 1);
                });
        sortedEntries.addAll(map.entrySet());
        Object[] array = sortedEntries.toArray();
        for (int i = 0; i < 8; i++) {
            boxes[i].getSelectionModel().select((Object) teams[((Map.Entry<Integer, Integer>) array[i]).getKey()].getName());
        }
        this.controller = controller;
    }

    @FXML
    public void applyRound1Picks(ActionEvent event) {
        rank1Pick.setDisable(true);
        rank2Pick.setDisable(true);
        rank3Pick.setDisable(true);
        rank4Pick.setDisable(true);
        rank5Pick.setDisable(true);
        rank6Pick.setDisable(true);
        rank7Pick.setDisable(true);
        rank8Pick.setDisable(true);
        applyPicksButton.setDisable(true);
        Match sf1 = new Schedule.Match(findTeam(rank1Pick.getSelectionModel().getSelectedItem()),
                findTeam(rank8Pick.getSelectionModel().getSelectedItem()),
                findTeam(rank4Pick.getSelectionModel().getSelectedItem()),
                findTeam(rank5Pick.getSelectionModel().getSelectedItem()), 1);
        Match sf2 = new Schedule.Match(findTeam(rank2Pick.getSelectionModel().getSelectedItem()),
                findTeam(rank7Pick.getSelectionModel().getSelectedItem()),
                findTeam(rank3Pick.getSelectionModel().getSelectedItem()),
                findTeam(rank6Pick.getSelectionModel().getSelectedItem()), 2);
        controller.enableEliminationsMode(sf1, sf2);
    }

    private Team findTeam(Object object) {
        String name = (String) object;
        Team[] teams = Schedule.getInstance().getTeams();
        for (Team t : teams) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }
}
