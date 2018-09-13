package com.coderedrobotics.nrgscoreboard;

import com.coderedrobotics.nrgscoreboard.util.MqttConnection;
import com.coderedrobotics.nrgscoreboard.ui.controllers.ControllerController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Michael
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        boolean settingsError = false;
        try {
            Settings.readFile();
        } catch (IOException | ParseException | ClassCastException | NullPointerException ex) {
            if (ex instanceof FileNotFoundException) {
                Settings.writeFile();
            } else {
                settingsError = true;
            }
            ex.printStackTrace();
        }
        
        MqttConnection.getInstance();
        
        FXMLLoader controllerLayout = new FXMLLoader();
        controllerLayout.setLocation(getClass().getResource("ui/views/Controller.fxml"));
        Parent controllerLayoutRoot = (Parent) controllerLayout.load();
        Scene controllerScene = new Scene(controllerLayoutRoot);
        primaryStage.setScene(controllerScene);

        primaryStage.setTitle("NRG Controller");

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
        if (settingsError) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Settings File Error");
            alert.setHeaderText("An error occured while reading setting file");
            alert.setContentText("There was a problem with reading the setting file. The JSON may be malformed. If the problem persists, delete \"nrg_settings.json\".");
            alert.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
