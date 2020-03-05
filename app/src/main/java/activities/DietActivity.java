package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gymapp.R;

public class DietActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener action
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_gym:
                    Intent intentGym = new Intent(getApplicationContext(), GymActivity.class);
                    intentGym.putExtra("EMAIL", emailFromIntent);
                    startActivity(intentGym);
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

    private final AppCompatActivity activity = DietActivity.this;

    private BottomNavigationView bottomNavigationView;

    private MenuItem navGym;
    private MenuItem navDiet;
    private MenuItem navProfile;

    private String emailFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_activity);

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
        navGym.setTitle("");
        navDiet.setTitle("Diet");
        navProfile.setTitle("");
        navDiet.setChecked(true);
    }
}
