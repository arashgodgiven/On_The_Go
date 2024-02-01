package com.example.OnTheGo.data;

import com.example.OnTheGo.data.model.CreatedTrail;

public class TrailRepository {

    private static volatile TrailRepository instance;

    private TrailDataSource dataSource;
    private CreatedTrail trail = null;

    private TrailRepository(TrailDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static TrailRepository getInstance(TrailDataSource dataSource) {
        if (instance == null) {
            instance = new TrailRepository(dataSource);
        }
        return instance;
    }

    public boolean isTrailCreated() {
        return trail != null;
    }

    private void setCreatedTrail(CreatedTrail trail) {
        this.trail = trail;
    }

    public Result<CreatedTrail> createTrail(String trailName, String trailDifficulty) {
        Result<CreatedTrail> result = dataSource.createTrail(trailName, trailDifficulty);
        if (result instanceof Result.Success) {
            setCreatedTrail(((Result.Success<CreatedTrail>) result).getData());
        }
        return result;
    }
}
