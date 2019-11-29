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
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.dicoding.picodiploma.academy.adapter.MoviesAdapter;
import com.dicoding.picodiploma.academy.db.FavoriteFilmHelper;
import com.dicoding.picodiploma.academy.entity.Movies;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NewMovieAlarmReceiver extends BroadcastReceiver {

    private final int ID_NEW_MOVIE = 101;
    private MovieResult result;
    private List<Movies> data;
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();
    MoviesAdapter adapter;

    ArrayList<Movies> moviesFetched = new ArrayList<>();

    public NewMovieAlarmReceiver() {

    }

    public class MovieResult {
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        final int notifId = ID_NEW_MOVIE;
        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(today);

//        adapter = new MoviesAdapter();
//        adapter.notifyDataSetChanged();


    }

    public void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "New Movie Release Channel";

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

    public void setNewMovieAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NewMovieAlarmReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NEW_MOVIE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.e("DICOBA", "JALAN" );

        new NotifAsync(moviesFetched,context).execute();

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(context, R.string.set_new_movie, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Tidak Ter set", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isAlarmSet(Context context) {
        boolean alarmUp = (PendingIntent.getBroadcast(context, ID_NEW_MOVIE,
                new Intent(context, NewMovieAlarmReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
        return alarmUp;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NewMovieAlarmReceiver.class);
        int requestCode = ID_NEW_MOVIE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, R.string.remove_new_movie, Toast.LENGTH_SHORT).show();
    }

    private static class NotifAsync extends AsyncTask<Void, Void, ArrayList<Movies>> {
        ArrayList<Movies> moviesFetched;
        Context context;


        private static final String API_KEY = "3a92c0f2b44f88d27ffe2b2de5466cc6";
        private ArrayList<Movies> mData;

        private NotifAsync(ArrayList<Movies> moviesFetched, Context context) {
            this.moviesFetched = moviesFetched;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movies> doInBackground(Void... voids) {
            SyncHttpClient client = new SyncHttpClient();
            final ArrayList<Movies> moviesItemses = new ArrayList<>();
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());

            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + formatter.format(date) + "&primary_release_date.lte=" + formatter.format(date);

            client.get ( url, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String ( responseBody );
                        JSONObject responseObject = new JSONObject ( result );
                        JSONArray list = responseObject.getJSONArray ( "results" );

                        for (int i = 0; i < list.length (); i++) {
                            JSONObject movies = list.getJSONObject ( i );
                            Movies moviesItems = new Movies ( movies );
                            moviesItemses.add ( moviesItems );
                        }
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

            return moviesItemses;
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            moviesFetched = movies;
            for (int i = 0; i < moviesFetched.size() ;i ++){
                String title = moviesFetched.get(i).getNamaFilm();
                String message = moviesFetched.get(i).getDeskripsiFilm();

                String CHANNEL_ID = "Channel_2";
                String CHANNEL_NAME = "New Movie Release Channel";

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
                    notificationManagerCompat.notify(i, notification);
                }
            }

        }
    }
}
