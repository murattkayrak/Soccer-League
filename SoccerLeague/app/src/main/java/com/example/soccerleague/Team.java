package com.example.soccerleague;

public class Team {

    private String name;
    private League league;
//    private String pictureLink; // sonradan dahil edilebilir

    public Team(String name, League league) {
        this.name = name;
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
