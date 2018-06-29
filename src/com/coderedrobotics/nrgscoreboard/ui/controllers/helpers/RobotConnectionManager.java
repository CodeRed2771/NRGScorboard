package com.coderedrobotics.nrgscoreboard.ui.controllers.helpers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Michael
 */
public class RobotConnectionManager {

    public BooleanProperty field1Red1Available = new SimpleBooleanProperty(false);
    public BooleanProperty field1Red2Available = new SimpleBooleanProperty(false);
    public BooleanProperty field1Blue1Available = new SimpleBooleanProperty(false);
    public BooleanProperty field1Blue2Available = new SimpleBooleanProperty(false);
    public BooleanProperty field2Red1Available = new SimpleBooleanProperty(false);
    public BooleanProperty field2Red2Available = new SimpleBooleanProperty(false);
    public BooleanProperty field2Blue1Available = new SimpleBooleanProperty(false);
    public BooleanProperty field2Blue2Available = new SimpleBooleanProperty(false);
    public BooleanProperty field1Red1Connected = new SimpleBooleanProperty(false);
    public BooleanProperty field1Red2Connected = new SimpleBooleanProperty(false);
    public BooleanProperty field1Blue1Connected = new SimpleBooleanProperty(false);
    public BooleanProperty field1Blue2Connected = new SimpleBooleanProperty(false);
    public BooleanProperty field2Red1Connected = new SimpleBooleanProperty(false);
    public BooleanProperty field2Red2Connected = new SimpleBooleanProperty(false);
    public BooleanProperty field2Blue1Connected = new SimpleBooleanProperty(false);
    public BooleanProperty field2Blue2Connected = new SimpleBooleanProperty(false);

    public RobotConnectionManager() {
    }

    public void updateStatusFromMqttMessage(String topic, MqttMessage message) {
        String strMessage = message.toString().toLowerCase();
        Platform.runLater(() -> {
            switch (topic) {
                case "/field/1/robot/R1/status":
                    field1Red1Available.set(!strMessage.equals("norobot"));
                    field1Red1Connected.set(field1Red1Available.get() && !strMessage.equals("disconnected"));
                    break;
                case "/field/1/robot/R2/status":
                    field1Red2Available.set(!strMessage.equals("norobot"));
                    field1Red2Connected.set(field1Red2Available.get() && !strMessage.equals("disconnected"));
                    break;
                case "/field/1/robot/B1/status":
                    field1Blue1Available.set(!strMessage.equals("norobot"));
                    field1Blue1Connected.set(field1Blue1Available.get() && !strMessage.equals("disconnected"));
                    break;
                case "/field/1/robot/B2/status":
                    field1Blue2Available.set(!strMessage.equals("norobot"));
                    field1Blue2Connected.set(field1Blue2Available.get() && !strMessage.equals("disconnected"));
                    break;
                case "/field/2/robot/R1/status":
                    field2Red1Available.set(!strMessage.equals("norobot"));
                    field2Red1Connected.set(field2Red1Available.get() && !strMessage.equals("disconnected"));
                    break;
                case "/field/2/robot/R2/status":
                    field2Red2Available.set(!strMessage.equals("norobot"));
                    field2Red2Connected.set(field2Red2Available.get() && !strMessage.equals("disconnected"));
                    break;
                case "/field/2/robot/B1/status":
                    field2Blue1Available.set(!strMessage.equals("norobot"));
                    field2Blue1Connected.set(field2Blue1Available.get() && !strMessage.equals("disconnected"));
                    break;
                case "/field/2/robot/B2/status":
                    field2Blue2Available.set(!strMessage.equals("norobot"));
                    field2Blue2Connected.set(field2Blue2Available.get() && !strMessage.equals("disconnected"));
                    break;
            }
        });
    }
}
