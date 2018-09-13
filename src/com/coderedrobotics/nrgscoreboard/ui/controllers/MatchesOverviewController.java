package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Match;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class MatchesOverviewController implements Initializable {

    private Consumer<Match> callback;

    @FXML
    private TableView<Match> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void init() {
        TableColumn<Match, Number> matchNumCol = new TableColumn<>("Match");
        TableColumn<Match, String> red1Col = new TableColumn<>("Red 1");
        TableColumn<Match, String> red2Col = new TableColumn<>("Red 2");
        TableColumn<Match, String> blue1Col = new TableColumn<>("Blue 1");
        TableColumn<Match, String> blue2Col = new TableColumn<>("Blue 2");
        TableColumn<Match, Number> redScore = new TableColumn<>("Red Score");
        TableColumn<Match, Number> blueScore = new TableColumn<>("Blue Score");
        TableColumn<Match, Number> redPenalty = new TableColumn<>("Red Penalty");
        TableColumn<Match, Number> bluePenalty = new TableColumn<>("Blue Penalty");
        TableColumn<Match, Number> redPoints = new TableColumn<>("Red Points");
        TableColumn<Match, Number> bluePoints = new TableColumn<>("Blue Points");
        TableColumn<Match, Number> redRankingPoints = new TableColumn<>("Red RP");
        TableColumn<Match, Number> blueRankingPoints = new TableColumn<>("Blue RP");

        redPenalty.setEditable(true);
        bluePenalty.setEditable(true);
        redPoints.setEditable(true);
        bluePoints.setEditable(true);

        redPenalty.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        redPenalty.setOnEditCommit(event -> {
            Match match = ((Match) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            match.setRedPenalty(event.getNewValue().intValue());
            match.rescore();
            refresh();
            if (callback != null) {
                callback.accept(match);
            }
        });

        bluePenalty.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        bluePenalty.setOnEditCommit(event -> {
            Match match = ((Match) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            match.setBluePenalty(event.getNewValue().intValue());
            match.rescore();
            refresh();
            if (callback != null) {
                callback.accept(match);
            }
        });

        redPoints.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        redPoints.setOnEditCommit(event -> {
            Match match = ((Match) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            match.setRedPoints(event.getNewValue().intValue());
            match.rescore();
            refresh();
            if (callback != null) {
                callback.accept(match);
            }
        });

        bluePoints.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        bluePoints.setOnEditCommit(event -> {
            Match match = ((Match) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            match.setBluePoints(event.getNewValue().intValue());
            match.rescore();
            refresh();
            if (callback != null) {
                callback.accept(match);
            }
        });

        redRankingPoints.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        redRankingPoints.setOnEditCommit(event -> {
            Match match = ((Match) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            match.setRedRankingPoints(event.getNewValue().intValue());
            match.rescore();
            refresh();
            if (callback != null) {
                callback.accept(match);
            }
        });

        blueRankingPoints.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        blueRankingPoints.setOnEditCommit(event -> {
            Match match = ((Match) event.getTableView().getItems().get(event.getTablePosition().getRow()));
            match.setBlueRankingPoints(event.getNewValue().intValue());
            match.rescore();
            refresh();
            if (callback != null) {
                callback.accept(match);
            }
        });

        matchNumCol.setStyle("-fx-font-weight: bold;");
        redScore.setStyle("-fx-font-weight: bold;");
        blueScore.setStyle("-fx-font-weight: bold;");

        red1Col.setSortable(false);
        red2Col.setSortable(false);
        blue1Col.setSortable(false);
        blue2Col.setSortable(false);

        refresh();

        matchNumCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getNumber()));
        red1Col.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRed1().getName()));
        red2Col.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRed2().getName()));
        blue1Col.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBlue1().getName()));
        blue2Col.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBlue2().getName()));
        redScore.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRedScore()));
        blueScore.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBlueScore()));
        redPenalty.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRedPenalty()));
        bluePenalty.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBluePenalty()));
        redPoints.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRedPoints()));
        bluePoints.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBluePoints()));
        redRankingPoints.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getRedRankingPoints()));
        blueRankingPoints.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getBlueRankingPoints()));

        table.getColumns().addAll(matchNumCol, red1Col, red2Col, blue1Col, blue2Col,
                redScore, blueScore, redPenalty, bluePenalty, redPoints, bluePoints,
                redRankingPoints, blueRankingPoints);
    }

    public void setEditCallback(Consumer<Match> callback) {
        this.callback = callback;
    }

    public void refresh() {
        table.getItems().clear();
        table.getItems().addAll(Schedule.getInstance().getMatches());
        if (Schedule.getInstance().getPlayoffMatches() != null) {
            table.getItems().addAll(Schedule.getInstance().getPlayoffMatches());
        }
    }

}
