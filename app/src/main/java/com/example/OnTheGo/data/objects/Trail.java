package com.example.OnTheGo.data.objects;

public class Trail {
    private String trailName;
    private String trailDifficulty;

    public Trail(String trailName, String trailDifficulty) {
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
