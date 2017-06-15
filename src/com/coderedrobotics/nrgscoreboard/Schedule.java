package com.coderedrobotics.nrgscoreboard;

/**
 *
 * @author Michael
 */
public class Schedule {

    Match[] matches;
    private static Schedule instance;
    Team[] teams;

    private Schedule(Match[] matches, Team[] teams) {
        this.matches = matches;
        this.teams = teams;
    }

    public static void initialize(Match[] matches, Team[] teams) {
        instance = new Schedule(matches, teams);
    }

    public static Schedule getInstance() {
        return instance;
    }

    public Match[] getMatches() {
        return matches;
    }

    public Match getMatch(int match) {
        return matches[match];
    }

    public Team[] getTeams() {
        return teams;
    }

    public static class Match {

        Team red1, red2, blue1, blue2;
        int number;
        int redPoints, redPenalty;
        int bluePoints, bluePenalty;
        int redScore = 0;
        int blueScore = 0;
        boolean scored;
        private boolean setRedPoints, setRedPenalty, setBluePoints, setBluePenalty;

        public Match(Team red1, Team red2, Team blue1, Team blue2, int number) {
            this.red1 = red1;
            this.red2 = red2;
            this.blue1 = blue1;
            this.blue2 = blue2;
            this.number = number;
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
                int bluePoints, int bluePenalty) {
            this.redScore = red;
            this.blueScore = blue;
            this.redPoints = redPoints;
            this.redPenalty = redPenalty;
            this.bluePoints = bluePoints;
            this.bluePenalty = bluePenalty;
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
    }
}
