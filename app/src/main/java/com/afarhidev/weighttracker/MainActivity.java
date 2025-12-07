package com.afarhidev.weighttracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afarhidev.weighttracker.activity.LoginActivity;
import com.afarhidev.weighttracker.activity.WeightGridActivity;
import com.afarhidev.weighttracker.repo.WeightTrackerRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ask user for permission to send an SMS notification
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1001);
        } else {
            proceedToNextActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        proceedToNextActivity();
    }

    private void proceedToNextActivity() {
        // get an instance of the repository
        WeightTrackerRepository weightTrackerRepository = WeightTrackerRepository.getInstance(this);
        // check if there is an active session
        if (weightTrackerRepository.getCurrentSession() == null) {
            // if there is no active session, show the login page
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            // if the there is a session, that means a user is logged in
            startActivity(new Intent(this, WeightGridActivity.class));
        }
    }
}