package com.firstapp.kyecothreads;


public class LeaderboardItem {
    private String userId;
    private String userName;
    private int points;
    private int rank;

    public LeaderboardItem(String userId, String userName, int points) {
        this.userId = userId;
        this.userName = userName;
        this.points = points;
        this.rank = 0;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}



