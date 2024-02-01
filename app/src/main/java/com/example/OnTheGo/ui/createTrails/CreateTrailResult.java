package com.example.OnTheGo.ui.createTrails;

import androidx.annotation.Nullable;

/**
 * Trail Creation Authentication result : success (trail details) or error message.
 */
class CreateTrailResult {
    @Nullable
    private CreatedTrailView success;
    @Nullable
    private Integer error;

    CreateTrailResult(@Nullable Integer error) {
        this.error = error;
    }

    CreateTrailResult(@Nullable CreatedTrailView success) {
        this.success = success;
    }

    @Nullable
    CreatedTrailView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
