package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.gymapp.R;

import helpers.InputValidation;
import models.User;
import sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutPasswordCmp;

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordCmp;

    private Button buttonRegister;

    private TextView textViewLinkLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutPasswordCmp = findViewById(R.id.textInputLayoutPasswordCmp);

        editTextUsername = findViewById(R.id.editUsername);
        editTextEmail = findViewById(R.id.editEmail);
        editTextPassword = findViewById(R.id.editPassword_r);
        editTextPasswordCmp = findViewById(R.id.editPassword_cmp);

        buttonRegister = findViewById(R.id.btnRegister);

        textViewLinkLogin = findViewById(R.id.login);
    }

    private void initListeners() {
        buttonRegister.setOnClickListener(this);
        textViewLinkLogin.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                postDataToSQLite();
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.login:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextUsername, textInputLayoutUsername, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(editTextPassword, editTextPasswordCmp, textInputLayoutPasswordCmp,
                getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(editTextEmail.getText().toString().trim())) {
            user.setUsername(editTextUsername.getText().toString().trim());
            user.setEmail(editTextEmail.getText().toString().trim());
            user.setPassword(editTextPassword.getText().toString().trim());
            user.setWeight((float) 0);
            user.setHeight((float) 0);
            user.setAge(0);

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        editTextUsername.setText(null);
        editTextEmail.setText(null);
        editTextPassword.setText(null);
        editTextPasswordCmp.setText(null);
    }
}
