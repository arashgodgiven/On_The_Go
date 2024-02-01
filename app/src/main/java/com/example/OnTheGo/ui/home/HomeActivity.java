package com.example.OnTheGo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.OnTheGo.data.objects.Trail;
import com.example.OnTheGo.data.objects.Trails;
import androidx.appcompat.app.AppCompatActivity;
import com.example.OnTheGo.R;
import com.example.OnTheGo.databinding.ActivityHomeBinding;
import com.example.OnTheGo.ui.createTrails.CreateTrailMapsActivity;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    ListView listView;
    String[] trailNames = {"St Georges Trail", "Ambles Trail", "Lonsdale Trail", "Lonsdale Trail", "Lonsdale Trail", "Lonsdale Trail"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = findViewById(R.id.trailsListView);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        final Button createTrailButton = binding.createTrailButton;
        final Button recordNavButton = binding.recordNavButton;
        final Button mapsNavButton = binding.mapsNavButton;
        final Button groupsNavButton = binding.groupsNavButton;
        final Button accountNavButton = binding.accountNavButton;

        recordNavButton.setEnabled(false);

        createTrailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Initiate create trail procedure.
                Intent intent = new Intent(HomeActivity.this, CreateTrailMapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    private class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Trails.getTrailList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.trail_row_data, null);
            TextView trailDifficulty = view1.findViewById(R.id.trailDifficultyTextview);
            TextView trailName = view1.findViewById(R.id.trailNameTextview);
            TextView trailDistanceFromUser = view1.findViewById(R.id.trailDistanceTextView);
            TextView trailLength = view1.findViewById(R.id.trailLengthTextview);
            TextView trailTime = view1.findViewById(R.id.trailTimeTextview);
            TextView trailElevation = view1.findViewById(R.id.trailElevationTextview);

            ImageView trailImage = view1.findViewById(R.id.trailImageView);

            Trail currentTrail = Trails.getTrailList().get(position);
            trailName.setText(currentTrail.getTrailName());
            trailDifficulty.setText(currentTrail.getTrailDifficulty());

            return view1;
        }
    }
}
