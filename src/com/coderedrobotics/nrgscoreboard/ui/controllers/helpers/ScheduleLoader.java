package com.coderedrobotics.nrgscoreboard.ui.controllers.helpers;

import com.coderedrobotics.nrgscoreboard.Main;
import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Team;
import com.coderedrobotics.nrgscoreboard.ui.controllers.GenerateMatchesController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author Michael
 */
public class ScheduleLoader {

    public boolean loadMatchSchedule() {
        BufferedReader read;
        String line;
        HashMap<String, Team> teamsandnames = new HashMap<>();
        ArrayList<Team> allteams = new ArrayList<>();
        try {
            read = new BufferedReader(new FileReader("schedule.csv"));
            ArrayList<Match> matches = new ArrayList<>();
            int mn = 1;
            String observedTeamForCountName = null;
            int matchesPerTeam = 0;
            while ((line = read.readLine()) != null) {
                String[] data = line.split(",");
                Team[] teams = new Team[4];
                for (int i = 0; i < 4; i++) {
                    if (observedTeamForCountName == null) {
                        observedTeamForCountName = data[i];
                        matchesPerTeam++;
                    } else if (observedTeamForCountName.equals(data[i])) {
                        matchesPerTeam++;
                    }

                    if (teamsandnames.containsKey(data[i])) {
                        teams[i] = teamsandnames.get(data[i]);
                    } else {
                        Team t = new Team(data[i]);
                        allteams.add(t);
                        teamsandnames.put(data[i], t);
                        teams[i] = t;
                    }
                }
                Match m = new Match(teams[0], teams[1], teams[2], teams[3], mn);
                mn++;
                matches.add(m);
            }
            Schedule.initialize(matches.toArray(new Match[0]), allteams.toArray(new Team[0]), matchesPerTeam);
            read.close();

            return true;
        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Match Schedule");
            alert.setHeaderText("Couldn't Read/Find Schedule");
            alert.setContentText("An error occured while reading the match schedule. "
                    + "The file either was not found or is not formatted correctly. "
                    + "Verify you have a CSV schdule file named \"schedule.csv\" in "
                    + "the same directory as this application and try again.");

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();
            Label label = new Label("Exception Details: ");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);
            alert.show();
        }
        return false;
    }

    public boolean loadCompetitionBackup() {
        BufferedReader read;
        String line;
        HashMap<String, Team> teamsandnames = new HashMap<>();
        ArrayList<Team> allteams = new ArrayList<>();
        try {
            read = new BufferedReader(new FileReader("competition_backup.csv"));
            ArrayList<Match> matches = new ArrayList<>();
            read.readLine();
            int mn = 1;
            String observedTeamForCountName = null;
            int matchesPerTeam = 0;
            while ((line = read.readLine()) != null) {
                System.out.println(line);
                String[] data = line.split(",");
                Team[] teams = new Team[4];
                for (int i = 1; i < 5; i++) {
                    if (observedTeamForCountName == null) {
                        observedTeamForCountName = data[i];
                        matchesPerTeam++;
                    } else if (observedTeamForCountName.equals(data[i])) {
                        matchesPerTeam++;
                    }

                    if (teamsandnames.containsKey(data[i])) {
                        teams[i - 1] = teamsandnames.get(data[i]);
                    } else {
                        Team t = new Team(data[i]);
                        allteams.add(t);
                        teamsandnames.put(data[i], t);
                        teams[i - 1] = t;
                    }
                }
                Match m = new Match(teams[0], teams[1], teams[2], teams[3], mn);
                if (!data[5].equals("--")) {
                    m.setScore(Integer.parseInt(data[5]), Integer.parseInt(data[6]),
                            Integer.parseInt(data[9]), Integer.parseInt(data[7]),
                            Integer.parseInt(data[10]), Integer.parseInt(data[8]),
                            Integer.parseInt(data[11]), Integer.parseInt(data[12]));
                }
                mn++;
                matches.add(m);
            }
            System.out.println("Read backup with: " + matches.size() + " matches and " + allteams.size() + " teams");
            Schedule.initialize(matches.toArray(new Match[0]), allteams.toArray(new Team[0]), matchesPerTeam);
            read.close();
            
            return true;
        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Match Schedule");
            alert.setHeaderText("Couldn't Read/Find Schedule");
            alert.setContentText("An error occured while reading the backup file. "
                    + "The file either was not found or is not formatted correctly. "
                    + "Verify you have a CSV schdule file named \"competition_backup.csv\" in "
                    + "the same directory as this application and try again.");

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();
            Label label = new Label("Exception Details: ");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);
            alert.show();
        }
        return false;
    }
    
    public void writeCompetitionBackup() {
        File schedule = new File("competition_backup.csv");
        try {
            schedule.createNewFile();
            FileWriter writer = new FileWriter(schedule);
            writer.write("Match #,Red 1,Red 2,Blue 1,Blue 2,Red Score,Blue Score,Red Penalty,Blue Penalty,Red Points,Blue Points,Red RP,Blue RP\n");
            for (Match m : Schedule.getInstance().getMatches()) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        m.getNumber(), m.getRed1().getName(), m.getRed2().getName(),
                        m.getBlue1().getName(), m.getBlue2().getName(),
                        m.isScored() ? m.getRedScore() : "--",
                        m.isScored() ? m.getBlueScore() : "--",
                        m.isScored() ? m.getRedPenalty() : "--",
                        m.isScored() ? m.getBluePenalty() : "--",
                        m.isScored() ? m.getRedPoints() : "--",
                        m.isScored() ? m.getBluePoints() : "--",
                        m.isScored() ? m.getRedRankingPoints(): "--",
                        m.isScored() ? m.getBlueRankingPoints() : "--"));
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(GenerateMatchesController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Backup Error");
            alert.setHeaderText("Couldn't Write Backup File");
            alert.setContentText("An error occured while writing the competition backup file. "
                    + "This competition is not being backed up. Make sure the file is not "
                    + "in use by another process.");
            alert.show();
        }
    }

}
