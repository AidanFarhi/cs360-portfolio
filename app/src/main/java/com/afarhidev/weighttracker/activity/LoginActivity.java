package com.afarhidev.weighttracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.afarhidev.weighttracker.R;
import com.afarhidev.weighttracker.model.User;
import com.afarhidev.weighttracker.repo.WeightTrackerRepository;

public class LoginActivity extends AppCompatActivity {

    private TextView emailTextView;
    private TextView passwordTextView;
    private WeightTrackerRepository weightTrackerRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get email and password text entry views
        emailTextView = findViewById(R.id.editTextTextEmailAddress);
        passwordTextView = findViewById(R.id.editTextTextPassword);

        // get an instance of WeightTrackerRepository
        weightTrackerRepository = WeightTrackerRepository.getInstance(this);
    }

    public void onRegisterViewClick(View v) {
        // start the RegisterActivity
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onLoginButtonClick(View v) {
        // get the email and password from the EditTexts
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();
        // check for a user in the DB with that email and password
        User user = weightTrackerRepository.getUser(email, password);
        // if a user is found, clear the current session, create a new one,
        // and show the WeightGridActivity view
        if (user != null) {
            weightTrackerRepository.clearSession();
            weightTrackerRepository.addSession(user.getId());
            startActivity(new Intent(this, WeightGridActivity.class));
        } else {
            System.out.println("invalid login");
        }
    }
}
