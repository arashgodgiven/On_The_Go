package com.example.OnTheGo.ui.createTrails;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.OnTheGo.data.TrailDataSource;
import com.example.OnTheGo.data.TrailRepository;

/**
 * ViewModel provider factory to instantiate CreateTrailViewModel.
 * Required given CreateTrailViewModel has a non-empty constructor
 */
public class CreateTrailViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateTrailViewModel.class)) {
            return (T) new CreateTrailViewModel(TrailRepository.getInstance(new TrailDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
