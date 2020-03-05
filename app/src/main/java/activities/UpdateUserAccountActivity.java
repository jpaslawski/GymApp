package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gymapp.R;

import models.User;
import sql.DatabaseHelper;

public class UpdateUserAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = UpdateUserAccountActivity.this;

    private EditText updateUsername;
    private EditText updateHeight;
    private EditText updateWeight;
    private EditText updateAge;
    private Button saveUpdate;
    private User user;
    private DatabaseHelper databaseHelper;
    private String userFromIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_account_activity);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        updateUsername = findViewById(R.id.updateUsername);
        updateWeight = findViewById(R.id.updateWeight);
        updateHeight = findViewById(R.id.updateHeight);
        updateAge = findViewById(R.id.updateAge);
        saveUpdate = findViewById(R.id.btnSaveUpdate);
    }

    private void initObjects() {
        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        userFromIntent = getIntent().getStringExtra("USER");
        databaseHelper = new DatabaseHelper(activity);
        user = databaseHelper.findUserByEmail(emailFromIntent);

        updateUsername.setText(user.getUsername());
        updateWeight.setText(user.getWeight().toString());
        updateHeight.setText(user.getHeight().toString());
        updateAge.setText(user.getAge().toString());
    }

    private void initListeners() {
        saveUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == saveUpdate) {
            updateSQLiteData();
            Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            profileIntent.putExtra("EMAIL", userFromIntent);
            startActivity(profileIntent);
        }
    }

    private void updateSQLiteData() {
        float weight, height;
        int age;

        weight = Float.valueOf(updateWeight.getText().toString());
        height = Float.valueOf(updateHeight.getText().toString());
        age = Integer.valueOf(updateAge.getText().toString());

        user.setUsername(updateUsername.getText().toString());
        user.setWeight(weight);
        user.setHeight(height);
        user.setAge(age);

        databaseHelper.updateUser(user);
    }
}
