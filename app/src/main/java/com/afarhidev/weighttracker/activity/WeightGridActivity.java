package com.afarhidev.weighttracker.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.afarhidev.weighttracker.R;
import com.afarhidev.weighttracker.model.WeightEntry;
import com.afarhidev.weighttracker.repo.WeightTrackerRepository;

import java.util.List;
import java.util.Locale;

public class WeightGridActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private WeightTrackerRepository weightTrackerRepository;
    private TextView targetWeightView;
    private TextView currentWeightView;
    private long loggedInUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // boilerplate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weightgrid);

        // get the UI elements
        tableLayout = findViewById(R.id.tableLayout);
        targetWeightView = findViewById(R.id.targetWeightTextView);
        currentWeightView = findViewById(R.id.currentWeightTextView);

        // get an instance of WeightTrackerRepository
        weightTrackerRepository = WeightTrackerRepository.getInstance(this);

        // get the logged in user's id from the session
        loggedInUserId = weightTrackerRepository.getCurrentSession().getUserId();

        // get all of the daily weight entries for the currently logged
        // in user and populate the TableLayout
        populateTableLayout();

        // set the target weight
        populateTargetWeightView();

        // set the current weight
        populateCurrentWeightView();

        // check if target weight has been reached.
        checkTargetWeightReached();
    }

    private void checkTargetWeightReached() {
        // check if user has allowed SMS notifications
        boolean hasPermissionToSendSMS = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED;
        // get the user's current and target weight
        WeightEntry currentWeightEntry = weightTrackerRepository.getCurrentWeightForUser(loggedInUserId);
        WeightEntry targetWeightEntry = weightTrackerRepository.getTargetWeightEntryForUser(loggedInUserId);
        // check if currentWeightEntry and targetWeightEntry are not null and if
        // user has granted access to send an SMS notification
        if (currentWeightEntry != null && targetWeightEntry != null && hasPermissionToSendSMS) {
            float currentWeight = currentWeightEntry.getWeight();
            float targetWeight = targetWeightEntry.getWeight();
            if (currentWeight <= targetWeight) {
                // send a message to the user if they have hit their target weight
                SmsManager smsManager = getSystemService(SmsManager.class);
                smsManager.sendTextMessage(
                        "1234567890",
                        null,
                        "You have hit your target weight!",
                        null,
                        null
                );
            }
        }
    }

    private void populateTableLayout() {
        // remove all the table rows
        tableLayout.removeAllViews();
        // get all weight entries for a user
        List<WeightEntry> dailyWeightEntries = weightTrackerRepository.getDailyWeightEntriesForUser(loggedInUserId);
        // generate table rows from the weight entries
        dailyWeightEntries.forEach(weightEntry -> {
            TableRow tr = generateRowFromWeightEntry(weightEntry);
            tableLayout.addView(tr);
        });
    }

    private void populateCurrentWeightView() {
        // get the user's current weight
        WeightEntry currentWeight = weightTrackerRepository.getCurrentWeightForUser(loggedInUserId);
        // if the user has a current weight, set it in the view
        if (currentWeight != null) {
            currentWeightView.setText(String.format(Locale.ENGLISH,"%.0f lb", currentWeight.getWeight()));
        } else {
            currentWeightView.setText("0.00 lb");
        }
    }

    private void populateTargetWeightView() {
        // get the user's target weight
        WeightEntry targetWeight = weightTrackerRepository.getTargetWeightEntryForUser(loggedInUserId);
        // if the user has a target weight, set it in the view
        if (targetWeight != null) {
            targetWeightView.setText(String.format(Locale.ENGLISH,"%.0f lb", targetWeight.getWeight()));
        } else {
            targetWeightView.setText("0.00 lb");
        }
    }

    private TableRow generateRowFromWeightEntry(WeightEntry weightEntry) {
        // row for displaying fields
        TableRow row = new TableRow(this);
        row.setTag(weightEntry.getId());

        // field for displaying the weight
        TextView weightView = new TextView(this);
        weightView.setId(View.generateViewId()); // dynamic ID if needed
        weightView.setLayoutParams(new TableRow.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 84, getResources().getDisplayMetrics()),
                TableRow.LayoutParams.MATCH_PARENT
        ));
        weightView.setText(String.format(Locale.ENGLISH,"%.2f lb", weightEntry.getWeight()));
        weightView.setGravity(Gravity.CENTER_VERTICAL);
        weightView.setBackgroundResource(R.drawable.textview_top_border);
        row.addView(weightView);

        // field for displaying the date
        TextView dateView = new TextView(this);
        dateView.setId(View.generateViewId());
        dateView.setLayoutParams(new TableRow.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 93, getResources().getDisplayMetrics()),
                TableRow.LayoutParams.MATCH_PARENT
        ));
        dateView.setText(weightEntry.getDate());
        dateView.setGravity(Gravity.CENTER_VERTICAL);
        dateView.setBackgroundResource(R.drawable.textview_top_border);
        row.addView(dateView);

        // edit button
        ImageButton editButton = new ImageButton(this);
        editButton.setId(View.generateViewId());
        editButton.setTag(weightEntry.getId());
        editButton.setLayoutParams(new TableRow.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 67, getResources().getDisplayMetrics()),
                TableRow.LayoutParams.MATCH_PARENT
        ));
        editButton.setImageResource(android.R.drawable.ic_menu_edit);
        editButton.setBackgroundResource(R.drawable.textview_top_border);
        editButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        editButton.setOnClickListener(this::onEditDailyWeightButtonClick);
        row.addView(editButton);

        // delete button
        ImageButton deleteBtn = new ImageButton(this);
        deleteBtn.setId(View.generateViewId());
        deleteBtn.setTag(weightEntry.getId());
        deleteBtn.setLayoutParams(new TableRow.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()),
                TableRow.LayoutParams.MATCH_PARENT
        ));
        deleteBtn.setImageResource(android.R.drawable.ic_menu_delete);
        deleteBtn.setBackgroundResource(R.drawable.textview_top_border);
        deleteBtn.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        deleteBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        deleteBtn.setOnClickListener(this::onDeleteButtonClick);
        row.addView(deleteBtn);

        return row;
    }

    public void onAddWeightEntryButtonClick(View view) {
        // show the daily weight activity when user clicks the button
        startActivity(new Intent(this, EnterDailyWeightActivity.class));
    }

    private void onEditDailyWeightButtonClick(View view) {
        // get the weight entry id from the view
        long weightEntryId = (long) view.getTag();
        // create an Intent and set a field to pass to the EnterDailyWeightActivity
        Intent intent = new Intent(this, EnterDailyWeightActivity.class);
        intent.putExtra("weightEntryId", weightEntryId);
        // show the weight entry activity
        startActivity(intent);
    }

    private void onDeleteButtonClick(View view) {
        // delete the weight entry
        weightTrackerRepository.deleteWeightEntry((long) view.getTag());
        // re-hydrate the UI
        populateCurrentWeightView();
        populateTableLayout();
    }

    public void onLogoutViewClick(View view) {
        // clear the active session from the DB
        weightTrackerRepository.clearSession();
        // show the login activity
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onEditTargetWeightViewClick(View v) {
        // show the target weight entry activity
        startActivity(new Intent(this, EnterTargetWeightActivity.class));
    }
}
