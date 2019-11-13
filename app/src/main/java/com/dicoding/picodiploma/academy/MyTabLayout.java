package com.dicoding.picodiploma.academy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.dicoding.picodiploma.academy.adapter.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MyTabLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_my_tab_layout );
        setTitle ( "FavoriteFilm" );

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter ( this, getSupportFragmentManager () );
        ViewPager viewPager = findViewById ( R.id.view_pager );
        viewPager.setAdapter ( sectionsPagerAdapter );
        TabLayout tabs = findViewById ( R.id.tabs );
        tabs.setupWithViewPager ( viewPager );

        getSupportActionBar ().setElevation ( 0 );
    }

}
