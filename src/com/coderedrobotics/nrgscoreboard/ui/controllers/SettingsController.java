package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Settings;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class SettingsController implements Initializable {

    private ObservableList aspectRatio1Items;
    private ObservableList aspectRatio2Items;
    private ObservableList aspectRatio3Items;
    private ObservableList aspectRatio4Items;

    private Stage projectorStage = null;
    private Runnable setupProjectorCallback = null;

    @FXML
    private TextField matchLengthField;

    @FXML
    private TextField endGameLengthField;

    @FXML
    private CheckBox enableSounds;

    @FXML
    private CheckBox enableEndGame;

    @FXML
    private ComboBox projectorLocationPicker;

    @FXML
    private ComboBox aspectRatioPicker;

    @FXML
    private ComboBox displayResolutionPicker;

    @FXML
    private CheckBox windowedModeOption;

    @FXML
    private CheckBox enableAutonomous;

    @FXML
    private TextField autonomousLengthField;

    @FXML
    private RadioButton embeddedBrokerOption;

    @FXML
    private RadioButton externalBrokerOption;
    
    @FXML
    private TextField brokerLocation;

    @FXML
    private CheckBox enableRankingPoints;

    @FXML
    private ComboBox rankTeamsBy;

    @FXML
    private ComboBox rankTiebreaker1;

    @FXML
    private ComboBox rankTiebreaker2;

    @FXML
    private ComboBox rankTiebreaker3;

    @FXML
    private ComboBox rankTiebreaker4;

    public SettingsController() {
        aspectRatio1Items = FXCollections.observableArrayList();
        aspectRatio1Items.add("640 x 480");
        aspectRatio1Items.add("800 x 600");
        aspectRatio1Items.add("1024 x 768");
        aspectRatio1Items.add("1152 x 864");
        aspectRatio1Items.add("1600 x 1200");
        aspectRatio2Items = FXCollections.observableArrayList();
        aspectRatio2Items.add("1280 x 720");
        aspectRatio2Items.add("1600 x 900");
        aspectRatio2Items.add("1920 x 1080");
        aspectRatio2Items.add("2560 x 1440");
        aspectRatio3Items = FXCollections.observableArrayList();
        aspectRatio3Items.add("1280 x 800");
        aspectRatio3Items.add("1440 x 900");
        aspectRatio3Items.add("1680 x 1050");
        aspectRatio3Items.add("1920 x 1200");
        aspectRatio3Items.add("2560 x 1600");
        aspectRatio4Items = FXCollections.observableArrayList();
        aspectRatio4Items.add("1280 x 768");
        aspectRatio4Items.add("1280 x 1024");
        aspectRatio4Items.add("1360 x 768");
        aspectRatio4Items.add("1366 x 768");
        aspectRatio4Items.add("1024 x 600");
    }

    public void setSetupProjectorCallback(Runnable runnable) {
        setupProjectorCallback = runnable;
    }

    public void setProjectorStage(Stage stage) {
        projectorStage = stage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        windowedModeOption.setSelected(Settings.windowed);
        matchLengthField.setText(String.valueOf(Settings.matchLength));
        endGameLengthField.setText(String.valueOf(Settings.endGameDuration));
        brokerLocation.setText(Settings.mqttBrokerLocation);
        enableSounds.setSelected(Settings.soundEnabled);
        enableEndGame.setSelected(Settings.endGameEnabled);
        enableAutonomous.setSelected(Settings.autonomousEnabled);
        autonomousLengthField.setText(String.valueOf(Settings.autonomousDuration));
        embeddedBrokerOption.setSelected(Settings.useEmbeddedMqttBroker);
        externalBrokerOption.setSelected(!Settings.useEmbeddedMqttBroker);
        enableRankingPoints.setSelected(Settings.enableRankingPoints);
        rankTeamsBy.getSelectionModel().select(Settings.rankTeamsBy.ordinal());
        rankTiebreaker1.getSelectionModel().select(Settings.rankTiebreaker1.ordinal());
        rankTiebreaker2.getSelectionModel().select(Settings.rankTiebreaker2.ordinal());
        rankTiebreaker3.getSelectionModel().select(Settings.rankTiebreaker3.ordinal());
        rankTiebreaker4.getSelectionModel().select(Settings.rankTiebreaker4.ordinal());

        matchLengthField.textProperty().addListener((e) -> {
            try {
                Settings.matchLength = Integer.parseInt(matchLengthField.textProperty().get());
            } catch (NumberFormatException ex) {
                Settings.matchLength = 135;
            }
            Settings.writeFile();
        });

        endGameLengthField.textProperty().addListener((e) -> {
            try {
                Settings.endGameDuration = Integer.parseInt(endGameLengthField.textProperty().get());
            } catch (NumberFormatException ex) {
                Settings.endGameDuration = 30;
            }
            Settings.writeFile();
        });

        enableSounds.selectedProperty().addListener((e) -> {
            Settings.soundEnabled = enableSounds.isSelected();
            Settings.writeFile();
        });

        enableEndGame.selectedProperty().addListener((e) -> {
            Settings.endGameEnabled = enableEndGame.isSelected();
            Settings.writeFile();
        });

        enableAutonomous.selectedProperty().addListener((e) -> {
            Settings.autonomousEnabled = enableAutonomous.isSelected();
            Settings.writeFile();
        });

        autonomousLengthField.textProperty().addListener((e) -> {
            try {
                Settings.autonomousDuration = Integer.parseInt(autonomousLengthField.textProperty().get());
            } catch (NumberFormatException ex) {
                Settings.autonomousDuration = 0;
            }
            Settings.writeFile();
        });

        embeddedBrokerOption.selectedProperty().addListener((e) -> {
            Settings.useEmbeddedMqttBroker = embeddedBrokerOption.selectedProperty().get();
            Settings.writeFile();
        });
        
        brokerLocation.textProperty().addListener((e) -> {
            Settings.mqttBrokerLocation = brokerLocation.textProperty().get();
            Settings.writeFile();
        });

        enableRankingPoints.selectedProperty().addListener((e) -> {
            Settings.enableRankingPoints = enableRankingPoints.selectedProperty().get();
            rankTiebreaker4.getSelectionModel().select(0);
            rankTiebreaker4.setDisable(!Settings.enableRankingPoints);
            
            Settings.writeFile();
        });

        rankTeamsBy.valueProperty().addListener((sender, oldItem, newItem) -> {
            rankListener(rankTeamsBy, oldItem, newItem);
        });

        rankTiebreaker1.valueProperty().addListener((sender, oldItem, newItem) -> {
            rankListener(rankTiebreaker1, oldItem, newItem);
        });

        rankTiebreaker2.valueProperty().addListener((sender, oldItem, newItem) -> {
            rankListener(rankTiebreaker2, oldItem, newItem);
        });

        rankTiebreaker3.valueProperty().addListener((sender, oldItem, newItem) -> {
            rankListener(rankTiebreaker3, oldItem, newItem);
        });

        rankTiebreaker4.valueProperty().addListener((sender, oldItem, newItem) -> {
            rankListener(rankTiebreaker4, oldItem, newItem);
        });
    }

    private void rankListener(ComboBox comboBox, Object oldItem, Object newItem) {
        ComboBox[] boxes = {rankTeamsBy, rankTiebreaker1, rankTiebreaker2, rankTiebreaker3, rankTiebreaker4};
        for (ComboBox box : boxes) {
            if (box == comboBox) {
                continue;
            }
            if (comboBox.getSelectionModel().getSelectedIndex() == box.getSelectionModel().getSelectedIndex()) {
                int index = 0;
                for (Object o : comboBox.getItems()) {
                    if (o == oldItem) {
                        break;
                    }
                    index++;
                }
                box.getSelectionModel().select(index);
            }
        }

        Settings.writeFile();
    }

    @FXML
    private void projectorLocationAction(ActionEvent event) {
        int selection = projectorLocationPicker.getSelectionModel().getSelectedIndex();
        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = g.getScreenDevices();
        Settings.projectorLocationIndex = projectorLocationPicker.getSelectionModel().getSelectedIndex();
        int width;
        switch (selection) {
            case 0:
            case 1:
                projectorStage.setX(0);
                break;
            case 2:
                width = devices[1].getDisplayMode().getWidth();
                projectorStage.setX(-width);
                break;
            case 3:
                width = devices[0].getDisplayMode().getWidth();
                projectorStage.setX(width);
                break;
        }
        Settings.writeFile();
    }

    @FXML
    private void toggleWindowedMode(ActionEvent event) {
        Settings.windowed = !Settings.windowed;
        if (setupProjectorCallback != null) {
            setupProjectorCallback.run();
        }
        Settings.writeFile();
    }

    @FXML
    private void aspectRatioChanged(ActionEvent event) {
        int selection = aspectRatioPicker.getSelectionModel().getSelectedIndex();
        switch (selection) {
            case 0:
                displayResolutionPicker.setItems(aspectRatio1Items);
                break;
            case 1:
                displayResolutionPicker.setItems(aspectRatio2Items);
                break;
            case 2:
                displayResolutionPicker.setItems(aspectRatio3Items);
                break;
            default:
                displayResolutionPicker.setItems(aspectRatio4Items);
                break;
        }
        Settings.writeFile();
    }

    @FXML
    private void projectorResolutionChanged(ActionEvent event) {
        String resolution = ((ObservableList<String>) displayResolutionPicker.getItems()).get(displayResolutionPicker.getSelectionModel().getSelectedIndex());
        String[] items = resolution.split("x");
        items[0] = items[0].trim();
        items[1] = items[1].trim();
        Settings.width = Integer.parseInt(items[0]);
        Settings.height = Integer.parseInt(items[1]);
        if (setupProjectorCallback != null) {
            setupProjectorCallback.run();
        }
        Settings.writeFile();
    }
}
