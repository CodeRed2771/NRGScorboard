package com.coderedrobotics.nrgscoreboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Michael
 */
public class Settings {

    public enum RankingOrderOption {
        RANKING_POINTS,
        AVERAGE_MATCH_SCORE,
        LEAST_PENALTY_POINTS,
        AVERAGE_NON_PENALTY_POINTS,
        WINS
    }
    
    public static int width = 1024;
    public static int height = 768;
    public static boolean windowed = false;
    public static int projectorLocationIndex = 0;
    
    public static boolean soundEnabled = true;
    public static boolean endGameEnabled = true;
    public static boolean autonomousEnabled = false;
    public static int matchLength = 150;
    public static int autonomousDuration = 0;
    public static int endGameDuration = 30;
    
    public static String mqttBrokerLocation = "10.27.71.200";
    public static boolean useEmbeddedMqttBroker = false;
    public static boolean field1AutomaticOpmode = true;
    public static boolean field2AutomaticOpmode = true;
    
    public static boolean enableRankingPoints = true;
    public static RankingOrderOption rankTeamsBy = RankingOrderOption.RANKING_POINTS;
    public static RankingOrderOption rankTiebreaker1 = RankingOrderOption.LEAST_PENALTY_POINTS;
    public static RankingOrderOption rankTiebreaker2 = RankingOrderOption.WINS;
    public static RankingOrderOption rankTiebreaker3 = RankingOrderOption.AVERAGE_NON_PENALTY_POINTS;
    public static RankingOrderOption rankTiebreaker4 = RankingOrderOption.AVERAGE_MATCH_SCORE;

    public static void readFile() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("nrg_settings.json"));
        JSONObject settings = (JSONObject) obj;
        
        JSONObject display = (JSONObject) settings.get("display");
        width = ((Long) display.get("width")).intValue();
        height = ((Long) display.get("height")).intValue();
        windowed = (boolean) display.get("windowed");
        projectorLocationIndex = ((Long) display.get("projectorLocationIndex")).intValue();
        
        JSONObject match = (JSONObject) settings.get("match");
        soundEnabled = (boolean) match.get("soundEnabled");
        endGameEnabled = (boolean) match.get("endGameEnabled");
        autonomousEnabled = (boolean) match.get("autonomousEnabled");
        matchLength = ((Long) match.get("matchLength")).intValue();
        autonomousDuration = ((Long) match.get("autonomousDuration")).intValue();
        endGameDuration = ((Long) match.get("endGameDuration")).intValue();
        
        JSONObject network = (JSONObject) settings.get("network");
        mqttBrokerLocation = (String) network.get("mqttBrokerLocation");
        useEmbeddedMqttBroker = (boolean) network.get("useEmbeddedMqttBroker");
        field1AutomaticOpmode = (boolean) network.get("field1AutomaticOpmode");
        field2AutomaticOpmode = (boolean) network.get("field2AutomaticOpmode");
        
        JSONObject ranking = (JSONObject) settings.get("ranking");
        enableRankingPoints = (boolean) ranking.get("enableRankingPoints");
        rankTeamsBy = RankingOrderOption.valueOf((String) ranking.get("rankTeamsBy"));
        rankTiebreaker1 = RankingOrderOption.valueOf((String) ranking.get("rankTiebreaker1"));
        rankTiebreaker2 = RankingOrderOption.valueOf((String) ranking.get("rankTiebreaker2"));
        rankTiebreaker3 = RankingOrderOption.valueOf((String) ranking.get("rankTiebreaker3"));
        rankTiebreaker4 = RankingOrderOption.valueOf((String) ranking.get("rankTiebreaker4"));
    }

    public static void writeFile() {
        JSONObject display = new JSONObject();
        display.put("width", width);
        display.put("height", height);
        display.put("windowed", windowed);
        display.put("projectorLocationIndex", projectorLocationIndex);

        JSONObject match = new JSONObject();
        match.put("soundEnabled", soundEnabled);
        match.put("endGameEnabled", endGameEnabled);
        match.put("autonomousEnabled", autonomousEnabled);
        match.put("matchLength", matchLength);
        match.put("autonomousDuration", autonomousDuration);
        match.put("endGameDuration", endGameDuration);

        JSONObject network = new JSONObject();
        network.put("mqttBrokerLocation", mqttBrokerLocation);
        network.put("useEmbeddedMqttBroker", useEmbeddedMqttBroker);
        network.put("field1AutomaticOpmode", field1AutomaticOpmode);
        network.put("field2AutomaticOpmode", field2AutomaticOpmode);

        JSONObject ranking = new JSONObject();
        ranking.put("enableRankingPoints", enableRankingPoints);
        ranking.put("rankTeamsBy", rankTeamsBy.toString());
        ranking.put("rankTiebreaker1", rankTiebreaker1.toString());
        ranking.put("rankTiebreaker2", rankTiebreaker2.toString());
        ranking.put("rankTiebreaker3", rankTiebreaker3.toString());
        ranking.put("rankTiebreaker4", rankTiebreaker4.toString());
        
        JSONObject settings = new JSONObject();
        settings.put("display", display);
        settings.put("match", match);
        settings.put("network", network);
        settings.put("ranking", ranking);
        
        try (FileWriter file = new FileWriter("nrg_settings.json")) {
            file.write(settings.toJSONString());
            file.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
