package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Schedule.Match;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class MatchesOverviewController implements Initializable {

    @FXML
    private TableView<Match> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void init() {
        TableColumn<Match, String> matchNumCol = new TableColumn<>("Match");
        TableColumn<Match, String> red1Col = new TableColumn<>("Red 1");
        TableColumn<Match, String> red2Col = new TableColumn<>("Red 2");
        TableColumn<Match, String> blue1Col = new TableColumn<>("Blue 1");
        TableColumn<Match, String> blue2Col = new TableColumn<>("Blue 2");
        TableColumn<Match, String> redScore = new TableColumn<>("Red Score");
        TableColumn<Match, String> blueScore = new TableColumn<>("Blue Score");
        
        refresh();
        
        matchNumCol.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getNumber())));
        red1Col.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRed1().getName()));
        red2Col.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRed2().getName()));
        blue1Col.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getBlue1().getName()));
        blue2Col.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getBlue2().getName()));
        redScore.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().isScored() ? String.valueOf(c.getValue().getRedScore()) : "--"));
        blueScore.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().isScored() ? String.valueOf(c.getValue().getBlueScore()) : "--"));

        table.getColumns().addAll(matchNumCol, red1Col, red2Col, blue1Col, blue2Col, redScore, blueScore);
    }
    
    public void refresh() {
        table.getItems().clear();
        table.getItems().addAll(Schedule.getInstance().getMatches());
    }

}
