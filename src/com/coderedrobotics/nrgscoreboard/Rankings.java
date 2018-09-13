package com.coderedrobotics.nrgscoreboard;

import com.coderedrobotics.nrgscoreboard.util.QuickSort;

/**
 *
 * @author Michael
 */
public class Rankings {
    
    private static Rankings instance = null;
    private Team[] rankedTeams;
    private QuickSort<Team> sorter;
    
    public static Rankings getInstance() {
        if (instance == null) {
            instance = new Rankings();
        }
        return instance;
    }
    
    private Rankings() {
        sorter = new QuickSort<>();
    }
    
    public Team[] getRankedTeams() {
        return rankedTeams;
    }
    
    public void rankTeams() {
        Team[] teams = Schedule.getInstance().getTeams();
        sorter.sort(teams);
        for (int i = 0; i < teams.length; i++) {
            teams[i].setRank(i + 1);
        }
        this.rankedTeams = teams;
    }
}
