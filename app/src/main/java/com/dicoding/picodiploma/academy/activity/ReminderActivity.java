package com.dicoding.picodiploma.academy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dicoding.picodiploma.academy.DailyAlarmReceiver;
//import com.dicoding.picodiploma.academy.NewMovieAlarmReceiver;
import com.dicoding.picodiploma.academy.R;

public class ReminderActivity extends AppCompatActivity {

    Switch dailyReminder;
    Switch newMoviesReminder;
    DailyAlarmReceiver dailyAlarmReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);
        setTitle(R.string.reminder_settings);

        dailyReminder = findViewById(R.id.daily_reminder);
        newMoviesReminder = findViewById(R.id.new_reminder);
        dailyAlarmReceiver = new DailyAlarmReceiver();

//        newMovieAlarmReceiver = new NewMovieAlarmReceiver();

        if (dailyAlarmReceiver.isAlarmSet(this)) {
            dailyReminder.setChecked(true);
        }
//        if (newMovieAlarmReceiver.isAlarmSet(this)) {
//            sw_newReminder.setChecked(true);
//        }


//        newMoviesReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    newMovieAlarmReceiver.setNewMovieAlarm(getApplicationContext());
//                } else {
//                    newMovieAlarmReceiver.cancelAlarm(getApplicationContext());
//                }
//            }
//        });

        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dailyAlarmReceiver.setDailyAlarm(getApplicationContext());
                } else {
                    dailyAlarmReceiver.cancelAlarm(getApplicationContext());

                }
            }
        });


    }
}
