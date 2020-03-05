package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gymapp.R;

import models.User;
import sql.DatabaseHelper;

public class DeleteUserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = DeleteUserAccountActivity.this;

    private TextView textViewUsername;
    private TextView alertDelete;
    private Button deleteUser;
    private Button cancelAction;
    private models.User user;
    private DatabaseHelper databaseHelper;
    private String userFromIntent;
    private Boolean ownAccountDeleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account_activity);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        textViewUsername = findViewById(R.id.deleteUsername);
        alertDelete = findViewById(R.id.alertDelete);

        deleteUser = findViewById(R.id.btnDelete);
        cancelAction = findViewById(R.id.btnCancelDelete);
    }

    private void initListeners() {
        deleteUser.setOnClickListener(this);
        cancelAction.setOnClickListener(this);
    }

    private void initObjects() {
        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        userFromIntent = getIntent().getStringExtra("USER");
        databaseHelper = new DatabaseHelper(activity);
        user = databaseHelper.findUserByEmail(emailFromIntent);

        textViewUsername.setText(user.getUsername());

        if (emailFromIntent.equals(userFromIntent)) {
            alertDelete.setVisibility(View.VISIBLE);
            ownAccountDeleted = true;
        } else {
            alertDelete.setVisibility(View.GONE);
            ownAccountDeleted = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                databaseHelper.deleteUser(user);
                if (ownAccountDeleted) {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                } else {
                    Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    profileIntent.putExtra("EMAIL", userFromIntent);
                    startActivity(profileIntent);
                }
                break;
            case R.id.btnCancelDelete:
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                profileIntent.putExtra("EMAIL", userFromIntent);
                startActivity(profileIntent);
        }
    }
}
