package com.coderedrobotics.nrgscoreboard;

import com.coderedrobotics.nrgscoreboard.ui.controllers.ControllerController;
import com.coderedrobotics.nrgscoreboard.ui.controllers.EditNamesController;
import com.coderedrobotics.nrgscoreboard.ui.controllers.GenerateMatchesController;
import com.coderedrobotics.nrgscoreboard.ui.controllers.MatchController;
import com.coderedrobotics.nrgscoreboard.ui.controllers.MatchesOverviewController;
import com.coderedrobotics.nrgscoreboard.ui.controllers.PlayoffsControllerController;
import com.coderedrobotics.nrgscoreboard.ui.controllers.PreMatchController;
import com.coderedrobotics.nrgscoreboard.ui.controllers.ScoreController;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Michael
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader controllerLayout = new FXMLLoader();
        controllerLayout.setLocation(getClass().getResource("ui/views/Controller.fxml"));
        Parent controllerLayoutRoot = (Parent) controllerLayout.load();
        Scene controllerScene = new Scene(controllerLayoutRoot);
        primaryStage.setScene(controllerScene);
        
        primaryStage.setTitle("NRG Scoreboard Controller");

        ((ControllerController) controllerLayout.getController()).init();
        
//        ((ControllerController) controllerLayout.getController()).init(matchLayoutRoot,
//                logoLayoutRoot, scoreLayoutRoot, preMatchLayoutRoot, playoffsControllerLayoutRoot, 
//                generateMatchesLayoutRoot, matchesOverviewLayoutRoot, editNamesLayoutRoot, matchController, scoreController, 
//                preMatchController, playoffsController, generateMatchesController, matchesOverviewController, editNamesController, stage);

//        stage.show();
        
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Close Confirmation");
            alert.setHeaderText("Close Scoreboard Software");
            alert.setContentText("Closing the controller window will stop the display software. "
                    + "Match scores stored in the progam will not be saved. "
                    + "Are you sure you want to close?");
            ButtonType yesButton = new ButtonType("Close Application");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                Platform.exit();
                System.exit(0);
            } else {
                event.consume();
            }
        });

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
