package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Rankings;
import com.coderedrobotics.nrgscoreboard.Team;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class PlayoffsControllerController implements Initializable {

    @FXML
    private RadioButton fourAllianceSingle;
    @FXML
    private RadioButton fourAllianceDouble;
    @FXML
    private RadioButton eightAllianceSingle;
    @FXML
    private RadioButton eightAllianceDouble;
    @FXML
    private RadioButton twoAllianceSingle;
    @FXML
    private RadioButton twoAllianceDouble;
    @FXML
    private RadioButton roundRobin;
    @FXML
    private ComboBox alliance1Team1;
    @FXML
    private ComboBox alliance1Team2;
    @FXML
    private ComboBox alliance2Team1;
    @FXML
    private ComboBox alliance2Team2;
    @FXML
    private ComboBox alliance3Team1;
    @FXML
    private ComboBox alliance3Team2;
    @FXML
    private ComboBox alliance4Team1;
    @FXML
    private ComboBox alliance4Team2;
    @FXML
    private ComboBox alliance5Team1;
    @FXML
    private ComboBox alliance5Team2;
    @FXML
    private ComboBox alliance6Team1;
    @FXML
    private ComboBox alliance6Team2;
    @FXML
    private ComboBox alliance7Team1;
    @FXML
    private ComboBox alliance7Team2;
    @FXML
    private ComboBox alliance8Team1;
    @FXML
    private ComboBox alliance8Team2;
    @FXML
    private Label semiFinal1UnderdogLabel;
    @FXML
    private Label semiFinal2UnderdogLabel;
    @FXML
    private Label semiFinal1Label;
    @FXML
    private Label semiFinal2Label;
    @FXML
    private Button applyPicksButton;
    @FXML
    private Pane quarter2Pane;
    @FXML
    private Pane quarter3Pane;
    @FXML
    private Pane quarter4Pane;

    public static enum PlayoffType {
        EIGHT_ALLIANCE_DOUBLE, EIGHT_ALLIANCE_SINGLE, FOUR_ALLIANCE_DOUBLE,
        FOUR_ALLIANCE_SINGLE, TWO_ALLIANCE_DOUBLE, TWO_ALLIANCE_SINGLE
    }
    
    private ControllerController controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fourAllianceDouble.selectedProperty().addListener((observable) -> {
            autoFillFourAlliance();
        });
        fourAllianceSingle.selectedProperty().addListener((observable) -> {
            autoFillFourAlliance();
        });
        eightAllianceDouble.selectedProperty().addListener((observable) -> {
            autoFillEightAlliance();
        });
        eightAllianceSingle.selectedProperty().addListener((observable) -> {
            autoFillEightAlliance();
        });
        twoAllianceDouble.selectedProperty().addListener((observable) -> {
            autoFillTwoAlliance();
        });
        twoAllianceSingle.selectedProperty().addListener((observable) -> {
            autoFillTwoAlliance();
        });
    }

    public void autoFillTwoAlliance() {
        semiFinal1Label.setText("Final 1");
        semiFinal2Label.setText("Quarter-Final 2");
        quarter2Pane.setDisable(true);
        quarter3Pane.setDisable(true);
        quarter4Pane.setDisable(true);
        semiFinal1UnderdogLabel.setText("#2");
        semiFinal2UnderdogLabel.setText("#7");

        Team[] teams = Schedule.getInstance().getTeams();

        ComboBox[] boxes = {alliance1Team1, alliance1Team2,
            alliance8Team1, alliance8Team2
        };
        String[] teamNames = new String[teams.length];
        for (int i = 0;
                i < teams.length;
                i++) {
            teamNames[i] = teams[i].getName();
        }
        Arrays.sort(teamNames);
        for (ComboBox box : boxes) {
            box.getItems().addAll((Object[]) teamNames);
        }
        Team[] rankedTeams = Rankings.getInstance().getRankedTeams();
        for (int i = 0;
                i < 2; i++) {
            boxes[i * 2].getSelectionModel().select((Object) rankedTeams[i].getName());
            boxes[i * 2 + 1].getSelectionModel().select((Object) rankedTeams[i].getName());
        }
    }

    public void autoFillFourAlliance() {
        semiFinal1Label.setText("Semi-Final 1");
        semiFinal2Label.setText("Semi-Final 2");
        quarter2Pane.setDisable(false);
        quarter3Pane.setDisable(true);
        quarter4Pane.setDisable(true);
        semiFinal1UnderdogLabel.setText("#4");
        semiFinal2UnderdogLabel.setText("#3");

        Team[] teams = Schedule.getInstance().getTeams();

        ComboBox[] boxes = {alliance1Team1, alliance1Team2,
            alliance2Team1, alliance2Team2,
            alliance7Team1, alliance7Team2,
            alliance8Team1, alliance8Team2
        };
        String[] teamNames = new String[teams.length];
        for (int i = 0;
                i < teams.length;
                i++) {
            teamNames[i] = teams[i].getName();
        }
        Arrays.sort(teamNames);
        for (ComboBox box : boxes) {
            box.getItems().addAll((Object[]) teamNames);
        }
        Team[] rankedTeams = Rankings.getInstance().getRankedTeams();
        for (int i = 0;
                i < 4; i++) {
            boxes[i * 2].getSelectionModel().select((Object) rankedTeams[i].getName());
            boxes[i * 2 + 1].getSelectionModel().select((Object) rankedTeams[i].getName());
        }
    }

    public void autoFillEightAlliance() {
        semiFinal1Label.setText("Quarter-Final 1");
        semiFinal2Label.setText("Quarter-Final 2");
        quarter2Pane.setDisable(false);
        quarter3Pane.setDisable(false);
        quarter4Pane.setDisable(false);
        semiFinal1UnderdogLabel.setText("#8");
        semiFinal2UnderdogLabel.setText("#7");

        Team[] teams = Schedule.getInstance().getTeams();

        ComboBox[] boxes = {alliance1Team1, alliance1Team2,
            alliance2Team1, alliance2Team2,
            alliance3Team1, alliance3Team2,
            alliance4Team1, alliance4Team2,
            alliance5Team1, alliance5Team2,
            alliance6Team1, alliance6Team2,
            alliance7Team1, alliance7Team2,
            alliance8Team1, alliance8Team2
        };
        String[] teamNames = new String[teams.length];
        for (int i = 0;
                i < teams.length;
                i++) {
            teamNames[i] = teams[i].getName();
        }
        Arrays.sort(teamNames);
        for (ComboBox box : boxes) {
            box.getItems().addAll((Object[]) teamNames);
        }
        Team[] rankedTeams = Rankings.getInstance().getRankedTeams();
        for (int i = 0;
                i < 8; i++) {
            boxes[i * 2].getSelectionModel().select((Object) rankedTeams[i].getName());
            boxes[i * 2 + 1].getSelectionModel().select((Object) rankedTeams[i].getName());
        }
    }

    public void init(ControllerController controller) {
        this.controller = controller;

        Team[] teams = Schedule.getInstance().getTeams();

        if (teams.length < 16) {
            eightAllianceDouble.setSelected(false);
            eightAllianceDouble.setDisable(true);
            eightAllianceSingle.setDisable(true);

            if (teams.length < 8) {
                fourAllianceDouble.setDisable(true);
                fourAllianceSingle.setDisable(true);
            }
            return;
        }

        autoFillEightAlliance();
    }

    @FXML
    public void applyRound1Picks(ActionEvent event) {
        alliance1Team1.setDisable(true);
        alliance1Team2.setDisable(true);
        alliance2Team1.setDisable(true);
        alliance2Team2.setDisable(true);
        alliance3Team1.setDisable(true);
        alliance3Team2.setDisable(true);
        alliance4Team1.setDisable(true);
        alliance4Team2.setDisable(true);
        alliance5Team1.setDisable(true);
        alliance5Team2.setDisable(true);
        alliance6Team1.setDisable(true);
        alliance6Team2.setDisable(true);
        alliance7Team1.setDisable(true);
        alliance7Team2.setDisable(true);
        alliance8Team1.setDisable(true);
        alliance8Team2.setDisable(true);
        applyPicksButton.setDisable(true);
        Team unknown = new Team("???");
        if (eightAllianceSingle.isSelected()) {
            Match qf1 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 1,
                    Match.MatchType.QUARTERFINAL, 1, 4);
            Match qf2 = new Match(findTeam(alliance2Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance2Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team2.getSelectionModel().getSelectedItem()), 2,
                    Match.MatchType.QUARTERFINAL, 2, 4);
            Match qf3 = new Match(findTeam(alliance3Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance3Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance6Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance6Team2.getSelectionModel().getSelectedItem()), 3,
                    Match.MatchType.QUARTERFINAL, 3, 4);
            Match qf4 = new Match(findTeam(alliance4Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance4Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance5Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance5Team2.getSelectionModel().getSelectedItem()), 4,
                    Match.MatchType.QUARTERFINAL, 4, 4);
            Match sf1 = new Match(unknown, unknown, unknown, unknown, 5, Match.MatchType.SEMIFINAL, 1, 2);
            Match sf2 = new Match(unknown, unknown, unknown, unknown, 5, Match.MatchType.SEMIFINAL, 2, 2);
            Match f1 = new Match(unknown, unknown, unknown, unknown, 7, Match.MatchType.FINAL, 1, 1);
            controller.enableEliminationsMode(PlayoffType.EIGHT_ALLIANCE_SINGLE, qf1, qf2, qf3, qf4, sf1, sf2, f1);
        } else if (eightAllianceDouble.isSelected()) {
            Match qf1_1 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 1,
                    Match.MatchType.QUARTERFINAL, 1, 8);
            Match qf2_1 = new Match(findTeam(alliance2Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance2Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team2.getSelectionModel().getSelectedItem()), 2,
                    Match.MatchType.QUARTERFINAL, 2, 8);
            Match qf3_1 = new Match(findTeam(alliance3Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance3Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance6Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance6Team2.getSelectionModel().getSelectedItem()), 3,
                    Match.MatchType.QUARTERFINAL, 3, 8);
            Match qf4_1 = new Match(findTeam(alliance4Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance4Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance5Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance5Team2.getSelectionModel().getSelectedItem()), 4,
                    Match.MatchType.QUARTERFINAL, 4, 8);
            Match qf1_2 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 5,
                    Match.MatchType.QUARTERFINAL, 5, 8);
            Match qf2_2 = new Match(findTeam(alliance2Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance2Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team2.getSelectionModel().getSelectedItem()), 6,
                    Match.MatchType.QUARTERFINAL, 6, 8);
            Match qf3_2 = new Match(findTeam(alliance3Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance3Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance6Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance6Team2.getSelectionModel().getSelectedItem()), 7,
                    Match.MatchType.QUARTERFINAL, 7, 8);
            Match qf4_2 = new Match(findTeam(alliance4Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance4Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance5Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance5Team2.getSelectionModel().getSelectedItem()), 8,
                    Match.MatchType.QUARTERFINAL, 8, 8);
            Match qf_tb_1 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.QUARTERFINAL, 1, -1);
            Match qf_tb_2 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.QUARTERFINAL, 2, -1);
            Match qf_tb_3 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.QUARTERFINAL, 3, -1);
            Match qf_tb_4 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.QUARTERFINAL, 4, -1);
            qf_tb_1.setIsTieBreaker(true);
            qf_tb_2.setIsTieBreaker(true);
            qf_tb_3.setIsTieBreaker(true);
            qf_tb_4.setIsTieBreaker(true);
            Match sf1_1 = new Match(unknown, unknown, unknown, unknown, 9, Match.MatchType.SEMIFINAL, 1, 4);
            Match sf2_1 = new Match(unknown, unknown, unknown, unknown, 9, Match.MatchType.SEMIFINAL, 2, 4);
            Match sf1_2 = new Match(unknown, unknown, unknown, unknown, 9, Match.MatchType.SEMIFINAL, 3, 4);
            Match sf2_2 = new Match(unknown, unknown, unknown, unknown, 9, Match.MatchType.SEMIFINAL, 4, 4);
            Match sf_tb_1 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.SEMIFINAL, 1, -1);
            Match sf_tb_2 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.SEMIFINAL, 2, -1);
            sf_tb_1.setIsTieBreaker(true);
            sf_tb_2.setIsTieBreaker(true);
            Match f1_1 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.FINAL, 1, 2);
            Match f1_2 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.FINAL, 2, 2);
            Match f_tb_1 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.FINAL, 1, -1);
            f_tb_1.setIsTieBreaker(true);
            controller.enableEliminationsMode(PlayoffType.EIGHT_ALLIANCE_DOUBLE, qf1_1, qf2_1, qf3_1, qf4_1, qf1_2, qf2_2, qf3_2, qf4_2,
                    qf_tb_1, qf_tb_2, qf_tb_3, qf_tb_4, sf1_1, sf2_1, sf1_2, sf2_2, sf_tb_1, sf_tb_2, f1_1, f1_2, f_tb_1);
        } else if (fourAllianceSingle.isSelected()) {
            Match sf1 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 1,
                    Match.MatchType.SEMIFINAL, 1, 2);
            Match sf2 = new Match(findTeam(alliance2Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance2Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team2.getSelectionModel().getSelectedItem()), 2,
                    Match.MatchType.SEMIFINAL, 2, 2);
            Match f1 = new Match(unknown, unknown, unknown, unknown, 3, Match.MatchType.FINAL, 1, 1);
            controller.enableEliminationsMode(PlayoffType.FOUR_ALLIANCE_SINGLE, sf1, sf2, f1);
        } else if (fourAllianceDouble.isSelected()) {
            Match sf1_1 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 1,
                    Match.MatchType.SEMIFINAL, 1, 4);
            Match sf2_1 = new Match(findTeam(alliance2Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance2Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team2.getSelectionModel().getSelectedItem()), 2,
                    Match.MatchType.SEMIFINAL, 2, 4);
            Match sf1_2 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 1,
                    Match.MatchType.SEMIFINAL, 3, 4);
            Match sf2_2 = new Match(findTeam(alliance2Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance2Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance7Team2.getSelectionModel().getSelectedItem()), 2,
                    Match.MatchType.SEMIFINAL, 4, 4);
            Match sf_tb_1 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.SEMIFINAL, 1, -1);
            Match sf_tb_2 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.SEMIFINAL, 2, -1);
            sf_tb_1.setIsTieBreaker(true);
            sf_tb_2.setIsTieBreaker(true);
            Match f1_1 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.FINAL, 1, 2);
            Match f1_2 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.FINAL, 2, 2);
            Match f_tb_1 = new Match(unknown, unknown, unknown, unknown, 0, Match.MatchType.FINAL, 1, -1);
            f_tb_1.setIsTieBreaker(true);
            controller.enableEliminationsMode(PlayoffType.FOUR_ALLIANCE_DOUBLE, sf1_1, sf2_1, sf1_2, sf2_2, sf_tb_1, sf_tb_2, f1_1, f1_2, f_tb_1);
        } else if (twoAllianceSingle.isSelected()) {
            Match f1 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 1,
                    Match.MatchType.FINAL, 1, 1);
            controller.enableEliminationsMode(PlayoffType.TWO_ALLIANCE_SINGLE, f1);
        } else if (twoAllianceDouble.isSelected()) {
            Match f1 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 1,
                    Match.MatchType.FINAL, 1, 3);
            Match f2 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 2,
                    Match.MatchType.FINAL, 2, 3);
            Match f3 = new Match(findTeam(alliance1Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance1Team2.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team1.getSelectionModel().getSelectedItem()),
                    findTeam(alliance8Team2.getSelectionModel().getSelectedItem()), 3,
                    Match.MatchType.FINAL, 3, 3);
            controller.enableEliminationsMode(PlayoffType.TWO_ALLIANCE_DOUBLE, f1, f2, f3);
        }
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
