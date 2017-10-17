package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Team;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class AddLateCompetitorController implements Initializable {

    @FXML
    private TextField competitorNameField;
    @FXML
    private CheckBox useSurrogates;
    @FXML
    private Button addCompetitorButton;

    private Runnable callback;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addCompetitorButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return competitorNameField.getText().equals("");
        }, competitorNameField.textProperty()));
    }

    @FXML
    private void addCompetitor(ActionEvent event) {
        Team newTeam = new Team(competitorNameField.getText());
        int current = Schedule.getInstance().getCurrentMatch();

        int matchCount = Schedule.getInstance().getMatchesPlayedPerTeam();
        ArrayList<Integer> remainingMatchNumbers = new ArrayList<>();
        for (int i = current; i < Schedule.getInstance().getNumMatches(); i++) {
            remainingMatchNumbers.add(i);
        }
        Random rand = new Random();
        int[] matchNums = new int[matchCount];
        int i = 0;
        for (i = 0; i < matchCount && remainingMatchNumbers.size() > 0; i++) {
            matchNums[i] = remainingMatchNumbers.remove(rand.nextInt(remainingMatchNumbers.size()));
        }
        ArrayList<Team> removedTeams = new ArrayList<>();
        for (int j = 0; j < i; j += 4) {
            if (j + 4 > matchNums.length) {
                break;
            }
            for (int k = 0; k < 4; k++) {
                Match m = Schedule.getInstance().getMatch(matchNums[j + k]);
                Team t = m.getRed1();
                if (removedTeams.contains(t)) {
                    t = m.getRed2();
                    if (removedTeams.contains(t)) {
                        m.getBlue1();
                        if (removedTeams.contains(t)) {
                            m.getBlue2();
                            Schedule.getInstance().replaceMatch(matchNums[j + k], new Match(m.getRed1(), m.getRed2(), m.getBlue1(), newTeam, m.getNumber()));
                        }
                        Schedule.getInstance().replaceMatch(matchNums[j + k], new Match(m.getRed1(), m.getRed2(), newTeam, m.getBlue2(), m.getNumber()));
                    }
                    Schedule.getInstance().replaceMatch(matchNums[j + k], new Match(m.getRed1(), newTeam, m.getBlue1(), m.getBlue2(), m.getNumber()));
                }
                Schedule.getInstance().replaceMatch(matchNums[j + k], new Match(newTeam, m.getRed2(), m.getBlue1(), m.getBlue2(), m.getNumber()));
                removedTeams.add(t);
            }
            Schedule.getInstance().addMatchToEnd(new Match(removedTeams.get(0), removedTeams.get(1), removedTeams.get(2), removedTeams.get(3), Schedule.getInstance().getNumMatches() + 1));
            removedTeams.clear();
        }
        Team[] teams = Schedule.getInstance().getTeams();
        int currentTeamPos = -1;
        boolean neededSurrogates = false;
        ArrayList<Integer> endMatchNums = new ArrayList<>();
        if (!useSurrogates.isSelected()) {
            for (int j = 0; j < matchCount - i; j++) {
                endMatchNums.add(Schedule.getInstance().getNumMatches());
                Schedule.getInstance().addMatchToEnd(new Match(newTeam,
                        teams[++currentTeamPos % teams.length],
                        teams[++currentTeamPos % teams.length],
                        teams[++currentTeamPos % teams.length],
                        Schedule.getInstance().getNumMatches() + 1));
            }
        } else {
            for (int j = 0; j < matchCount - i; j++) {
                endMatchNums.add(Schedule.getInstance().getNumMatches());
                neededSurrogates = true;
                Schedule.getInstance().addMatchToEnd(new Match(newTeam,
                        new Team(teams[++currentTeamPos % teams.length].getName() + " (Surrogate)"),
                        new Team(teams[++currentTeamPos % teams.length].getName() + " (Surrogate)"),
                        new Team(teams[++currentTeamPos % teams.length].getName() + " (Surrogate)"),
                        Schedule.getInstance().getNumMatches() + 1));
            }
        }
        Schedule.getInstance().addTeam(newTeam);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added Late Competitor");
        alert.setHeaderText("Added Late Competitor");
        alert.setContentText(newTeam.getName() + " was successfully added to the schedule. He/She will play in matches: "
                + "\n\n\t" + getFormattedString(matchNums, endMatchNums.toArray(new Integer[]{}))
                + "\n\n" + (neededSurrogates ? "Needed to use surrogates for these matches:\n\n\t" 
                + getFormattedString(new int[]{}, endMatchNums.toArray(new Integer[]{}))
                : "Did not create any surrogate matches."));
        alert.showAndWait();
        if (callback != null) {
            callback.run();
        }
    }
    
    public void setCompletedCallback(Runnable runnable) {
        callback = runnable;
    }
    
    private String getFormattedString(int[] matchIndexes1, Integer[] matchIndexes2) {
        String s = "";
        Arrays.sort(matchIndexes1);
        Arrays.sort(matchIndexes2);
        for (int i : matchIndexes1) {
            s += Schedule.getInstance().getMatch(i).getNumber() + ", ";
        }
        for (Integer i : matchIndexes2) {
            s += Schedule.getInstance().getMatch(i).getNumber() + ", ";
        }
        return s.substring(0, s.length() - 2);
    }
}
