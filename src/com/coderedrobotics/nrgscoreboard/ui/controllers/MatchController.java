package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.Main;
import com.coderedrobotics.nrgscoreboard.Schedule.Match;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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

    private Label controllerTime;

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

    public void updateTeamDisplays(Match m) {
        timePercentage.setProgress(0);
        time.setVisible(true);
        time.setText("135");
        match.setText("Match " + m.getNumber());
        this.red1.setText(m.getRed1().getName());
        this.red2.setText(m.getRed2().getName());
        this.blue1.setText(m.getBlue1().getName());
        this.blue2.setText(m.getBlue2().getName());
    }

    public void startTimer() {
        if (currentThread != null && currentThread.isAlive()) {
            System.out.println("Cannot start new match when current match is running.");
        }
        remainingSeconds = 135;
        matchRunning = true;
        timePercentage.setStyle("-fx-progress-color: #0F0;");
        time.setVisible(true);
        currentThread = new Thread(() -> {
            while (remainingSeconds >= 0 && matchRunning) {
                timePercentage.setProgress((135 - remainingSeconds) / 135d);
                Platform.runLater(() -> {
                    time.setText(String.valueOf(remainingSeconds));
                    if (controllerTime != null) {
                        controllerTime.setText(String.format("%02d:%02d", (int) Math.floor(remainingSeconds / 60), remainingSeconds % 60));
                    }
                });
                if (remainingSeconds == 30) {
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
                    timePercentage.setStyle("-fx-progress-color: #FFD700;");
                }
                if (remainingSeconds == 0) {
                    timePercentage.setStyle("-fx-progress-color: #FF0000;");
                    time.setVisible(false);
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
        time.setVisible(false);
    }
}
