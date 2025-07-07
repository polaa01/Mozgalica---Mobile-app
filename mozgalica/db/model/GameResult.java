package com.example.mozgalica.db.model;

public class GameResult {

    private int id;
    private String game;
    private String username;
    private int score;


    public GameResult()
    {
        super();
    }
    public GameResult(String game, String username, int score) {

        this.game = game;
        this.username = username;
        this.score = score;
    }

    public GameResult (int id, String game, String username, int score)
    {
        this.id = id;
        this.game = game;
        this.username = username;
        this.score = score;
    }


    public int getId() {
        return id;
    }

    public String getGame() {
        return game;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
