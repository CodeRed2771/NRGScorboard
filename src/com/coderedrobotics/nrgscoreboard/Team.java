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
        matches.stream().forEach((m) -> {
            if (m.isRed(this)) {
                totalScore += m.getRedScore();
                wins += m.getRedScore() > m.getBlueScore() ? 1 : 0;
                loses += m.getBlueScore() > m.getRedScore() ? 1 : 0;
                ties += m.getBlueScore() == m.getRedScore() ? 1 : 0;
            } else {
                totalScore += m.getBlueScore();
                wins += m.getBlueScore() > m.getRedScore() ? 1 : 0;
                loses += m.getRedScore() > m.getBlueScore() ? 1 : 0;
                ties += m.getBlueScore() == m.getRedScore() ? 1 : 0;
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

    public int getLoses() {
        return loses;
    }

    public int getTies() {
        return ties;
    }

    public int getNumMatches() {
        return matches.size();
    }

    public double getAverageScore() {
        return ((double) (totalScore)) / getNumMatches();
    }
    
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
}
