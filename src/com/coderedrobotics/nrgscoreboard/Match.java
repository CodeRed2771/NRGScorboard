package com.coderedrobotics.nrgscoreboard;

/**
 *
 * @author Michael
 */
public class Match {

    Team red1, red2, blue1, blue2;
    int number;
    int redPoints, redPenalty;
    int bluePoints, bluePenalty;
    int redScore = 0;
    int blueScore = 0;
    int redRankingPoints;
    int blueRankingPoints;
    boolean scored;
    private boolean setRedPoints, setRedPenalty, setBluePoints, setBluePenalty;
    private MatchType type;
    private int numberInSeries;
    private int totalInSeries;
    private boolean isTieBreaker;

    public static enum MatchType {
        NORMAL, QUARTERFINAL, SEMIFINAL, FINAL
    }
    
    public static enum Station {
        RED_1, RED_2, BLUE_1, BLUE_2
    }
    
    public Match(Team red1, Team red2, Team blue1, Team blue2, int number) {
        this(red1, red2, blue1, blue2, number, MatchType.NORMAL, -1, -1);
    }

    public Match(Team red1, Team red2, Team blue1, Team blue2, int number, MatchType type, int numberInSeries, int totalInSeries) {
        this.red1 = red1;
        this.red2 = red2;
        this.blue1 = blue1;
        this.blue2 = blue2;
        this.number = number;
        this.type = type;
        this.numberInSeries = numberInSeries;
        this.totalInSeries = totalInSeries;
    }

    public Team getRed1() {
        return red1;
    }

    public Team getRed2() {
        return red2;
    }

    public Team getBlue1() {
        return blue1;
    }

    public Team getBlue2() {
        return blue2;
    }

    public int getNumber() {
        return number;
    }

    public void setScore(int red, int blue, int redPoints, int redPenalty,
                         int bluePoints, int bluePenalty, 
                         int redRankingPoints, int blueRankingPoints) {
        this.redScore = red;
        this.blueScore = blue;
        this.redPoints = redPoints;
        this.redPenalty = redPenalty;
        this.bluePoints = bluePoints;
        this.bluePenalty = bluePenalty;
        this.redRankingPoints = redRankingPoints;
        this.blueRankingPoints = blueRankingPoints;
        scored = true;
    }

    public int getRedScore() {
        return redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public boolean isScored() {
        return scored;
    }

    public int getRedPenalty() {
        return redPenalty;
    }

    public void setRedPenalty(int redPenalty) {
        this.redPenalty = redPenalty;
        setRedPenalty = true;
    }

    public int getBluePoints() {
        return bluePoints;
    }

    public void setBluePoints(int bluePoints) {
        this.bluePoints = bluePoints;
        setBluePoints = true;
    }

    public int getBluePenalty() {
        return bluePenalty;
    }

    public void setBluePenalty(int bluePenalty) {
        this.bluePenalty = bluePenalty;
        setBluePenalty = true;
    }

    public int getRedPoints() {
        return redPoints;
    }

    public void setRedPoints(int redPoints) {
        this.redPoints = redPoints;
        setRedPoints = true;
    }

    public void rescore() {
        if (scored || (setRedPoints && setBluePoints && setRedPenalty && setBluePenalty)) {
            redScore = bluePenalty + redPoints;
            blueScore = redPenalty + bluePoints;
            scored = true;
        }
    }

    public boolean isRed(Team t) {
        return t == red1 || t == red2;
    }

    public MatchType getType() {
        return type;
    }

    public int getNumberInSeries() {
        return numberInSeries;
    }

    public int getTotalInSeries() {
        return totalInSeries;
    }
    
    public boolean isTieBreaker() {
        return isTieBreaker;
    }

    public void setIsTieBreaker(boolean isTieBreaker) {
        this.isTieBreaker = isTieBreaker;
    }

    public int getRedRankingPoints() {
        return redRankingPoints;
    }

    public void setRedRankingPoints(int redRankingPoints) {
        this.redRankingPoints = redRankingPoints;
    }

    public int getBlueRankingPoints() {
        return blueRankingPoints;
    }

    public void setBlueRankingPoints(int blueRankingPoints) {
        this.blueRankingPoints = blueRankingPoints;
    }
    
    public Team replaceTeam(Station station, Team newTeam) {
        Team oldTeam = null;
        switch (station) {
            case RED_1:
                oldTeam = red1;
                red1 = newTeam;
                break;
            case RED_2:
                oldTeam = red2;
                red2 = newTeam;
                break;
            case BLUE_1:
                oldTeam = blue1;
                blue1 = newTeam;
                break;
            case BLUE_2:
                oldTeam = blue2;
                blue2 = newTeam;
                break;
        }
        return oldTeam;
    }
}
