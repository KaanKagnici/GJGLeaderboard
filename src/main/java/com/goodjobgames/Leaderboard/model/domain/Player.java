package com.goodjobgames.Leaderboard.model.domain;

public class Player {

    private String user_id;
    private String display_name,country;
    private long rank;
    private double points;


    public Player(String user_id, String display_name, String country, double points) {
        this.user_id = user_id;
        this.display_name = display_name;
        this.country = country;
        this.points = points;
    }

    public Player(String user_id, String display_name, String country, double points, long rank) {
        this.user_id = user_id;
        this.display_name = display_name;
        this.country = country;
        this.rank = rank;
        this.points = points;
    }

    public Player() {
    }



    public String getUser_id() {
        return user_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getCountry() {
        return country;
    }

    public long getRank() {
        return rank;
    }

    public double getPoints() {
        return points;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
