package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Main;
import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.util.MqttConnection;
import com.coderedrobotics.nrgscoreboard.Rankings;
import com.coderedrobotics.nrgscoreboard.Settings;
import com.coderedrobotics.nrgscoreboard.ui.controllers.helpers.EliminationsAdvancer;
import com.coderedrobotics.nrgscoreboard.ui.controllers.helpers.IndicatorColorManager;
import com.coderedrobotics.nrgscoreboard.ui.controllers.helpers.ScheduleLoader;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class ControllerController implements Initializable {

    private MatchController matchController;
    private ScoreController scoreController;
    private PreMatchController preMatchController;
    private PlayoffsControllerController playoffsController;
    private GenerateMatchesController generateMatchesController;
    private MatchesOverviewController matchesOverviewController;
    private EditNamesController editNamesController;
    private RankingsController rankingsController;
    private AddLateCompetitorController addLateCompetitorController;
    private AddMoreMatchesController addMoreMatchesController;
    private SettingsController settingsController;
    private int match = 0;
    private Parent matchLayoutRoot, logoLayoutRoot, scoreLayoutRoot, preMatchLayoutRoot,
            generateMatchesLayoutRoot, matchesOverviewLayoutRoot, editNamesLayoutRoot,
            rankingsLayoutRoot, addLateCompetitorLayoutRoot, settingsLayoutRoot,
            fieldControlsLayoutRoot, enterScoresLayoutRoot, addMoreMatchesLayoutRoot;
    private Parent playoffsControllerLayoutRoot;
    private Parent currentLayoutRoot;
    private Parent previousLayoutRoot;
    private Stage projectorStage;
    private boolean inEliminations;
    private Stack<Integer> previousEliminationMatchHistory;
    private PlayoffsControllerController.PlayoffType playOffType;
    private Stage playoffsStage = null;
    private Stage generateMatchScheduleStage = null;
    private Stage matchesOverviewStage = null;
    private Stage editCompetitorNamesStage = null;
    private Stage rankingsStage = null;
    private Stage addLateCompetitorStage = null;
    private Stage settingsStage = null;
    private Stage fieldControlsStage = null;
    private Stage enterScoresStage = null;
    private Stage addMoreMatchesStage = null;

    private ScheduleLoader scheduleLoader;
    private EliminationsAdvancer eliminationsAdvancer;

    private Match[] matches;

    @FXML
    private Label currentMatchLabel;

    @FXML
    private Label redScore;

    @FXML
    private Label blueScore;

    @FXML
    private Label redPenaltyPoints;

    @FXML
    private Label bluePenaltyPoints;

    @FXML
    private Label redRP;

    @FXML
    private Label blueRP;

    @FXML
    private Button startMatchButton;

    @FXML
    private Button abortMatchButton;

    @FXML
    private Button manualScoringButton;

    @FXML
    private Button viewMatchScheduleButton;

    @FXML
    private Button previousMatchButton;

    @FXML
    private Button nextMatchButton;

    @FXML
    private Button competitionReportButton;

    @FXML
    private Button eliminationSelectionButton;

    @FXML
    private RadioButton preMatchNextDisplayOption;

    @FXML
    private RadioButton preMatchCurrentDisplayOption;

    @FXML
    private RadioButton matchDetailsDisplayOption;

    @FXML
    private RadioButton matchScoreDisplayOption;

    @FXML
    private RadioButton logoOnlyDisplayOption;

    @FXML
    private MenuItem viewScheduleMenuItem;

    @FXML
    private MenuItem generateMatchScheduleMenuItem;

    @FXML
    private MenuItem loadScheduleFromCSVMenuItem;

    @FXML
    private MenuItem loadFromCompetitonBackupMenuItem;

    @FXML
    private MenuItem addMoreMatchesMenuItem;

    @FXML
    private MenuItem editTeamNamesMenuItem;

    @FXML
    private MenuItem addLateCompetitorMenuItem;

    @FXML
    private MenuItem removeCompetitorMenuItem;

    @FXML
    private Label matchClock;

    @FXML
    private RadioButton field1;

    @FXML
    private RadioButton field2;

    @FXML
    private Label red1Label;

    @FXML
    private Label red2Label;

    @FXML
    private Label blue1Label;

    @FXML
    private Label blue2Label;

    @FXML
    private Circle field1FCSIndicator;

    @FXML
    private Circle field1Red1Indicator;

    @FXML
    private Circle field1Red2Indicator;

    @FXML
    private Circle field1Blue1Indicator;

    @FXML
    private Circle field1Blue2Indicator;

    @FXML
    private Circle field2FCSIndicator;

    @FXML
    private Circle field2Red1Indicator;

    @FXML
    private Circle field2Red2Indicator;

    @FXML
    private Circle field2Blue1Indicator;

    @FXML
    private Circle field2Blue2Indicator;

    public ControllerController() {
        scheduleLoader = new ScheduleLoader();
        eliminationsAdvancer = new EliminationsAdvancer();
        previousEliminationMatchHistory = new Stack<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IndicatorColorManager colorManager = new IndicatorColorManager();
        MqttConnection.getInstance().setColorManager(colorManager);

        field1FCSIndicator.fillProperty().bind(colorManager.field1FCS);
        field1Red1Indicator.fillProperty().bind(colorManager.field1Red1);
        field1Red2Indicator.fillProperty().bind(colorManager.field1Red2);
        field1Blue1Indicator.fillProperty().bind(colorManager.field1Blue1);
        field1Blue2Indicator.fillProperty().bind(colorManager.field1Blue2);
        field2FCSIndicator.fillProperty().bind(colorManager.field2FCS);
        field2Red1Indicator.fillProperty().bind(colorManager.field2Red1);
        field2Red2Indicator.fillProperty().bind(colorManager.field2Red2);
        field2Blue1Indicator.fillProperty().bind(colorManager.field2Blue1);
        field2Blue2Indicator.fillProperty().bind(colorManager.field2Blue2);
    }

    public void init() throws IOException {
        FXMLLoader playoffsLoader = new FXMLLoader();
        playoffsLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/PlayoffsController.fxml"));
        playoffsControllerLayoutRoot = (Parent) playoffsLoader.load();
        playoffsController = playoffsLoader.getController();

        FXMLLoader matchLayoutLoader = new FXMLLoader();
        matchLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/Match.fxml"));
        matchLayoutRoot = (Parent) matchLayoutLoader.load();
        matchController = matchLayoutLoader.getController();

        FXMLLoader logoLayoutLoader = new FXMLLoader();
        logoLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/Logo.fxml"));
        logoLayoutRoot = (Parent) logoLayoutLoader.load();

        FXMLLoader scoreLayoutLoader = new FXMLLoader();
        scoreLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/Score.fxml"));
        scoreLayoutRoot = (Parent) scoreLayoutLoader.load();
        scoreController = scoreLayoutLoader.getController();

        FXMLLoader preMatchLayoutLoader = new FXMLLoader();
        preMatchLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/PreMatch.fxml"));
        preMatchLayoutRoot = (Parent) preMatchLayoutLoader.load();
        preMatchController = preMatchLayoutLoader.getController();

        FXMLLoader generateMatchesLayoutLoader = new FXMLLoader();
        generateMatchesLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/GenerateMatches.fxml"));
        generateMatchesLayoutRoot = (Parent) generateMatchesLayoutLoader.load();
        generateMatchesController = generateMatchesLayoutLoader.getController();

        FXMLLoader matchesOverviewLayoutLoader = new FXMLLoader();
        matchesOverviewLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/MatchesOverview.fxml"));
        matchesOverviewLayoutRoot = (Parent) matchesOverviewLayoutLoader.load();
        matchesOverviewController = matchesOverviewLayoutLoader.getController();

        FXMLLoader editNamesLayoutLoader = new FXMLLoader();
        editNamesLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/EditNames.fxml"));
        editNamesLayoutRoot = (Parent) editNamesLayoutLoader.load();
        editNamesController = editNamesLayoutLoader.getController();

        FXMLLoader rankingsLayoutLoader = new FXMLLoader();
        rankingsLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/Rankings.fxml"));
        rankingsLayoutRoot = (Parent) rankingsLayoutLoader.load();
        rankingsController = rankingsLayoutLoader.getController();

        FXMLLoader addNewCompetitorLayoutLoader = new FXMLLoader();
        addNewCompetitorLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/AddLateCompetitor.fxml"));
        addLateCompetitorLayoutRoot = (Parent) addNewCompetitorLayoutLoader.load();
        addLateCompetitorController = addNewCompetitorLayoutLoader.getController();

        FXMLLoader settingsLayoutLoader = new FXMLLoader();
        settingsLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/Settings.fxml"));
        settingsLayoutRoot = (Parent) settingsLayoutLoader.load();
        settingsController = settingsLayoutLoader.getController();
        settingsController.setSetupProjectorCallback(this::setupProjectorStage);

        FXMLLoader fieldControlsLayoutLoader = new FXMLLoader();
        fieldControlsLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/FieldControls.fxml"));
        fieldControlsLayoutRoot = (Parent) fieldControlsLayoutLoader.load();

        FXMLLoader enterScoresLayoutLoader = new FXMLLoader();
        enterScoresLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/EnterScores.fxml"));
        enterScoresLayoutRoot = (Parent) enterScoresLayoutLoader.load();
        EnterScoresController enterScoresController = enterScoresLayoutLoader.getController();
        enterScoresController.setSaveCallback(this::saveScores);
        
        FXMLLoader addMoreMatchesLayoutLoader = new FXMLLoader();
        addMoreMatchesLayoutLoader.setLocation(getClass().getResource("/com/coderedrobotics/nrgscoreboard/ui/views/AddMoreMatches.fxml"));
        addMoreMatchesLayoutRoot = (Parent) addMoreMatchesLayoutLoader.load();
        addMoreMatchesController = addMoreMatchesLayoutLoader.getController();

        setupProjectorStage();
        matchController.setControlDisplayTimeLabel(matchClock);
        matchController.setMatchCompleteCallback(this::matchComplete);
    }

    private void setupProjectorStage() {
        if (projectorStage != null) {
            projectorStage.hide();
            projectorStage = null;
        }

        AnchorPane background = new AnchorPane();
        background.setStyle("-fx-background-color: #333;");

        double scaleFactor = Math.min(Settings.height / 768d, Settings.width / 1024d);
        matchLayoutRoot.setScaleY(scaleFactor);
        matchLayoutRoot.setScaleX(scaleFactor);
        logoLayoutRoot.setScaleY(scaleFactor);
        logoLayoutRoot.setScaleX(scaleFactor);
        scoreLayoutRoot.setScaleY(scaleFactor);
        scoreLayoutRoot.setScaleX(scaleFactor);
        preMatchLayoutRoot.setScaleY(scaleFactor);
        preMatchLayoutRoot.setScaleX(scaleFactor);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #000");
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(matchLayoutRoot);
        stackPane.getChildren().add(logoLayoutRoot);
        stackPane.getChildren().add(scoreLayoutRoot);
        stackPane.getChildren().add(preMatchLayoutRoot);

        if (currentLayoutRoot == null) {
            currentLayoutRoot = logoLayoutRoot;
        }

        matchLayoutRoot.setOpacity(currentLayoutRoot == matchLayoutRoot ? 1 : 0);
        logoLayoutRoot.setOpacity(currentLayoutRoot == logoLayoutRoot ? 1 : 0);
        scoreLayoutRoot.setOpacity(currentLayoutRoot == scoreLayoutRoot ? 1 : 0);
        preMatchLayoutRoot.setOpacity(currentLayoutRoot == preMatchLayoutRoot ? 1 : 0);

        Scene scene = new Scene(stackPane, Settings.width, Settings.height);
        Stage stage = new Stage();

        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = g.getScreenDevices();
        stage.setWidth(Settings.width);
        stage.setHeight(Settings.height);
        if (Settings.windowed) {
            stage.initStyle(StageStyle.DECORATED);
        } else {
            int selection = Settings.projectorLocationIndex;
            int width;
            switch (selection) {
                case 0:
                case 1:
                    stage.setX(0);
                    break;
                case 2:
                    width = devices[1].getDisplayMode().getWidth();
                    stage.setX(-width);
                    break;
                case 3:
                    width = devices[0].getDisplayMode().getWidth();
                    stage.setX(width);
                    break;
            }
            stage.setY(0);
            stage.initStyle(StageStyle.UNDECORATED);
        }

        stage.setTitle("NRG Scoreboard");
        stage.setScene(scene);

        stage.show();
        projectorStage = stage;
        settingsController.setProjectorStage(projectorStage);
    }

    private void finishUpInitialization() {
        this.matches = Schedule.getInstance().getMatches();

        startMatchButton.setDisable(false);
//        abortMatchButton.setDisable(false);
        manualScoringButton.setDisable(false);
        previousMatchButton.setDisable(false);
        competitionReportButton.setDisable(false);
        nextMatchButton.setDisable(false);
        preMatchNextDisplayOption.setDisable(false);
        preMatchCurrentDisplayOption.setDisable(false);
        matchDetailsDisplayOption.setDisable(false);
        logoOnlyDisplayOption.setDisable(false);
        eliminationSelectionButton.setDisable(false);
        viewMatchScheduleButton.setDisable(false);
        viewScheduleMenuItem.setDisable(false);

        generateMatchScheduleMenuItem.setDisable(true);
        loadScheduleFromCSVMenuItem.setDisable(true);
        loadFromCompetitonBackupMenuItem.setDisable(true);
        addMoreMatchesMenuItem.setDisable(false);
        editTeamNamesMenuItem.setDisable(false);
        addLateCompetitorMenuItem.setDisable(false);
        removeCompetitorMenuItem.setDisable(false);

        setMatch(0);

        if (matches[match].isScored()) {
            matchScoreDisplayOption.setDisable(false);
        }
        
        if (generateMatchScheduleStage != null) {
            generateMatchScheduleStage.hide();
        }

        currentLayoutRoot = logoLayoutRoot;
        
        scheduleLoader.writeCompetitionBackup();
    }

    @FXML
    private void startMatch(ActionEvent event) {
        matchController.setField(field1.isSelected() ? 1 : 2);
        field1.setDisable(true);
        field2.setDisable(true);
        if (Settings.soundEnabled) {
            try {
                InputStream audioSrc = Main.class.getResourceAsStream("/Start Auto_normalized.wav");
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream start = AudioSystem.getAudioInputStream(bufferedIn);
                Clip c = AudioSystem.getClip();
                c.open(start);
                c.start();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | IllegalArgumentException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        startMatchButton.setDisable(true);
        abortMatchButton.setDisable(false);
        matchController.startTimer();
    }

    @FXML
    private void abortMatch(ActionEvent event) {
        if (Settings.soundEnabled) {
            try {
                InputStream audioSrc = Main.class.getResourceAsStream("/fog-blast.wav");
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream start = AudioSystem.getAudioInputStream(bufferedIn);
                Clip c = AudioSystem.getClip();
                c.open(start);
                c.start();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | IllegalArgumentException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        startMatchButton.setDisable(false);
        abortMatchButton.setDisable(true);
        field1.setDisable(false);
        field2.setDisable(false);
        matchController.stopMatch();
        matchClock.setText("0:00");
    }

    @FXML
    private void loadMatchSchedule(ActionEvent event) {
        if (scheduleLoader.loadMatchSchedule()) {
            finishUpInitialization();
        }
    }

    @FXML
    private void loadCompetitionBackup(ActionEvent event) {
        if (scheduleLoader.loadCompetitionBackup()) {
            finishUpInitialization();
            for (Match m : Schedule.getInstance().getMatches()) {
                if (m.isScored()) {
                    m.getRed1().addMatch(m);
                    m.getRed2().addMatch(m);
                    m.getBlue1().addMatch(m);
                    m.getBlue2().addMatch(m);
                }
            }
            Rankings.getInstance().rankTeams();
        }
    }

    @FXML
    private void nextMatch(ActionEvent event) {
        if (inEliminations) {
            int m = eliminationsAdvancer.getNextMatchAfter(playOffType, match);
            if (m == match) {
                return;
            }
            previousEliminationMatchHistory.push(match);
            match = m;
        } else {
            if (match == matches.length - 1) {
                return;
            }
            match++;
        }
        setMatch(match);
        field1.setSelected(!field1.isSelected());
        field2.setSelected(!field1.isSelected());
        matchController.setField(field1.isSelected() ? 1 : 2);
        if (preMatchNextDisplayOption.isSelected()) {
            preMatchCurrentDisplayOption.setSelected(true);
        }
    }

    @FXML
    private void previousMatch(ActionEvent event) {
        if (match == 0) {
            return;
        }
        if (inEliminations) {
            if (previousEliminationMatchHistory.empty()) {
                return;
            }
            match = previousEliminationMatchHistory.pop();
        } else {
            match--;
        }
        setMatch(match);
        field1.setSelected(!field1.isSelected());
        field2.setSelected(!field1.isSelected());
        matchController.setField(field1.isSelected() ? 1 : 2);
        if (preMatchCurrentDisplayOption.isSelected()) {
            preMatchNextDisplayOption.setSelected(true);
        }
    }

    @FXML
    private void preMatchCurrentDisplayOptionAction(ActionEvent event) {
        fadeLayout(preMatchLayoutRoot);
        preMatchController.updateTeamDisplays(matches[match]);
        preMatchController.startRankingDisplay();
    }

    @FXML
    private void preMatchNextDisplayOptionAction(ActionEvent event) {
        fadeLayout(preMatchLayoutRoot);
        preMatchController.updateTeamDisplays(matches[match + 1]);
        preMatchController.startRankingDisplay();
    }

    @FXML
    private void matchDetailsDisplayOptionAction(ActionEvent event) {
        fadeLayout(matchLayoutRoot);
        preMatchController.stopRankingDisplay();
    }

    @FXML
    private void matchScoreDisplayOptionAction(ActionEvent event) {
        fadeLayout(scoreLayoutRoot);
        preMatchController.stopRankingDisplay();
    }

    @FXML
    private void logoDisplayOptionAction(ActionEvent event) {
        fadeLayout(logoLayoutRoot);
        preMatchController.stopRankingDisplay();
    }

    @FXML
    private void manualScoring(ActionEvent event) {
        if (enterScoresStage != null) {
            enterScoresStage.show();
            enterScoresStage.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Enter Scores");
        stage.setScene(new Scene(enterScoresLayoutRoot));
        stage.show();
        enterScoresStage = stage;
    }

    private void saveScores(int red, int blue, int redPoints, int redPenalty, int bluePoints, int bluePenalty, int redRankingPoints, int blueRankingPoints) {
        Match m = matches[match];
        m.setScore(red, blue, redPoints, redPenalty, bluePoints, bluePenalty, redRankingPoints, blueRankingPoints);
        m.getRed1().addMatch(m);
        m.getRed2().addMatch(m);
        m.getBlue1().addMatch(m);
        m.getBlue2().addMatch(m);
        Rankings.getInstance().rankTeams();
        Platform.runLater(() -> {
            scoreController.updateDisplay(m);
            matchScoreDisplayOption.setDisable(false);

            redScore.setText(String.valueOf(m.getRedScore()));
            blueScore.setText(String.valueOf(m.getBlueScore()));
            redPenaltyPoints.setText(String.valueOf(m.getRedPenalty()) + " From Red Penalty");
            bluePenaltyPoints.setText(String.valueOf(m.getBluePenalty()) + " From Blue Penalty");
            redRP.setText("(" + String.valueOf(m.getRedRankingPoints()) + " RP)");
            blueRP.setText("(" + String.valueOf(m.getBlueRankingPoints()) + " RP)");
        });

        if (inEliminations) {
            matches = eliminationsAdvancer.handleEliminationAdvancement(matches, m, match, playOffType);
        }

        matchesOverviewController.refresh();
        rankingsController.refresh();
        scheduleLoader.writeCompetitionBackup();
    }

    @FXML
    private void competitionReport(ActionEvent event) {
        if (rankingsStage != null) {
            rankingsStage.show();
            rankingsStage.requestFocus();
            rankingsController.refresh();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Team Rankings");
        stage.setScene(new Scene(rankingsLayoutRoot));
        rankingsController.init();
        stage.show();
        rankingsStage = stage;
    }

    @FXML
    private void help(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Help");
        alert.setContentText("Loading Match Data:\n"
                + "Create a CSV file containing the match schedule with "
                + "filename \"schedule.csv\" in the same directory as the "
                + "display software .jar file (current directory). Each line "
                + "in the file should be a match, each match should have four "
                + "columns containing the four teams in this order: red1, red2, "
                + "blue1, blue2.\n\nAll buttons work for the current match number, "
                + "which can be found in the upper left corner of the control "
                + "window.\n\nScoring:\nEnter the match score in the red and "
                + "blue score fields and click \"Apply Scoring\". If you "
                + "enter the wrong score, reenter both scores and click "
                + "\"Apply Scoring\" again. After a score has been entered, "
                + "the \"Match Score\" option will become available. If you "
                + "use the previous match button to go back to a match that "
                + "has already been scored, that score will appear in the red "
                + "and blue score fields. You can still change that score at "
                + "any point by clicking \"Apply Scoring\".\n\nElimination "
                + "Instructions:\nClick \"Open Elimination Selection\". Choose "
                + "the eight competing teams. The ComboBoxes are prepopulated "
                + "with the top 8 ranked teams, and are layed out in the way "
                + "that matches are played. (Rank 1 & Rank 8 play together against "
                + "Rank 4 & Rank 5 in SF1, Rank 2 & Rank 7 play together "
                + "against Rank 3 & Rank 6 in SF2.) As soon as you have commited "
                + "the elimination alliance selections, the software will go into "
                + "elimination mode. NOTE: THIS IS NOT REVERSIBLE. The software "
                + "will automatically determine who will play in the final match "
                + "after the two semifinal matches have been scored. This final "
                + "match will automatically appear as \"Match 3\" in elimination "
                + "mode after matches 1 and 2 have been scored.");
        alert.setResizable(true);
        alert.show();
    }

    @FXML
    private void openEliminationSelectionScreen(ActionEvent event) {
        if (playoffsStage != null) {
            playoffsStage.show();
            playoffsStage.requestFocus();
            playoffsController.init(this);
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Elimination Team Selection");
        stage.setScene(new Scene(playoffsControllerLayoutRoot));
        playoffsController.init(this);
        stage.show();
        playoffsStage = stage;
    }

    void enableEliminationsMode(PlayoffsControllerController.PlayoffType playOffType, Match... matches) {
        this.matches = matches;
        inEliminations = true;
        this.playOffType = playOffType;
        setMatch(0);
        eliminationSelectionButton.setDisable(true);
        Schedule.getInstance().setPlayoffMatches(matches);
        previousEliminationMatchHistory.clear();
    }

    private void fadeLayout(Parent newLayout) {
        previousLayoutRoot = currentLayoutRoot;
        currentLayoutRoot = newLayout;
        Platform.runLater(() -> {
            try {
                previousLayoutRoot.setOpacity(1.0);
                currentLayoutRoot.setOpacity(0.0);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(0), new KeyValue(previousLayoutRoot.opacityProperty(), 1.0), new KeyValue(currentLayoutRoot.opacityProperty(), 0.0)),
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(previousLayoutRoot.opacityProperty(), 0.0), new KeyValue(currentLayoutRoot.opacityProperty(), 1.0))
                );

                timeline.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setMatch(int number) {
        match = number;
        Schedule.getInstance().setCurrentMatch(match);
        Match m = matches[match];
        Platform.runLater(() -> {
            matchScoreDisplayOption.setDisable(!m.isScored());
            redScore.setText(m.isScored() ? String.valueOf(m.getRedScore()) : "--");
            blueScore.setText(m.isScored() ? String.valueOf(m.getBlueScore()) : "--");
            redPenaltyPoints.setText((m.isScored() ? String.valueOf(m.getRedPenalty()) : "--") + " From Red Penalty");
            bluePenaltyPoints.setText((m.isScored() ? String.valueOf(m.getBluePenalty()) : "--") + " From Blue Penalty");
            redRP.setText("(" + (m.isScored() ? String.valueOf(m.getRedRankingPoints()) : "-") + " RP)");
            blueRP.setText("(" + (m.isScored() ? String.valueOf(m.getBlueRankingPoints()) : "-") + " RP)");
            red1Label.setText(m.getRed1().getName());
            red2Label.setText(m.getRed2().getName());
            blue1Label.setText(m.getBlue1().getName());
            blue2Label.setText(m.getBlue2().getName());
            if (m.isTieBreaker()) {
                if (null != m.getType()) {
                    switch (m.getType()) {
                        case QUARTERFINAL:
                            currentMatchLabel.setText("Current Match: QF " + m.getNumberInSeries() + " Tiebreaker");
                            break;
                        case SEMIFINAL:
                            currentMatchLabel.setText("Current Match: SF " + m.getNumberInSeries() + " Tiebreaker");
                            break;
                        case FINAL:
                            currentMatchLabel.setText("Current Match: Finals Tiebreaker");
                            break;
                        default:
                            break;
                    }
                }
            } else if (m.getType() == Match.MatchType.NORMAL) {
                currentMatchLabel.setText("Current Match: " + m.getNumber());
            } else if (m.getType() == Match.MatchType.QUARTERFINAL) {
                currentMatchLabel.setText("Current Match: Quarterfinal " + m.getNumberInSeries() + " of " + m.getTotalInSeries());
            } else if (m.getType() == Match.MatchType.SEMIFINAL) {
                currentMatchLabel.setText("Current Match: Semifinal " + m.getNumberInSeries() + " of " + m.getTotalInSeries());
            } else if (m.getType() == Match.MatchType.FINAL) {
                currentMatchLabel.setText("Current Match: Final " + m.getNumberInSeries() + " of " + m.getTotalInSeries());
            }
            matchController.updateTeamDisplays(m);
            if (preMatchNextDisplayOption.isSelected()) {
                preMatchController.updateTeamDisplays(matches[match + 1]);
            } else {
                preMatchController.updateTeamDisplays(matches[match]);
            }
            scoreController.updateDisplay(m);

            preMatchNextDisplayOption.setDisable(match == matches.length - 1);
        });
    }

    @FXML
    private void generateMatchSchedule(ActionEvent event) {
        if (generateMatchScheduleStage != null) {
            generateMatchScheduleStage.show();
            generateMatchScheduleStage.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Generate Match Schedule");
        stage.setScene(new Scene(generateMatchesLayoutRoot));
        generateMatchesController.setCompletedCallback(this::finishUpInitialization);
        stage.show();
        generateMatchScheduleStage = stage;
    }

    @FXML
    private void editCompetitorNames(ActionEvent event) {
        if (editCompetitorNamesStage != null) {
            editCompetitorNamesStage.show();
            editCompetitorNamesStage.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Edit Competitor Names");
        stage.setScene(new Scene(editNamesLayoutRoot));
        editNamesController.init();
        editNamesController.setCompletedCallback(() -> {
            matchesOverviewController.refresh();
            scheduleLoader.writeCompetitionBackup();
        });
        stage.show();
        editCompetitorNamesStage = stage;
        matchesOverviewController.refresh();
    }

    @FXML
    private void viewMatchOverview(ActionEvent event) {
        if (matchesOverviewStage != null) {
            matchesOverviewStage.show();
            matchesOverviewStage.requestFocus();
            matchesOverviewController.refresh();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Match Schedule");
        stage.setScene(new Scene(matchesOverviewLayoutRoot));
        matchesOverviewController.init();
        matchesOverviewController.setEditCallback(this::overviewEditCallback);
        stage.show();
        matchesOverviewStage = stage;
    }

    @FXML
    private void addLateCompetitor(ActionEvent event) {
        if (addLateCompetitorStage != null) {
            addLateCompetitorStage.show();
            addLateCompetitorStage.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Add Late Competitor");
        stage.setScene(new Scene(addLateCompetitorLayoutRoot));
        addLateCompetitorController.setCompletedCallback(() -> {
            stage.hide();
            this.matches = Schedule.getInstance().getMatches();
        });
        stage.show();
        addLateCompetitorStage = stage;
    }

    private void overviewEditCallback(Match m) {
        matchScoreDisplayOption.setDisable(!matches[match].isScored());
        if (matches[match].isScored()) {
            scoreController.updateDisplay(matches[match]);
        }
        scheduleLoader.writeCompetitionBackup();

        m.getRed1().addMatch(m);
        m.getRed2().addMatch(m);
        m.getBlue1().addMatch(m);
        m.getBlue2().addMatch(m);
        Rankings.getInstance().rankTeams();
    }

    @FXML
    private void openSettings(ActionEvent event) {
        if (settingsStage != null) {
            settingsStage.show();
            settingsStage.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(new Scene(settingsLayoutRoot));
        stage.show();
        settingsStage = stage;
    }

    @FXML
    private void openFieldControls(ActionEvent event) {
        if (fieldControlsStage != null) {
            fieldControlsStage.show();
            fieldControlsStage.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Field Controls");
        stage.setScene(new Scene(fieldControlsLayoutRoot));
        stage.show();
        fieldControlsStage = stage;
    }

    @FXML
    private void closeProgram(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close Confirmation");
        alert.setHeaderText("Close Scoreboard Software");
        alert.setContentText("Closing the controller window will stop the display software. "
                + "Match scores stored in the progam will not be saved. "
                + "Are you sure you want to close?");
        ButtonType yesButton = new ButtonType("Close Application");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            Platform.exit();
            System.exit(0);
        } else {
            event.consume();
        }
    }

    private void matchComplete() {
        startMatchButton.setDisable(false);
        abortMatchButton.setDisable(true);
        field1.setDisable(false);
        field2.setDisable(false);
    }

    @FXML
    private void addMoreMatchesToSchedule(ActionEvent event) {
        if (addMoreMatchesStage != null) {
            addMoreMatchesStage.show();
            addMoreMatchesStage.requestFocus();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Add More Matches");
        stage.setScene(new Scene(addMoreMatchesLayoutRoot));
        addMoreMatchesController.setCompletedCallback(() -> {
            this.matches = Schedule.getInstance().getMatches();
            addMoreMatchesStage.hide();
            matchesOverviewController.refresh();
            scheduleLoader.writeCompetitionBackup();
        });
        stage.show();
        addMoreMatchesStage = stage;
    }

    @FXML
    private void removeCompetitor(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setHeaderText(null);
        alert.setContentText("This feature doesn't work yet. Sorry!");
        alert.show();
    }
}
