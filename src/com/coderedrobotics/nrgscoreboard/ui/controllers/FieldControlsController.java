package com.coderedrobotics.nrgscoreboard.ui.controllers;

import com.coderedrobotics.nrgscoreboard.util.MqttConnection;
import com.coderedrobotics.nrgscoreboard.Settings;
import com.coderedrobotics.nrgscoreboard.ui.controllers.helpers.RobotConnectionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class FieldControlsController implements Initializable {

    @FXML
    private Button field1Red1Button;
    
    @FXML
    private Button field1Red2Button;
    
    @FXML
    private Button field1Blue1Button;
    
    @FXML
    private Button field1Blue2Button;
    
    @FXML
    private Button field2Red1Button;
    
    @FXML
    private Button field2Red2Button;
    
    @FXML
    private Button field2Blue1Button;
    
    @FXML
    private Button field2Blue2Button;
    
    @FXML
    private ComboBox field1Opmode;
    
    @FXML
    private ComboBox field2Opmode;
    
    private RobotConnectionManager manager;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = new RobotConnectionManager();
        MqttConnection.getInstance().setRobotConnectionManager(manager);
        
        field1Red1Button.disableProperty().bind(Bindings.not(manager.field1Red1Available));
        field1Red2Button.disableProperty().bind(Bindings.not(manager.field1Red2Available));
        field1Blue1Button.disableProperty().bind(Bindings.not(manager.field1Blue1Available));
        field1Blue2Button.disableProperty().bind(Bindings.not(manager.field1Blue2Available));
        field2Red1Button.disableProperty().bind(Bindings.not(manager.field2Red1Available));
        field2Red2Button.disableProperty().bind(Bindings.not(manager.field2Red2Available));
        field2Blue1Button.disableProperty().bind(Bindings.not(manager.field2Blue1Available));
        field2Blue2Button.disableProperty().bind(Bindings.not(manager.field2Blue2Available));
        
        field1Red1Button.textProperty().bind(Bindings.when(manager.field1Red1Connected).then("Disconnect").otherwise("Connect"));
        field1Red2Button.textProperty().bind(Bindings.when(manager.field1Red2Connected).then("Disconnect").otherwise("Connect"));
        field1Blue1Button.textProperty().bind(Bindings.when(manager.field1Blue1Connected).then("Disconnect").otherwise("Connect"));
        field1Blue2Button.textProperty().bind(Bindings.when(manager.field1Blue2Connected).then("Disconnect").otherwise("Connect"));
        field2Red1Button.textProperty().bind(Bindings.when(manager.field2Red1Connected).then("Disconnect").otherwise("Connect"));
        field2Red2Button.textProperty().bind(Bindings.when(manager.field2Red2Connected).then("Disconnect").otherwise("Connect"));
        field2Blue1Button.textProperty().bind(Bindings.when(manager.field2Blue1Connected).then("Disconnect").otherwise("Connect"));
        field2Blue2Button.textProperty().bind(Bindings.when(manager.field2Blue2Connected).then("Disconnect").otherwise("Connect"));
        
        ObservableList opmodes = FXCollections.observableArrayList();
        opmodes.add("<Automatic>");
        opmodes.add("Disabled");
        opmodes.add("Autonomous");
        opmodes.add("Teleop");
        opmodes.add("End Game");
        
        field1Opmode.setItems(opmodes);
        field1Opmode.setValue("<Automatic>");
        field1Opmode.valueProperty().addListener((e) -> {
            if (field1Opmode.getValue().toString().equals("<Automatic>")) {
                Settings.field1AutomaticOpmode = true;
                MqttConnection.getInstance().publish("/field/1/opmode", "disabled", true, 1);
                return;
            }
            Settings.field1AutomaticOpmode = false;
            MqttConnection.getInstance().publish("/field/1/opmode", field1Opmode.getValue().toString().toLowerCase().replace(" ", ""), true, 1);
        });
        
        field2Opmode.setItems(opmodes);
        field2Opmode.setValue("<Automatic>");
        field2Opmode.valueProperty().addListener((e) -> {
            if (field2Opmode.getValue().toString().equals("<Automatic>")) {
                Settings.field2AutomaticOpmode = true;
                MqttConnection.getInstance().publish("/field/2/opmode", "disabled", true, 1);
                return;
            }
            Settings.field2AutomaticOpmode = false;
            MqttConnection.getInstance().publish("/field/2/opmode", field2Opmode.getValue().toString().toLowerCase().replace(" ", ""), true, 1);
        });
    }
    
    @FXML
    public void field1Red1ButtonAction(ActionEvent event) {
        String command = manager.field1Red1Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/1/robot/R1/control", command, false, 1);
    }
    
    @FXML
    public void field1Red2ButtonAction(ActionEvent event) {
        String command = manager.field1Red2Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/1/robot/R2/control", command, false, 1);
    }
    
    @FXML
    public void field1Blue1ButtonAction(ActionEvent event) {
        String command = manager.field1Blue1Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/1/robot/B1/control", command, false, 1);
    }
    
    @FXML
    public void field1Blue2ButtonAction(ActionEvent event) {
        String command = manager.field1Blue2Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/1/robot/B2/control", command, false, 1);
    }
    
    @FXML
    public void field2Red1ButtonAction(ActionEvent event) {
        String command = manager.field2Red1Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/2/robot/R1/control", command, false, 1);
    }
    
    @FXML
    public void field2Red2ButtonAction(ActionEvent event) {
        String command = manager.field2Red2Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/2/robot/R2/control", command, false, 1);
    }
    
    @FXML
    public void field2Blue1ButtonAction(ActionEvent event) {
        String command = manager.field2Blue1Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/2/robot/B1/control", command, false, 1);
    }
    
    @FXML
    public void field2Blue2ButtonAction(ActionEvent event) {
        String command = manager.field2Blue2Connected.get() ? "disconnect" : "connect";
        MqttConnection.getInstance().publish("/field/2/robot/B2/control", command, false, 1);
    }
}
