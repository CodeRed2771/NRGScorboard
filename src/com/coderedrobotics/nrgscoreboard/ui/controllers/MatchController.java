package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Main;
import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Settings;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
public class MatchController implements Initializable {

    @FXML
    private Label red1;
    @FXML
    private Label red2;
    @FXML
    private Label blue1;
    @FXML
    private Label blue2;
    @FXML
    private Label match;
    @FXML
    private ProgressIndicator timePercentage;
    @FXML
    private Label time;

    @FXML
    private Rectangle hotColor;

    private Label controllerTime;

    private Runnable matchCompleteCallback = null;

    private int remainingSeconds = 135;
    private boolean matchRunning = true;
    private Thread currentThread;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setControlDisplayTimeLabel(Label label) {
        this.controllerTime = label;
    }

    public void setMatchCompleteCallback(Runnable runnable) {
        matchCompleteCallback = runnable;
    }

    public void updateTeamDisplays(Match m) {
        timePercentage.setProgress(0);
        time.setVisible(true);
        time.setText(String.format("%01d:%02d", (int) Math.floor(Settings.matchLength / 60), Settings.matchLength % 60));
        if (m.isTieBreaker()) {
            if (null != m.getType()) switch (m.getType()) {
                case QUARTERFINAL:
                    this.match.setText("QF " + m.getNumberInSeries() + " Tiebreaker");
                    break;
                case SEMIFINAL:
                    this.match.setText("SF " + m.getNumberInSeries() + " Tiebreaker");
                    break;
                case FINAL:
                    this.match.setText("Finals Tiebreaker");
                    break;
                default:
                    break;
            }
        } else if (m.getType() == Match.MatchType.NORMAL) {
            this.match.setText("Match " + m.getNumber());
        } else if (m.getType() == Match.MatchType.QUARTERFINAL) {
            this.match.setText("Quarterfinal " + m.getNumberInSeries() + " of " + m.getTotalInSeries());
        } else if (m.getType() == Match.MatchType.SEMIFINAL) {
            this.match.setText("Semifinal " + m.getNumberInSeries() + " of " + m.getTotalInSeries());
        } else if (m.getType() == Match.MatchType.FINAL) {
            this.match.setText("Final " + m.getNumberInSeries() + " of " + m.getTotalInSeries());
        }
        hotColor.setFill(Paint.valueOf("TRANSPARENT"));
        this.red1.setText(m.getRed1().getName());
        this.red2.setText(m.getRed2().getName());
        this.blue1.setText(m.getBlue1().getName());
        this.blue2.setText(m.getBlue2().getName());
    }

    public void startTimer() {
        if (currentThread != null && currentThread.isAlive()) {
            System.out.println("Cannot start new match when current match is running.");
        }
        remainingSeconds = Settings.matchLength;
        matchRunning = true;
        timePercentage.setStyle("-fx-progress-color: #0F0;");
        time.setVisible(true);
        currentThread = new Thread(() -> {
            while (remainingSeconds >= 0 && matchRunning) {
                timePercentage.setProgress((Settings.matchLength - remainingSeconds) / (double) Settings.matchLength);
                Platform.runLater(() -> {
                    time.setText(String.format("%01d:%02d", (int) Math.floor(remainingSeconds / 60), remainingSeconds % 60));
                    if (controllerTime != null) {
                        controllerTime.setText(String.format("%02d:%02d", (int) Math.floor(remainingSeconds / 60), remainingSeconds % 60));
                    }
                });
                if (remainingSeconds == Settings.endGameDuration && Settings.endGameEnabled) {
                    if (Settings.soundEnabled) {
                        try {
                            InputStream audioSrc = getClass().getResourceAsStream("/Start of End Game_normalized.wav");
                            InputStream bufferedIn = new BufferedInputStream(audioSrc);
                            AudioInputStream start = AudioSystem.getAudioInputStream(bufferedIn);
                            Clip c = AudioSystem.getClip();
                            c.open(start);
                            c.start();
                        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | IllegalArgumentException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
//                    timePercentage.setStyle("-fx-progress-color: #FFD700;"); // normally, but taken out for following random color selection:
                    String[] colors = {"MAGENTA", "DARKORANGE", "LIME", "YELLOW"};
//                    timePercentage.setStyle("-fx-progress-color: " + colors[new Random().nextInt(4)] + ";");
                    hotColor.setFill(Paint.valueOf(colors[new Random().nextInt(4)]));
                }
                if (remainingSeconds == 0) {
                    timePercentage.setStyle("-fx-progress-color: #FF0000;");
//                    time.setVisible(false);
                    if (Settings.soundEnabled) {
                        try {
                            InputStream audioSrc = getClass().getResourceAsStream("/Match End_normalized.wav");
                            InputStream bufferedIn = new BufferedInputStream(audioSrc);
                            AudioInputStream start = AudioSystem.getAudioInputStream(bufferedIn);
                            Clip c = AudioSystem.getClip();
                            c.open(start);
                            c.start();
                        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (matchCompleteCallback != null) {
                        matchCompleteCallback.run();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MatchController.class.getName()).log(Level.SEVERE, null, ex);
                }
                remainingSeconds--;
            }
        });
        currentThread.start();
    }

    public void stopMatch() {
        matchRunning = false;
        timePercentage.setStyle("-fx-progress-color: #FF0000;");
        timePercentage.setProgress(1.0);
        time.setText("0:00");
        hotColor.setFill(Paint.valueOf("TRANSPARENT"));
    }
}
