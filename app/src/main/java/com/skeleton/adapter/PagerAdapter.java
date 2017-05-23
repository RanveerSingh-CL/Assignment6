package com.skeleton.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.skeleton.fragment.LoginFragment;
import com.skeleton.fragment.SignupFragment;

import java.util.List;

/**
 * Developer: Saurabh Verma
 * Dated: 03-03-2017.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private Context mContext;
    private String []tabTitles = new String[]{"Sign Up", "Login"};

    /**
     * Instantiates a new Pager adapter.
     *
     * @param fm      the fm
     * @param context the fragments
     */
    public PagerAdapter(final FragmentManager fm, final Context context) {
        super(fm);
        mContext = context;
        Log.d("TAG", "here i am");
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0:
                return new SignupFragment();
            case 1:
                return new LoginFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(final int position) {
        // Generate title based on item position

        return tabTitles[position];

    }
}
