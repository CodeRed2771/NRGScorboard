package com.coderedrobotics.nrgscoreboard.ui.controllers.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Michael
 */
public class IndicatorColorManager {

    public ObjectProperty<Color> field1FCS;
    public ObjectProperty<Color> field1Red1;
    public ObjectProperty<Color> field1Red2;
    public ObjectProperty<Color> field1Blue1;
    public ObjectProperty<Color> field1Blue2;
    public ObjectProperty<Color> field2FCS;
    public ObjectProperty<Color> field2Red1;
    public ObjectProperty<Color> field2Red2;
    public ObjectProperty<Color> field2Blue1;
    public ObjectProperty<Color> field2Blue2;
    public ObjectProperty<Color> mqttConnected;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy hh:mm:ss a");
    
    private Date previousField1Time = null;
    private Date previousField2Time = null;
    
    private Thread lastSeenTimeChecker;

    public IndicatorColorManager() {
        field1FCS = new SimpleObjectProperty<>(this, "field1FCS", Color.RED);
        field1Red1 = new SimpleObjectProperty<>(this, "field1Red1", Color.RED);
        field1Red2 = new SimpleObjectProperty<>(this, "field1Red2", Color.RED);
        field1Blue1 = new SimpleObjectProperty<>(this, "field1Blue1", Color.RED);
        field1Blue2 = new SimpleObjectProperty<>(this, "field1Blue2", Color.RED);
        field2FCS = new SimpleObjectProperty<>(this, "field2FCS", Color.RED);
        field2Red1 = new SimpleObjectProperty<>(this, "field2Red1", Color.RED);
        field2Red2 = new SimpleObjectProperty<>(this, "field2Red2", Color.RED);
        field2Blue1 = new SimpleObjectProperty<>(this, "field2Blue1", Color.RED);
        field2Blue2 = new SimpleObjectProperty<>(this, "field2Blue2", Color.RED);
        mqttConnected = new SimpleObjectProperty<>(this, "mqttBrokerLocation", Color.RED);
        
        lastSeenTimeChecker = new Thread(() -> {
            while (true) {
                if (previousField1Time != null && Date.from(Instant.now()).getTime() - previousField1Time.getTime() > 10500) {
                    field1FCS.set(Color.ORANGE);
                }
                
                if (previousField2Time != null && Date.from(Instant.now()).getTime() - previousField2Time.getTime() > 10500) { 
                    field2FCS.set(Color.ORANGE);
                }
                
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IndicatorColorManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        lastSeenTimeChecker.setDaemon(true);
        lastSeenTimeChecker.start();
    }

    public void updateStatusFromMqttMessage(String topic, MqttMessage message) {
        try {
            String strMessage = message.toString().toLowerCase();
            switch (topic) {
                case "/field/1/lastseen":
                    if (strMessage.equals("disconnected")) {
                        previousField1Time = null;
                        field1FCS.set(Color.RED);
                        return;
                    }
                    previousField1Time = dateFormat.parse(strMessage);
                    field1FCS.set(Color.LIME);
                    break;
                case "/field/1/robot/R1/status":
                    field1Red1.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
                case "/field/1/robot/R2/status":
                    field1Red2.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
                case "/field/1/robot/B1/status":
                    field1Blue1.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
                case "/field/1/robot/B2/status":
                    field1Blue2.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
                case "/field/2/lastseen":
                    if (strMessage.equals("disconnected")) {
                        previousField2Time = null;
                        field2FCS.set(Color.RED);
                        return;
                    }
                    previousField2Time = dateFormat.parse(strMessage);
                    field2FCS.set(Color.LIME);
                    break;
                case "/field/2/robot/R1/status":
                    field2Red1.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
                case "/field/2/robot/R2/status":
                    field2Red2.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
                case "/field/2/robot/B1/status":
                    field2Blue1.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
                case "/field/2/robot/B2/status":
                    field2Blue2.set(strMessage.equals("norobot") ? Color.RED : strMessage.equals("disconnected") ? Color.ORANGE : Color.LIME);
                    break;
            }
        } catch (ParseException ex) {
            Logger.getLogger(IndicatorColorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setMqttConnected(boolean connected) {
        mqttConnected.set(connected ? Color.LIME : Color.RED);
    }
}
