package com.coderedrobotics.nrgscoreboard;

/**
 *
 * @author Michael
 */
public class Schedule {

    private Match[] matches;
    private Match[] playoffMatches;
    private static Schedule instance;
    private Team[] teams;
    
    private int matchesPerTeam;
    private int currentMatch;
    
    private Schedule(Match[] matches, Team[] teams) {
        this.matches = matches;
        this.teams = teams;
    }

    public static void initialize(Match[] matches, Team[] teams, int matchesPerTeam) {
        instance = new Schedule(matches, teams);
        instance.matchesPerTeam = matchesPerTeam;
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

    public int getMatchesPlayedPerTeam() {
        return matchesPerTeam;
    }
    
    public int getNumMatches() {
        return matches.length;
    }
    
    public int getCurrentMatch() {
        return currentMatch;
    }
    
    public void setCurrentMatch(int currentMatch) {
        this.currentMatch = currentMatch;
    }
    
    public Match replaceMatch(int matchNum, Match newMatch) {
        Match oldMatch = matches[matchNum];
        matches[matchNum] = newMatch;
        return oldMatch;
    }
    
    public void addMatchToEnd(Match match) {
        Match[] matches = new Match[this.matches.length + 1];
        for (int i = 0; i < this.matches.length; i++ ){
            matches[i] = this.matches[i];
        }
        matches[this.matches.length] = match;
        this.matches = matches;
    }
    
    public void addTeam(Team team) {
        Team[] teams = new Team[this.teams.length + 1];
        for (int i = 0; i < this.teams.length; i++ ){
            teams[i] = this.teams[i];
        }
        teams[this.teams.length] = team;
        this.teams = teams;
    }

    public Match[] getPlayoffMatches() {
        return playoffMatches;
    }

    public void setPlayoffMatches(Match[] playoffMatches) {
        this.playoffMatches = playoffMatches;
    }
    
    public int calculateHighScore(Match exclude) {
        int score = Integer.MIN_VALUE;
        for (Match m : matches) {
            if (m == exclude) {
                continue;
            }
            
            if (m.isScored() && m.getRedScore() > score) {
                score = m.getRedScore();
            }
            if (m.isScored() && m.getBlueScore() > score) {
                score = m.getBlueScore();
            }
        }
        return score;
    }
}
