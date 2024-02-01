package com.example.OnTheGo.data.model;

/**
 * Data class that captures trail information for created trail retrieved from CreateTrailRepository
 */
public class CreatedTrail {

    private String trailName;
    private String trailDifficulty;

    public CreatedTrail(String trailName, String trailDifficulty) {
        this.trailName = trailName;
        this.trailDifficulty = trailDifficulty;
    }

    public String getTrailName() {
        return trailName;
    }

    public String getTrailDifficulty() {
        return trailDifficulty;
    }
}
