package com.afarhidev.weighttracker.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.afarhidev.weighttracker.R;
import com.afarhidev.weighttracker.model.WeightEntry;
import com.afarhidev.weighttracker.repo.WeightTrackerRepository;

import java.util.Calendar;
import java.util.Locale;

public class EnterDailyWeightActivity extends AppCompatActivity {

    WeightTrackerRepository weightTrackerRepository;
    private EditText dailyWeightEntryText;
    private EditText dateEditText;
    private long userId;
    private long weightEntryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterdailyweight);

        // get an instance of the repo and get the logged in user's id
        weightTrackerRepository = WeightTrackerRepository.getInstance(this);
        userId = weightTrackerRepository.getCurrentSession().getUserId();

        // get the weight entry and date entry
        dailyWeightEntryText = findViewById(R.id.dailyWeightInputEditText);
        dateEditText = findViewById(R.id.dateEditText);

        // get the weightEntryId from the Intent
        weightEntryId = getIntent().getLongExtra("weightEntryId", -1);
    }

    public void onDateEditTextClick(View v) {
        // show a date picker when the user clicks the edit date view
        EditText dateEditTextView = (EditText) v;
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format to yyyy-MM-dd
                    String formattedDate = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                    );
                    dateEditTextView.setText(formattedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    public void onSaveDailyWeightButtonClick(View v) {
        // get the weight and date from the text views
        String dailyWeightString = dailyWeightEntryText.getText().toString();
        String date = dateEditText.getText().toString();
        // check if the user has entered a date and weight
        if (!dailyWeightString.isEmpty() && !date.isEmpty()) {
            float dailyWeight = Float.parseFloat(dailyWeightString);
            WeightEntry dailyWeightEntry = new WeightEntry(userId, dailyWeight, date, "daily");
            // if this is an update, the weightEntryId will be greater than -1
            if (weightEntryId > -1) {
                weightTrackerRepository.updateWeightEntry(dailyWeight, date, weightEntryId);
            } else {
                weightTrackerRepository.addWeightEntry(dailyWeightEntry);
            }
            // show the WeightGridActivity
            startActivity(new Intent(this, WeightGridActivity.class));
        }
    }
}
