package com.example.bakingapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class StepsAdapter extends FragmentPagerAdapter {
    Context mContext;
    List<Fragment> mSteps;
    public StepsAdapter(Context context, FragmentManager fragmentManager, List<Fragment> steps)
    {
        super(fragmentManager);
        mContext = context;
        mSteps = steps;
    }

    @Override
    public Fragment getItem(int i) {
        return mSteps.get(i);
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }

}
