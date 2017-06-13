package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Main;
import com.coderedrobotics.nrgscoreboard.Schedule;
import com.coderedrobotics.nrgscoreboard.Schedule.Match;
import com.coderedrobotics.nrgscoreboard.Team;
import com.sun.prism.paint.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
    private int match = 0;
    private Parent matchLayoutRoot, logoLayoutRoot, scoreLayoutRoot, preMatchLayoutRoot, 
            generateMatchesLayoutRoot, matchesOverviewLayoutRoot, editNamesLayoutRoot;
    private Parent playoffsControllerLayoutRoot;
    private Parent currentLayoutRoot;
    private Parent previousLayoutRoot;
    private Stage projectorStage;
    private boolean inEliminations;
    private Team sf1Winner1, sf1Winner2;
    private Team sf2Winner1, sf2Winner2;
    private Stage playoffsStage = null;
    private Stage generateMatchScheduleStage = null;
    private Stage matchesOverviewStage = null;
    private Stage editCompetitorNamesStage = null;

    private boolean windowed = false;
    private int width = 1024;
    private int height = 768;
    
    private ObservableList aspectRatio1Items;
    private ObservableList aspectRatio2Items;
    private ObservableList aspectRatio3Items;
    private ObservableList aspectRatio4Items;
    
    private Match[] matches;
    
    @FXML
    private Label currentMatchLabel;

    @FXML
    private TextField redScoreField;

    @FXML
    private TextField blueScoreField;

    @FXML
    private TextField redPenaltyField;

    @FXML
    private TextField bluePenaltyField;

    @FXML
    private Label redTotalScore;

    @FXML
    private Label blueTotalScore;

    @FXML
    private Button startMatchButton;

    @FXML
    private Button abortMatchButton;

    @FXML
    private Button applyScoringButton;

    @FXML
    private Button editRecordedScoresButton;

    @FXML
    private Button previousMatchButton;

    @FXML
    private Button nextMatchButton;

    @FXML
    private Button competitionReportButton;

    @FXML
    private Button loadMatchScheduleButton;

    @FXML
    private Button eliminationSelectionButton;

    @FXML
    private RadioButton preMatchDisplayOption;

    @FXML
    private RadioButton matchDetailsDisplayOption;

    @FXML
    private RadioButton matchScoreDisplayOption;

    @FXML
    private RadioButton logoOnlyDisplayOption;

    @FXML
    private ComboBox projectorLocationPicker;

    @FXML
    private ComboBox aspectRatioPicker;

    @FXML
    private ComboBox displayResolutionPicker;

    @FXML
    private CheckBox windowedModeOption;

    @FXML
    private Button disqualifyCompetitorButton;

    @FXML
    private Button addLateCompetitorButton;
    
    @FXML
    private Button viewMatchScheduleButton;
    
    @FXML
    private Button editCompetitorNamesButton;
    
    @FXML
    private Label matchClock;

    public ControllerController() {
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        redScoreField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
        blueScoreField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
        redPenaltyField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
        bluePenaltyField.textProperty().addListener((observable, oldValue, newValue) -> {
            recalculateTotalScores();
        });
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

        setupProjectorStage();
    }
    
    private void setupProjectorStage() {
        if (projectorStage != null) {
            projectorStage.hide();
            projectorStage = null;
        }
        
        AnchorPane background = new AnchorPane();
        background.setStyle("-fx-background-color: #333;");

        
//        matchLayoutRoot.setScaleY(height / 768d);
        matchLayoutRoot.minWidth(width);
        matchLayoutRoot.minHeight(height);
        matchLayoutRoot.prefWidth((width));
        matchLayoutRoot.prefHeight(height);
        matchLayoutRoot.resize(width, height);
        matchLayoutRoot.layout();
//        matchLayoutRoot.setScaleX(height / 768d);
        
        
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #f00");
        stackPane.setAlignment(Pos.CENTER);
//        stackPane.getChildren().add(background);        
        stackPane.getChildren().add(matchLayoutRoot);
        stackPane.getChildren().add(logoLayoutRoot);
        stackPane.getChildren().add(scoreLayoutRoot);
        stackPane.getChildren().add(preMatchLayoutRoot);
                
        matchLayoutRoot.setOpacity(0.0);
        logoLayoutRoot.setOpacity(1.0);
        scoreLayoutRoot.setOpacity(0.0);
        preMatchLayoutRoot.setOpacity(0.0);

        Scene scene = new Scene(stackPane, width, height);
        Stage stage = new Stage();

        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = g.getScreenDevices();
        stage.setWidth(this.width);
        stage.setHeight(this.height);
        if (windowed) {
            stage.initStyle(StageStyle.DECORATED);
        } else {
            int selection = projectorLocationPicker.getSelectionModel().getSelectedIndex();
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
    }
    
//    public void init(Parent matchLayoutRoot, Parent logoLayoutRoot, Parent scoreLayoutRoot,
//            Parent preMatchLayoutRoot, Parent playoffsControllerLayoutRoot,
//            Parent generateMatchesLayoutRoot, Parent matchesOverviewLayoutRoot, Parent editNamesLayoutRoot,
//            MatchController matchController, ScoreController scoreController, PreMatchController preMatchController,
//            PlayoffsControllerController playoffsController, GenerateMatchesController generateMatchesController,
//            MatchesOverviewController matchesOverviewController, EditNamesController editNamesController, Stage projectorStage) {
//        this.matchLayoutRoot = matchLayoutRoot;
//        this.logoLayoutRoot = logoLayoutRoot;
//        this.scoreLayoutRoot = scoreLayoutRoot;
//        this.preMatchLayoutRoot = preMatchLayoutRoot;
//        this.playoffsControllerLayoutRoot = playoffsControllerLayoutRoot;
//        this.playoffsController = playoffsController;
//        this.matchController = matchController;
//        this.scoreController = scoreController;
//        this.preMatchController = preMatchController;
//        this.projectorStage = projectorStage;
//        this.generateMatchesLayoutRoot = generateMatchesLayoutRoot;
//        this.generateMatchesController = generateMatchesController;
//        this.matchesOverviewLayoutRoot = matchesOverviewLayoutRoot;
//        this.matchesOverviewController = matchesOverviewController;
//        this.editNamesLayoutRoot = editNamesLayoutRoot;
//        this.editNamesController = editNamesController;
//
//        matchController.setControlDisplayTimeLabel(matchClock);
//    }

    @FXML
    private void startMatch(ActionEvent event) {
        try {
            InputStream audioSrc = Main.class.getResourceAsStream("/Start Auto_normalized.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream start = AudioSystem.getAudioInputStream(bufferedIn);
            Clip c = AudioSystem.getClip();
            c.open(start);
            c.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        matchController.startTimer();
    }

    @FXML
    private void abortMatch(ActionEvent event) {
        try {
            InputStream audioSrc = Main.class.getResourceAsStream("/fog-blast.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream start = AudioSystem.getAudioInputStream(bufferedIn);
            Clip c = AudioSystem.getClip();
            c.open(start);
            c.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        matchController.stopMatch();
    }

    @FXML
    private void loadMatchSchedule(ActionEvent event) {
        BufferedReader read;
        String line;
        HashMap<String, Team> teamsandnames = new HashMap<>();
        ArrayList<Team> allteams = new ArrayList<>();
        try {
            read = new BufferedReader(new FileReader("schedule.csv"));
            ArrayList<Schedule.Match> matches = new ArrayList<>();
            int mn = 1;
            while ((line = read.readLine()) != null) {
                String[] data = line.split(",");
                Team[] teams = new Team[4];
                for (int i = 0; i < 4; i++) {
                    if (teamsandnames.containsKey(data[i])) {
                        teams[i] = teamsandnames.get(data[i]);
                    } else {
                        Team t = new Team(data[i]);
                        allteams.add(t);
                        teamsandnames.put(data[i], t);
                        teams[i] = t;
                    }
                }
                Schedule.Match m = new Schedule.Match(teams[0], teams[1], teams[2], teams[3], mn);
                mn++;
                matches.add(m);
            }
            Schedule.initialize(matches.toArray(new Schedule.Match[0]), allteams.toArray(new Team[0]));
            read.close();

            finishUpInitialization();
        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

            Alert alert = new Alert(AlertType.ERROR);
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
    }

    @FXML
    private void nextMatch(ActionEvent event) {
        if (match == matches.length - 1) {
            return;
        }
        match++;
        setMatch(match);
    }

    @FXML
    private void previousMatch(ActionEvent event) {
        if (match == 0) {
            return;
        }
        match--;
        setMatch(match);
    }

    @FXML
    private void preMatchDisplayOptionAction(ActionEvent event) {
        fadeLayout(preMatchLayoutRoot);
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
    private void applyScoring(ActionEvent event) {
        int red, blue, redPenalty, redPoints, bluePoints, bluePenalty;
        try {
            redPoints = Integer.parseInt(redScoreField.getText());
            bluePoints = Integer.parseInt(blueScoreField.getText());
            redPenalty = Integer.parseInt(redPenaltyField.getText());
            bluePenalty = Integer.parseInt(bluePenaltyField.getText());
            red = redPoints + bluePenalty;
            blue = bluePoints + redPenalty;
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Score");
            alert.setHeaderText(null);
            alert.setContentText("Enter a valid integer value for both red "
                    + "and blue scores before applying the scores.");

            alert.show();
            return;
        }
        Schedule.Match m = matches[match];
        if (inEliminations) {
            if (match == 0) {
                if (red > blue) {
                    sf1Winner1 = m.getRed1();
                    sf1Winner2 = m.getRed2();
                } else {
                    sf1Winner1 = m.getBlue1();
                    sf1Winner2 = m.getBlue2();
                }
            } else if (match == 1) {
                if (red > blue) {
                    sf2Winner1 = m.getRed1();
                    sf2Winner2 = m.getRed2();
                } else {
                    sf2Winner1 = m.getBlue1();
                    sf2Winner2 = m.getBlue2();
                }
                matches = new Match[]{matches[0], matches[1], new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 3)};
            }
        }
        m.setScore(red, blue, redPoints, redPenalty, bluePoints, bluePenalty);
        m.getRed1().addMatch(m);
        m.getRed2().addMatch(m);
        m.getBlue1().addMatch(m);
        m.getBlue2().addMatch(m);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Team[] teams = Schedule.getInstance().getTeams();
        for (int i = 0; i < teams.length; i++) {
            map.put(i, teams[i].getTotalScore());
        }
        SortedSet<Map.Entry<Integer, Integer>> sortedEntries = new TreeSet<>(
                (Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return -(res != 0 ? res : 1);
                });
        sortedEntries.addAll(map.entrySet());
        int rank = 1;
        for (Map.Entry<Integer, Integer> e : sortedEntries) {
            teams[e.getKey()].setRank(rank);
            rank++;
        }
        Platform.runLater(() -> {
            scoreController.updateDisplay(m);
            matchScoreDisplayOption.setDisable(false);
            redScoreField.setText("");
            blueScoreField.setText("");
            redPenaltyField.setText("");
            bluePenaltyField.setText("");
        });
        
        matchesOverviewController.refresh();
    }

    @FXML
    private void recalculateTotalScores() {
        int bluePoints, redPoints, bluePenalty, redPenalty;
        try {
            redPoints = Integer.parseInt(redScoreField.getText());
            bluePenalty = Integer.parseInt(bluePenaltyField.getText());
            int red = redPoints + bluePenalty;
            redTotalScore.setText("Total: " + red);
        } catch (NumberFormatException ex) {
            redTotalScore.setText("Total: ---");
        }

        try {
            bluePoints = Integer.parseInt(blueScoreField.getText());
            redPenalty = Integer.parseInt(redPenaltyField.getText());
            int blue = bluePoints + redPenalty;
            blueTotalScore.setText("Total: " + blue);
        } catch (NumberFormatException ex) {
            blueTotalScore.setText("Total: ---");
        }
    }

    @FXML
    private void projectorLocationAction(ActionEvent event) {
        int selection = projectorLocationPicker.getSelectionModel().getSelectedIndex();
        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = g.getScreenDevices();
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
    }

    @FXML
    private void competitionReport(ActionEvent event) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Team[] teams = Schedule.getInstance().getTeams();
        for (int i = 0; i < teams.length; i++) {
            map.put(i, teams[i].getTotalScore());
        }
        SortedSet<Map.Entry<Integer, Integer>> sortedEntries = new TreeSet<>(
                (Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return -(res != 0 ? res : 1);
                });
        sortedEntries.addAll(map.entrySet());
        String data = "";
        for (Map.Entry<Integer, Integer> e : sortedEntries) {
            Team t = teams[e.getKey()];
            data += String.format("%25s    %-10s%-20s%-20s%-10s", t.getName(), (t.getWins() + "-"
                    + t.getLoses() + "-" + t.getTies()), "Avg. Score: "
                    + t.getAverageScore(), "Total Score: " + t.getTotalScore(), "Rank: " + t.getRank()) + "\n";
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Competition Report");
        alert.setHeaderText("Ranked Competition Data");
        alert.setContentText(data);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-family: monospace");
        alert.setResizable(true);
        alert.show();
        alert.setWidth(800);
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

    void enableEliminationsMode(Match sf1, Match sf2) {
        matches = new Match[]{sf1, sf2};
        inEliminations = true;
        setMatch(0);
        eliminationSelectionButton.setDisable(true);
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
        Schedule.Match m = matches[match];
        Platform.runLater(() -> {
            matchScoreDisplayOption.setDisable(!m.isScored());
            redScoreField.setText(m.isScored() ? String.valueOf(m.getRedScore()) : "");
            blueScoreField.setText(m.isScored() ? String.valueOf(m.getBlueScore()) : "");
            currentMatchLabel.setText("Current Match: " + (match + 1));
            matchController.updateTeamDisplays(m);
            preMatchController.updateTeamDisplays(matches[match]);
            scoreController.updateDisplay(m);
        });
    }

    @FXML
    private void toggleWindowedMode(ActionEvent event) {
        windowed = !windowed;
        setupProjectorStage();        
    }

    @FXML
    private void generateMatchSchedule(ActionEvent event) {
        if (generateMatchScheduleStage != null) {
            generateMatchScheduleStage.show();
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
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Edit Competitor Names");
        stage.setScene(new Scene(editNamesLayoutRoot));
        editNamesController.init();
        editNamesController.setCompletedCallback(matchesOverviewController::refresh);
        stage.show();
        editCompetitorNamesStage = stage;
        matchesOverviewController.refresh();
    }

    private void finishUpInitialization() {
        this.matches = Schedule.getInstance().getMatches();

        startMatchButton.setDisable(false);
        abortMatchButton.setDisable(false);
        applyScoringButton.setDisable(false);
        previousMatchButton.setDisable(false);
        competitionReportButton.setDisable(false);
        nextMatchButton.setDisable(false);
        preMatchDisplayOption.setDisable(false);
        matchDetailsDisplayOption.setDisable(false);
        logoOnlyDisplayOption.setDisable(false);
        redScoreField.setDisable(false);
        blueScoreField.setDisable(false);
        eliminationSelectionButton.setDisable(false);
        loadMatchScheduleButton.setDisable(true);
        redPenaltyField.setDisable(false);
        bluePenaltyField.setDisable(false);
        disqualifyCompetitorButton.setDisable(false);
        addLateCompetitorButton.setDisable(false);
        viewMatchScheduleButton.setDisable(false);
        editRecordedScoresButton.setDisable(false);
        editCompetitorNamesButton.setDisable(false);
        
        setMatch(0);

        currentLayoutRoot = logoLayoutRoot;
    }
    
    @FXML
    private void viewMatchOverview(ActionEvent event) {
        if (matchesOverviewStage != null) {
            matchesOverviewStage.show();
            matchesOverviewController.refresh();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Match Schedule");
        stage.setScene(new Scene(matchesOverviewLayoutRoot));
        matchesOverviewController.init();
        stage.show();
        matchesOverviewStage = stage;
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
    }
    
    @FXML
    private void projectorResolutionChanged(ActionEvent event){
        String resolution = ((ObservableList<String>) displayResolutionPicker.getItems()).get(displayResolutionPicker.getSelectionModel().getSelectedIndex());
        String[] items = resolution.split("x");
        items[0] = items[0].trim();
        items[1] = items[1].trim();
        width = Integer.parseInt(items[0]);
        height = Integer.parseInt(items[1]);
        setupProjectorStage();
    }
}
