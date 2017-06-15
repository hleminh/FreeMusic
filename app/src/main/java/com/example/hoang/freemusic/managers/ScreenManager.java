package com.example.hoang.freemusic.managers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.hoang.freemusic.R;

/**
 * Created by Hoang on 5/30/2017.
 */

public class ScreenManager {
    public static void openFragment(FragmentManager fragmentManager, Fragment fragment, int layoutID, boolean addToBackStack, boolean haveAnimation) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
        if (haveAnimation)
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_bot, 0, 0, R.anim.exit_to_bot);
        fragmentTransaction.replace(layoutID, fragment);
        fragmentTransaction.commit();
    }

    public static void backFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStackImmediate();
    }
}
