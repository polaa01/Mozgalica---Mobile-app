package com.example.mozgalica;

import java.io.Serializable;

public class GameDetails implements Serializable {

    private String title;
    private String description;
    private String rules;
    private String scoring;
    private String youtubeUrl;

    public String getTitle() {
        return title;
    }

    public GameDetails(String title, String description, String rules, String scoring, String youtubeUrl) {
        this.title = title;
        this.description = description;
        this.rules = rules;
        this.scoring = scoring;
        this.youtubeUrl = youtubeUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getRules() {
        return rules;
    }

    public String getScoring() {
        return scoring;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public GameDetails()
    {
        super();
    }
}
