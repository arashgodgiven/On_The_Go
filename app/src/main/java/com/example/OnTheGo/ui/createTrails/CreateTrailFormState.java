package com.example.OnTheGo.ui.createTrails;

import androidx.annotation.Nullable;

/**
 * Data validation state of the create trail form.
 */
class CreateTrailFormState {
    @Nullable
    private Integer trailNameError;
    @Nullable
    private Integer trailDifficultyError;
    private boolean isDataValid;

    CreateTrailFormState(@Nullable Integer trailNameError, @Nullable Integer trailDifficultyError) {
        this.trailNameError = trailNameError;
        this.trailDifficultyError = trailDifficultyError;
        this.isDataValid = false;
    }

    CreateTrailFormState(boolean isDataValid) {
        this.trailNameError = null;
        this.trailDifficultyError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getTrailNameError() {
        return trailNameError;
    }

    @Nullable
    Integer getTrailDifficultyError() {
        return trailDifficultyError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
