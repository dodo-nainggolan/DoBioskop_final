package com.dicoding.picodiploma.academy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.dicoding.picodiploma.academy.db.DatabaseHelper;
import com.dicoding.picodiploma.academy.db.FavoriteFilmHelper;
import com.dicoding.picodiploma.academy.db.FavoriteTvShowsHelper;
import com.dicoding.picodiploma.academy.fragment.MoviesFragment;
import com.dicoding.picodiploma.academy.fragment.TvShowsFragment;
import com.dicoding.picodiploma.academy.widget.StackWidgetService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "dicoding channel";

    private FavoriteFilmHelper favoriteFilmHelper;
    private FavoriteTvShowsHelper favoriteTvShowsHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            String title;

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    title = "MOVIES";
                    setTitle(title);


                    fragment = new MoviesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();

                    return true;

                case R.id.navigation_dashboard:

                    title = "TV SHOWS";
                    setTitle(title);
                    fragment = new TvShowsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();

                    return true;

                case R.id.favorite_dashboard:

                    Intent favActivity = new Intent(MainActivity.this, MyTabLayout.class);
                    startActivity(favActivity);

//                    title = "MY FAVORITE";
//                    setTitle ( title );
//                    fragment = new FavoriteFilmFragment ();
//                    getSupportFragmentManager ().beginTransaction ()
//                            .replace ( R.id.container_layout, fragment, fragment.getClass ().getSimpleName () )
//                            .commit ();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_home);
        }

        favoriteFilmHelper = FavoriteFilmHelper.getInstance(getApplicationContext());
        favoriteFilmHelper.open();
        favoriteTvShowsHelper = FavoriteTvShowsHelper.getInstance(getApplicationContext());
        favoriteTvShowsHelper.open();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendNotification(View view) {

        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        Log.e("TOMBOL", "Ditekan" );

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp))
                .setContentTitle(getResources().getString(R.string.content_title))
                .setContentText(getResources().getString(R.string.content_text))
                .setSubText(getResources().getString(R.string.subtext))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}