package com.example.hoang.freemusic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;

import com.example.hoang.freemusic.fragments.DownloadFragment;
import com.example.hoang.freemusic.fragments.FavouriteFragment;
import com.example.hoang.freemusic.fragments.MusicTypeFragment;

/**
 * Created by Hoang on 5/28/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new MusicTypeFragment();
            case 1: return new FavouriteFragment();
            case 2: return new DownloadFragment();
            default:
                return new MusicTypeFragment();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
