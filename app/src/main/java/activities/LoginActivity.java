package activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputLayout;
import com.gymapp.R;

import helpers.InputValidation;
import sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonLogin;

    private TextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = findViewById(R.id.textInputLayoutLogin);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        editTextEmail = findViewById(R.id.editLogin);
        editTextPassword = findViewById(R.id.editPassword);

        buttonLogin = findViewById(R.id.btnLogin);

        textViewLinkRegister = findViewById(R.id.register);
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                verifyFromSQLite();
                break;
            case R.id.register:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        if (databaseHelper.checkUser(editTextEmail.getText().toString().trim()
            , editTextPassword.getText().toString().trim())) {

            Intent gymIntent = new Intent(activity, GymActivity.class);
            gymIntent.putExtra("EMAIL", editTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(gymIntent);
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        editTextEmail.setText(null);
        editTextPassword.setText(null);
    }
}