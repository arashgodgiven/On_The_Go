package com.example.OnTheGo.data;

import com.example.OnTheGo.data.model.CreatedTrail;

import java.io.IOException;

public class TrailDataSource {

    public Result<CreatedTrail> createTrail(String trailName, String trailDifficulty) {

        try {
            CreatedTrail fakeTrail =
                    new CreatedTrail(
                            java.util.UUID.randomUUID().toString(),
                            "Difficulty Placeholder");
            return new Result.Success<>(fakeTrail);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error creating trail", e));
        }
    }
}
