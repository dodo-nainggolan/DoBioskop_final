package com.dicoding.picodiploma.academy.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.fragment.FavoriteFilmFragment;
import com.dicoding.picodiploma.academy.fragment.FavoriteTvShowsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2
    };

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super ( fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT );
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteFilmFragment ();
                break;
            case 1:
                fragment = new FavoriteTvShowsFragment ();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources ().getString ( TAB_TITLES[position] );
    }

    @Override
    public int getCount() {
        return 2;
    }
}
