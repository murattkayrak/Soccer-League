package com.example.soccerleague;

public class Team {

    private String name;
    private League league;
    private String pictureLink; // sonradan dahil edilebilir

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, String pictureLink) {
        this.name = name;
        this.pictureLink = pictureLink;
    }

    public Team(String name, League league, String pictureLink) {
        this.name = name;
        this.league = league;
        this.pictureLink = pictureLink;
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

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
