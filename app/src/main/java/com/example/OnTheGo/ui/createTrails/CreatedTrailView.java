package com.example.OnTheGo.ui.createTrails;

/**
 * Class displaying updated trail info to the UI.
 */
public class CreatedTrailView {
    private String trailName;
    //... other data fields that may be accessible to the UI

    CreatedTrailView(String trailName) {
        this.trailName = trailName;
    }

    String getTrailName() {
        return trailName;
    }
}
