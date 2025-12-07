package com.afarhidev.weighttracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.afarhidev.weighttracker.R;
import com.afarhidev.weighttracker.model.WeightEntry;
import com.afarhidev.weighttracker.repo.WeightTrackerRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EnterTargetWeightActivity extends AppCompatActivity {

    private WeightTrackerRepository weightTrackerRepository;
    private TextInputEditText weightEditText;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertargetweight);

        // get an instance of the repo and get the logged in user's id
        weightTrackerRepository = WeightTrackerRepository.getInstance(this);
        userId = weightTrackerRepository.getCurrentSession().getUserId();

        // get the weightEditView element
        weightEditText = findViewById(R.id.weightInputEditText);
    }

    public void onSaveButtonClick(View v) {
        // get the current date and the inputted weight from the View
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        String weightInputString = weightEditText.getText().toString();
        // if the weight input is not empty, then put it in the DB
        if (!weightInputString.isEmpty()) {
            float weightInputFloat = Float.parseFloat(weightInputString);
            WeightEntry weightEntry = new WeightEntry(userId, weightInputFloat, formattedDate, "target");
            if (weightTrackerRepository.getTargetWeightEntryForUser(userId) != null) {
                weightTrackerRepository.setTargetWeightEntryForUser(userId, weightInputFloat, formattedDate);
            } else {
                weightTrackerRepository.addWeightEntry(weightEntry);
            }
            // show the WeightGridActivity
            startActivity(new Intent(this, WeightGridActivity.class));
        }
    }
}
