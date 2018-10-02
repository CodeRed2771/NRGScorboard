package com.coderedrobotics.nrgscoreboard.util;

import com.coderedrobotics.nrgscoreboard.Robot;
import com.coderedrobotics.nrgscoreboard.Settings;
import com.coderedrobotics.nrgscoreboard.ui.controllers.helpers.IndicatorColorManager;
import com.coderedrobotics.nrgscoreboard.ui.controllers.helpers.RobotConnectionManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Michael
 */
public class MqttConnection {

    private MqttClient client;
    private Thread connectThread;
    public IndicatorColorManager colorManager = null;
    public RobotConnectionManager robotConnectionManager = null;
    
    private static MqttConnection instance;
    public static MqttConnection getInstance() {
        if (instance == null) {
            instance = new MqttConnection();
        }
        return instance;
    }
    
    private MqttConnection() {
        try {
            client = new MqttClient("tcp://" + Settings.mqttBrokerLocation, "nrg-field-timer");
            startConnectThread();
        } catch (MqttException ex) {
            Logger.getLogger(MqttConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setColorManager(IndicatorColorManager colorManager) {
        this.colorManager = colorManager;
        colorManager.setMqttConnected(client != null && client.isConnected());
    }

    public void setRobotConnectionManager(RobotConnectionManager robotConnectionManager) {
        this.robotConnectionManager = robotConnectionManager;
    }
    
    private void startConnectThread() {
        connectThread = new Thread(() -> {
                boolean connected = false;
                do {
                    try {
                        connect();
                        connected = true;
                    } catch (MqttException ex) {
                        
                    }
                } while (!connected);
            });
        connectThread.start();
    }
    
    private void connect() throws MqttException {
        if (client == null || client.isConnected()) {
            return;
        }
        
        client.connect();
        client.setTimeToWait(5000);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                client = null;
                colorManager.setMqttConnected(false);
                startConnectThread();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (colorManager != null) {
                    colorManager.updateStatusFromMqttMessage(topic, message);
                }
                if (robotConnectionManager != null) {
                    robotConnectionManager.updateStatusFromMqttMessage(topic, message);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        
        client.subscribe(new String[] {
            "/field/1/robot/R1/status",
            "/field/1/robot/R2/status",
            "/field/1/robot/B1/status",
            "/field/1/robot/B2/status",
            "/field/2/robot/R1/status",
            "/field/2/robot/R2/status",
            "/field/2/robot/B1/status",
            "/field/2/robot/B2/status",
            "/field/1/lastseen",
            "/field/2/lastseen"
        }, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0, 1, 1
        });
        if (colorManager != null) {
            colorManager.setMqttConnected(true);
        }
    }
    
    public void publish(String topic, String message) {
        publish(topic, message, false, 0);
    }
    
    public void publish(String topic, String message, boolean retain, int qualityOfService) {
        if (client == null && !connectThread.isAlive()) {
            startConnectThread();
        }
        
        try {
            client.publish(topic, message.getBytes(), qualityOfService, retain);
        } catch (MqttException ex) {
            startConnectThread();
        }
    }

    private Robot.RobotOpMode getOpModeFromString(String opMode) {
        switch (opMode) {
            case "disabled":
                return Robot.RobotOpMode.Disabled;
            case "autonomous":
                return Robot.RobotOpMode.Autonomous;
            case "teleop":
                return Robot.RobotOpMode.Teleop;
            case "endgame":
                return Robot.RobotOpMode.EndGame;
            case "disconected":
                return Robot.RobotOpMode.Disconnected;
            default:
                return null;
        }
    }

    private String getStringFromOpMode(Robot.RobotOpMode opMode) {
        switch (opMode) {
            case NoRobot:
                return "norobot";
            case Disconnected:
                return "disconnected";
            case Disabled:
                return "disabled";
            case Teleop:
                return "teleop";
            case Autonomous:
                return "autonomous";
            case EndGame:
                return "endgame";
            default:
                return null;
        }
    }
}
