package com.example.OnTheGo.ui.createTrails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.OnTheGo.data.TrailRepository;
import com.example.OnTheGo.data.Result;
import com.example.OnTheGo.data.model.CreatedTrail;
import com.example.OnTheGo.R;



public class CreateTrailViewModel extends ViewModel {

    private MutableLiveData<CreateTrailFormState> createTrailFormState = new MutableLiveData<>();
    private MutableLiveData<CreateTrailResult> createTrailResult = new MutableLiveData<>();
    private TrailRepository trailRepository;

    CreateTrailViewModel(TrailRepository createTrailRepository) {
        this.trailRepository = createTrailRepository;
    }

    LiveData<CreateTrailFormState> getCreateTrailFormState() {
        return createTrailFormState;
    }

    LiveData<CreateTrailResult> getCreateTrailResult() {
        return createTrailResult;
    }

    public void createTrail(String trailName, String trailDifficulty) {
        Result<CreatedTrail> result = trailRepository.createTrail(trailName, trailDifficulty);

        if (result instanceof Result.Success) {
            CreatedTrail data = ((Result.Success<CreatedTrail>) result).getData();
            createTrailResult.setValue(new CreateTrailResult(new CreatedTrailView(data.getTrailName())));
        } else {
            createTrailResult.setValue(new CreateTrailResult(R.string.create_trail_failed));
        }
    }

    public void createTrailDataChanged(String trailName, String trailDifficulty) {
        if (!isTrailNameValid(trailName)) {
            createTrailFormState.setValue(new CreateTrailFormState(R.string.invalid_trail_name, null));
        } else if (!isTrailDifficultyValid(trailDifficulty)) {
            createTrailFormState.setValue(new CreateTrailFormState(null, R.string.invalid_trail_difficulty));
        } else {
            createTrailFormState.setValue(new CreateTrailFormState(true));
        }
    }

    private boolean isTrailNameValid(String trailName) {
        if (trailName == null) {
            return false;
        }
        if (trailName.matches(".*\\d.*") || !trailName.matches("[a-zA-Z]+")) {
            return false;
        } else {
            return !trailName.trim().isEmpty();
        }
    }

    private boolean isTrailDifficultyValid(String trailDifficulty) {
        if (trailDifficulty == null) {
            return false;
        } else {
            return !trailDifficulty.trim().isEmpty();
        }
    }
}
