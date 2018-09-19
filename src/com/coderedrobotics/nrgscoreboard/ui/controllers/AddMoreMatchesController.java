package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Team;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class AddMoreMatchesController implements Initializable {

    @FXML
    private Spinner numberMatchesSpinner;

    @FXML
    private Label eachTeamWillPlayLabel;

    private Runnable callback;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberMatchesSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            recalculateNumMatchesPerTeam();
        });
    }

    public void setCompletedCallback(Runnable callback) {
        this.callback = callback;
        
        numberMatchesSpinner.getValueFactory().setValue(Schedule.getInstance().getTeams().length);
        recalculateNumMatchesPerTeam();
    }

    private void recalculateNumMatchesPerTeam() {
        int numTeams = Schedule.getInstance().getTeams().length;
        int numMatches = (int) numberMatchesSpinner.getValue();
        int matchesPerTeam = (int) Math.floor(((double) numMatches * 4d) / (double) numTeams);

        if ((numMatches * 4) % numTeams != 0) {
            eachTeamWillPlayLabel.setText("With this number, some teams will play " + matchesPerTeam + " matches, some will play " + (matchesPerTeam + 1) + ".");
        } else {
            eachTeamWillPlayLabel.setText("With this number, each play will play: " + matchesPerTeam + " matches.");
        }
    }

    @FXML
    private void generateMatchSchedule(ActionEvent action) {
        int numMatches = (int) numberMatchesSpinner.getValue();

        Team[] teams = Schedule.getInstance().getTeams();
        
        ArrayList<Team> usedTeams = new ArrayList<>();
        ArrayList<Team> usedMatchTeams = new ArrayList<>();

        Match[] matches = new Match[numMatches];

        for (int i = 0; i < numMatches; i++) {
            Team red1 = getTeam(teams, usedTeams, usedMatchTeams);
            Team red2 = getTeam(teams, usedTeams, usedMatchTeams);
            Team blue1 = getTeam(teams, usedTeams, usedMatchTeams);
            Team blue2 = getTeam(teams, usedTeams, usedMatchTeams);

            usedMatchTeams.clear();

            matches[i] = new Match(red1, red2, blue1, blue2, i + 1 + Schedule.getInstance().getMatches().length);
        }
        ArrayList<Match> fullScheduleAsList = new ArrayList<>();
        fullScheduleAsList.addAll(Arrays.asList(Schedule.getInstance().getMatches()));
        fullScheduleAsList.addAll(Arrays.asList(matches));
        Match[] fullSchedule = fullScheduleAsList.toArray(new Match[]{});

        Schedule.initialize(fullSchedule, teams, (int) Math.floor(((double) fullSchedule.length * 4d) / (double) teams.length));

        File schedule = new File("schedule.csv");
        try {
            schedule.createNewFile();
            FileWriter writer = new FileWriter(schedule);
            for (Match m : matches) {
                writer.write(String.format("%s,%s,%s,%s\n", m.getRed1().getName(), m.getRed2().getName(), m.getBlue1().getName(), m.getBlue2().getName()));
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(GenerateMatchesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        callback.run();
    }

    private Team getTeam(Team[] teams, ArrayList<Team> usedTeams, ArrayList<Team> usedMatchTeams) {
        if (usedTeams.size() == teams.length) {
            usedTeams.clear();
        }

        Team selectedTeam;
        Random rand = new Random();
        do {
            selectedTeam = teams[rand.nextInt(teams.length)];
        } while (usedTeams.contains(selectedTeam) || usedMatchTeams.contains(selectedTeam));
        usedTeams.add(selectedTeam);
        usedMatchTeams.add(selectedTeam);
        return selectedTeam;
    }

}
