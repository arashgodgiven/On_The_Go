package com.example.OnTheGo.ui.createTrails;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.OnTheGo.data.objects.Trail;
import com.example.OnTheGo.data.objects.Trails;
import com.example.OnTheGo.ui.home.HomeActivity;
import com.example.OnTheGo.ui.login.LoginActivity;
import com.example.OnTheGo.ui.login.LoginViewModel;
import com.example.OnTheGo.ui.login.LoginViewModelFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.OnTheGo.R;
import com.example.OnTheGo.databinding.ActivityCreateTrailMapsBinding;

public class CreateTrailMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap gMap;
    private CreateTrailViewModel createTrailViewModel;
    private ActivityCreateTrailMapsBinding binding;

    private EditText trailNameEditText;
    private RadioGroup trailDifficultyRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTrailMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createTrailViewModel = new ViewModelProvider(this, new CreateTrailViewModelFactory())
                .get(CreateTrailViewModel.class);


        // Map process start -----------------------------------------------------------------------
        // Check for map access permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getApplicationContext(), "maps is null", Toast.LENGTH_SHORT).show();
        }
        // Map process end -----------------------------------------------------------------------


        final Button createTrailButton = binding.createTrailButton;
        createTrailButton.setEnabled(false);
        trailNameEditText = findViewById(R.id.trailNameEditText);
        trailDifficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);

        createTrailViewModel.getCreateTrailFormState().observe(this, new Observer<CreateTrailFormState>() {
            @Override
            public void onChanged(CreateTrailFormState createTrailFormState) {
                if (createTrailFormState == null) {
                    return;
                }
                createTrailButton.setEnabled(createTrailFormState.isDataValid());
                if (createTrailFormState.getTrailNameError() != null) {
                    trailNameEditText.setError(getString(createTrailFormState.getTrailNameError()));
                }
                if (createTrailFormState.getTrailDifficultyError() != null) {
                    Toast.makeText(CreateTrailMapsActivity.this, "Choose a trail difficulty", Toast.LENGTH_LONG).show();
                }
            }
        });

        createTrailViewModel.getCreateTrailResult().observe(this, new Observer<CreateTrailResult>() {
            @Override
            public void onChanged(CreateTrailResult createTrailResult) {
                if (createTrailResult == null) {
                    return;
                }
                if (createTrailResult.getError() != null) {
                    showCreateTrailFailed(createTrailResult.getError());
                }
                if (createTrailResult.getSuccess() != null) {
                    updateUiWithUser(createTrailResult.getSuccess());

                    Toast.makeText(CreateTrailMapsActivity.this, "Trail created successfully", Toast.LENGTH_LONG).show();

                    trailNameEditText.getText().clear();
                    trailDifficultyRadioGroup.clearCheck();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                createTrailViewModel.createTrailDataChanged(trailNameEditText.getText().toString(),
                        trailDifficultyRadioGroup.toString());
            }
        };
        trailNameEditText.addTextChangedListener(afterTextChangedListener);
//        trailDifficultyRadioGroup.addTextChangedListener();

        createTrailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Initiate create trail procedure.
                createTrailViewModel.createTrail(trailNameEditText.getText().toString(),
                        trailDifficultyRadioGroup.toString());

//                String trailName = trailNameEditText.getText().toString().trim();
//
//                int selectedDifficultyId = trailDifficultyRadioGroup.getCheckedRadioButtonId();
//                RadioButton selectedRadioButton = findViewById(selectedDifficultyId);
//                String trailDifficulty = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
//
//                Trail newTrail = new Trail(trailName, trailDifficulty);
//                Trails.addTrail(newTrail);
//
//                onBackPressed();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable My Location
                enableMyLocation();
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (gMap != null) {
            // Enable My Location layer
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            gMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Add a marker at the current location and move the camera
        LatLng currentLocation = new LatLng(0, 0); // Replace with the user's actual location
        gMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker at Current Location"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        gMap = googleMap;
//
//        // Check and request location permission if needed
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            gMap.setMyLocationEnabled(true);
//
//            // Get the last known location
//            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, location -> {
//                        if (location != null) {
//                            // Use the location to update the map, e.g., move camera to this location
//                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
//                        }
//                    });
//        } else {
//            // Request location permission
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//        }
//    }

    private void updateUiWithUser(CreatedTrailView model) {
        String trailName = trailNameEditText.getText().toString().trim();

        int selectedDifficultyId = trailDifficultyRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedDifficultyId);
        String trailDifficulty = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";

        Trail newTrail = new Trail(trailName, trailDifficulty);
        Trails.addTrail(newTrail);

        onBackPressed();
    }

    private void showCreateTrailFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

}