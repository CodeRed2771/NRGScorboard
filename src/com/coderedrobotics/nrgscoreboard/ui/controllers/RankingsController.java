package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Team;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Michael
 */
public class RankingsController implements Initializable {
    @FXML
    private TableView<Team> table;

    private TableColumn<Team, Number> rankCol;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init() {
        TableColumn<Team, Number> rankCol = new TableColumn<>("Displayed Rank");
        TableColumn<Team, String> teamCol = new TableColumn<>("Team");
        TableColumn<Team, Number> numPlayedCol = new TableColumn<>("Number Played");
        TableColumn<Team, Number> winsCol = new TableColumn<>("Wins");
        TableColumn<Team, Number> lossesCol = new TableColumn<>("Losses");
        TableColumn<Team, Number> tiesCol = new TableColumn<>("Ties");
        TableColumn<Team, Number> totalRPCol = new TableColumn<>("Total RP");
        TableColumn<Team, Number> avgRPCol = new TableColumn<>("Avg. RP");
        TableColumn<Team, Number> totalScoreCol = new TableColumn<>("Total Score");
        TableColumn<Team, Number> avgScoreCol = new TableColumn<>("Avg. Score");
        TableColumn<Team, Number> penaltyCol = new TableColumn<>("Alliance Penalty");
        TableColumn<Team, Number> pointsForCol = new TableColumn<>("Alliance Points");
        
        rankCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getRank()));
        teamCol.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getName()));
        numPlayedCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getNumberMatchesPlayed()));
        winsCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getWins()));
        lossesCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getLosses()));
        tiesCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getTies()));
        totalRPCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getTotalRankingPoints()));
        avgRPCol.setCellValueFactory(c-> new SimpleDoubleProperty(c.getValue().getAverageRankingPoints()));
        totalScoreCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getTotalScore()));
        avgScoreCol.setCellValueFactory(c-> new SimpleDoubleProperty(c.getValue().getAverageMatchScore()));
        penaltyCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getTotalPenaltyPoints()));
        pointsForCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getTotalAlliancePoints()));

        table.getColumns().addAll(rankCol, teamCol, numPlayedCol, winsCol, lossesCol, 
                tiesCol, totalRPCol, avgRPCol, totalScoreCol, avgScoreCol, penaltyCol, pointsForCol);
        
        this.rankCol = rankCol;
        
        refresh();
    }
    
    public void refresh() {
        table.getItems().clear();
        table.getItems().addAll(Schedule.getInstance().getTeams());
        
        if (rankCol == null || table.getColumns().isEmpty()) {
            return;
        }
        
        table.getColumns().get(0).setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().setAll(rankCol);
    }

}
