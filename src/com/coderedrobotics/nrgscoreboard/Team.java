package com.coderedrobotics.nrgscoreboard;

import com.coderedrobotics.nrgscoreboard.Settings.RankingOrderOption;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Michael
 */
public class Team implements Comparable<Team> {

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
    private int totalRankingPoints;

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
        rank = 0;
        penalty = 0;
        points = 0;
        totalRankingPoints = 0;
        matches.stream().forEach((m) -> {
            if (m.isRed(this)) {
                totalScore += m.getRedScore();
                totalRankingPoints += m.getRedRankingPoints();
                wins += m.getRedScore() > m.getBlueScore() ? 1 : 0;
                loses += m.getBlueScore() > m.getRedScore() ? 1 : 0;
                ties += m.getBlueScore() == m.getRedScore() ? 1 : 0;
                penalty += m.getRedPenalty();
                points += m.getRedPoints();
            } else {
                totalScore += m.getBlueScore();
                totalRankingPoints += m.getBlueRankingPoints();
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

    public double getAverageMatchScore() {
        int numMatches = getNumberMatchesPlayed();
        if (numMatches == 0) {
            return 0.0;
        }
        return ((double) (totalScore)) / numMatches;
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

    public int getTotalRankingPoints() {
        return totalRankingPoints;
    }

    public double getAverageRankingPoints() {
        int numMatches = getNumberMatchesPlayed();
        if (numMatches == 0) {
            return 0.0;
        }
        return ((double) (totalRankingPoints)) / (double) numMatches;
    }
    
    public double getAverageNonPenaltyPoints() {
        int numMatches = getNumberMatchesPlayed();
        if (numMatches == 0) {
            return 0.0;
        }
        return ((double) (points)) / (double) numMatches;
    }

    @Override
    public int compareTo(Team o) {
        if (o == null) {
            throw new NullPointerException("Cannot compare to null");
        }

        int result;
        if ((result = compareBy(Settings.rankTeamsBy, o)) != 0) {
            return result;
        } else if ((result = compareBy(Settings.rankTiebreaker1, o)) != 0) {
            return result;
        } else if ((result = compareBy(Settings.rankTiebreaker2, o)) != 0) {
            return result;
        } else if ((result = compareBy(Settings.rankTiebreaker3, o)) != 0) {
            return result;
        } else if ((result = compareBy(Settings.rankTiebreaker4, o)) != 0) {
            return result;
        } else {
            return new Random().nextBoolean() ? 1 : -1;
        }
    }
    
    private int compareBy(RankingOrderOption option, Team o) {
        switch (option) {
            case RANKING_POINTS:
                return -normalize(this.getAverageRankingPoints() - o.getAverageRankingPoints()); // negate to rank descending 
            case AVERAGE_MATCH_SCORE:
                return -normalize(this.getAverageMatchScore() - o.getAverageMatchScore()); // negate to rank descending 
            case AVERAGE_NON_PENALTY_POINTS:
                return -normalize(this.getAverageNonPenaltyPoints() - o.getAverageNonPenaltyPoints()); // negate to rank descending 
            case LEAST_PENALTY_POINTS:
                return this.penalty - o.penalty;
            case WINS:
                return -(this.wins - o.wins); // negate to rank descending 
        }
        return 0;
    }
    
    private int normalize(double value) {
        return (int) (value / Math.abs(value));
    }
}
