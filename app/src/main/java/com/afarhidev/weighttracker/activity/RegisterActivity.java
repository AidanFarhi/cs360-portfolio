package com.afarhidev.weighttracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.afarhidev.weighttracker.R;
import com.afarhidev.weighttracker.model.User;
import com.afarhidev.weighttracker.repo.WeightTrackerRepository;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordRepeated;
    private WeightTrackerRepository weightTrackerRepository;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // get an instance of weightTrackerRepository
        weightTrackerRepository = WeightTrackerRepository.getInstance(this);

        // get the UI elements
        editTextEmail = findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = findViewById(R.id.editTextTextPassword2);
        editTextPasswordRepeated = findViewById(R.id.editTextTextPassword);
    }

    public void onLoginViewClick(View v) {
        // show the LoginActivity view
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onRegisterButtonClick(View v) {
        // get the email and passwords from the EditText views
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordRepeated = editTextPasswordRepeated.getText().toString().trim();
        // validate the inputs
        if (validateInputs(email, password, passwordRepeated)) {
            // if the inputs are valid, create the user in the DB,
            // clear the current session, add a new session,
            // and show the WeightGridActivity view
            User newUser = weightTrackerRepository.addUser(email, password);
            weightTrackerRepository.clearSession();
            weightTrackerRepository.addSession(newUser.getId());
            startActivity(new Intent(this, WeightGridActivity.class));
        } else {
            System.out.println("invalid");
        }
    }

    private boolean validateInputs(String email, String password, String passwordRepeated) {
        // perform validations for the email and passwords
        boolean emailIsValid = email != null && !email.isEmpty() && email.matches(EMAIL_REGEX);
        boolean passwordIsValid = password != null && password.length() > 8;
        boolean passwordRepeatedIsValid = passwordRepeated != null && passwordRepeated.length() > 8;
        boolean passwordsMatch = password != null && password.equals(passwordRepeated);
        return emailIsValid && passwordIsValid && passwordRepeatedIsValid && passwordsMatch;
    }
}
