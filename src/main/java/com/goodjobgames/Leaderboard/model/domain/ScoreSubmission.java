package com.goodjobgames.Leaderboard.model.domain;

public class ScoreSubmission {
    private double score_worth;
    private String user_id;

    public ScoreSubmission(double score_worth, String user_id) {
        this.score_worth = score_worth;
        this.user_id = user_id;
    }

    public ScoreSubmission() {
    }

    public double getScore_worth() {
        return score_worth;
    }

    public void setScore_worth(double score_worth) {
        this.score_worth = score_worth;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
