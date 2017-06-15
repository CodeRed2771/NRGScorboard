package com.coderedrobotics.nrgscoreboard;

import com.coderedrobotics.nrgscoreboard.Schedule.Match;
import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public class Team {

    private String name;
    private final ArrayList<Match> matches;

    //STATS
    private int totalScore;
    private int wins;
    private int loses;
    private int ties;
    private int rank;
    private int penalty;
    private int points;

    public Team(String name) {
        this.name = name;
        matches = new ArrayList<>();
    }

    public void addMatch(Match match) {
        matches.remove(match);
        matches.add(match);
        recalculate();
    }

    public void recalculate() {
        totalScore = 0;
        wins = 0;
        loses = 0;
        ties = 0;
        penalty = 0;
        points = 0;
        matches.stream().forEach((m) -> {
            if (m.isRed(this)) {
                totalScore += m.getRedScore();
                wins += m.getRedScore() > m.getBlueScore() ? 1 : 0;
                loses += m.getBlueScore() > m.getRedScore() ? 1 : 0;
                ties += m.getBlueScore() == m.getRedScore() ? 1 : 0;
                penalty += m.getRedPenalty();
                points += m.getRedPoints();
            } else {
                totalScore += m.getBlueScore();
                wins += m.getBlueScore() > m.getRedScore() ? 1 : 0;
                loses += m.getRedScore() > m.getBlueScore() ? 1 : 0;
                ties += m.getBlueScore() == m.getRedScore() ? 1 : 0;
                penalty += m.getBluePenalty();
                points += m.getBluePoints();
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getTotalScore() {
        return totalScore;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return loses;
    }

    public int getTies() {
        return ties;
    }

    public int getNumberMatchesPlayed() {
        return matches.size();
    }

    public double getAverageScore() {
        return ((double) (totalScore)) / getNumberMatchesPlayed();
    }
    
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public int getTotalPenaltyPoints() {
        return penalty;
    }
    
    public int getTotalAlliancePoints() {
        return points;
    }
}
