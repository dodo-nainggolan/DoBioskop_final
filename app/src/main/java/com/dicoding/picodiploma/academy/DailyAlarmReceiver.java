package com.dicoding.picodiploma.academy;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import static android.app.AlarmManager.RTC_WAKEUP;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class DailyAlarmReceiver extends BroadcastReceiver {
    private final int ID_DAILY = 100;

    public DailyAlarmReceiver() {

    }
    @Override

    public void onReceive(Context context, Intent intent) {
        String title = context.getString(R.string.judul_reminder);
        String message = context.getString(R.string.pesan_reminder);
        int notifId = ID_DAILY;

        showAlarmNotification(context, title, message, notifId);
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_live_tv)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }
    public void setDailyAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 19);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.e(TAG, "setDailyAlarm: " + calendar.getTimeInMillis());
        Log.e(TAG, "setDailyAlarm: " + alarmManager.INTERVAL_DAY);
        if (alarmManager != null) {

            alarmManager.setInexactRepeating(RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(context, R.string.set_daily, Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isAlarmSet(Context context) {
        boolean alarmUp = (PendingIntent.getBroadcast(context, ID_DAILY,
                new Intent(context, DailyAlarmReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
        Log.e("Alarm", alarmUp + "OK");
        return alarmUp;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, R.string.remove_daily, Toast.LENGTH_SHORT).show();
        }

    }
}
