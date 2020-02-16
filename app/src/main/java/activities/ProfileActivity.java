package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gymapp.R;

import models.User;
import sql.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

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

                case R.id.nav_diet:
                    Intent intentDiet = new Intent(getApplicationContext(), DietActivity.class);
                    intentDiet.putExtra("EMAIL", emailFromIntent);
                    startActivity(intentDiet);
                    break;
            }
            return false;
        }
    };

    private final AppCompatActivity activity = ProfileActivity.this;

    private BottomNavigationView bottomNavigationView;

    private MenuItem navGym;
    private MenuItem navDiet;
    private MenuItem navProfile;

    private Button viewAllUsers;
    private Button updateAccount;
    private Button deleteAccount;
    private Button logout;

    private TextView usernameTextView;
    private TextView weightTextView;
    private TextView heightTextView;
    private TextView ageTextView;

    private DatabaseHelper databaseHelper;

    private User user;

    private String emailFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        initViews();
        initObjects();
        initListeners();
        bottomNavigationFix();
        bottomNavigationView.setOnNavigationItemSelectedListener(action);
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navGym = bottomNavigationView.getMenu().findItem(R.id.nav_gym);
        navDiet = bottomNavigationView.getMenu().findItem(R.id.nav_diet);
        navProfile = bottomNavigationView.getMenu().findItem(R.id.nav_profile);

        viewAllUsers = findViewById(R.id.viewAllUsers);
        updateAccount = findViewById(R.id.updateAccountButton);
        deleteAccount = findViewById(R.id.deleteAccountButton);
        logout = findViewById(R.id.logoutButton);

        usernameTextView = findViewById(R.id.profileUsernameTextView);
        weightTextView = findViewById(R.id.profileWeightTextView);
        heightTextView = findViewById(R.id.profileHeightTextView);
        ageTextView = findViewById(R.id.profileAgeTextView);
    }

    private void initListeners() {
        viewAllUsers.setOnClickListener(this);
        updateAccount.setOnClickListener(this);
        deleteAccount.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    private void initObjects() {
        emailFromIntent = getIntent().getStringExtra("EMAIL");
        databaseHelper = new DatabaseHelper(activity);
        user = databaseHelper.findUserByEmail(emailFromIntent);

        usernameTextView.setText(user.getUsername());
        weightTextView.setText(user.getWeight().toString());
        heightTextView.setText(user.getHeight().toString());
        ageTextView.setText(user.getAge().toString());
    }

    private void bottomNavigationFix() {
        navGym.setTitle("");
        navDiet.setTitle("");
        navProfile.setTitle("Profile");
        navProfile.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.viewAllUsers:
                Intent viewUsers = new Intent(activity, UserListActivity.class);
                viewUsers.putExtra("EMAIL", emailFromIntent);
                startActivity(viewUsers);
                break;

            case R.id.updateAccountButton:
                Intent updateIntent = new Intent(activity, UpdateUserAccountActivity.class);
                updateIntent.putExtra("EMAIL", emailFromIntent);
                updateIntent.putExtra("USER", emailFromIntent);
                startActivity(updateIntent);
                break;

            case R.id.deleteAccountButton:
                Intent deleteIntent = new Intent(activity, DeleteUserAccountActivity.class);
                deleteIntent.putExtra("EMAIL", emailFromIntent);
                deleteIntent.putExtra("USER", emailFromIntent);
                startActivity(deleteIntent);
                break;

            case R.id.logoutButton:
                Intent logoutIntent = new Intent(activity, LoginActivity.class);
                startActivity(logoutIntent);
                break;

        }
    }
}
