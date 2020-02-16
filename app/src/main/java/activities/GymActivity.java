package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gymapp.R;

public class GymActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener action
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_diet:
                    Intent intentDiet = new Intent(getApplicationContext(), DietActivity.class);
                    intentDiet.putExtra("EMAIL", emailFromIntent);
                    startActivity(intentDiet);
                    break;

                case R.id.nav_profile:
                    Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                    intentProfile.putExtra("EMAIL", emailFromIntent);
                    startActivity(intentProfile);
                    break;
            }
            return false;
        }
    };

    private final AppCompatActivity activity = GymActivity.this;

    private BottomNavigationView bottomNavigationView;

    private MenuItem navGym;
    private MenuItem navDiet;
    private MenuItem navProfile;

    String emailFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_activity);

        initViews();
        initObjects();
        bottomNavigationFix();
        bottomNavigationView.setOnNavigationItemSelectedListener(action);
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navGym = bottomNavigationView.getMenu().findItem(R.id.nav_gym);
        navDiet = bottomNavigationView.getMenu().findItem(R.id.nav_diet);
        navProfile = bottomNavigationView.getMenu().findItem(R.id.nav_profile);
    }

    private void initObjects() {
        emailFromIntent = getIntent().getStringExtra("EMAIL");
    }

    private void bottomNavigationFix() {
        navGym.setTitle("Gym");
        navDiet.setTitle("");
        navProfile.setTitle("");
        navGym.setChecked(true);
    }
}
