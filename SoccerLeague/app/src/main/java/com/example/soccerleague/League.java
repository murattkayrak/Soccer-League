package com.example.soccerleague;

import java.util.List;

public class League {

    private String name;
    private int teamCount;
    private List<Team> teamList;
    private List<Match> matchList;

    public League(String name) {
        this.name = name;
    }

    public League(String name, int teamCount) {
        this.name = name;
        this.teamCount = teamCount;
    }

    public League(String name, int teamCount, List<Team> teamList, List<Match> matchList) {
        this.name = name;
        this.teamCount = teamCount;
        this.teamList = teamList;
        this.matchList = matchList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }
}
