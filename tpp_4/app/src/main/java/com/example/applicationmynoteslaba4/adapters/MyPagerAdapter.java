package com.example.applicationmynoteslaba4.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.applicationmynoteslaba4.FragmentAdd;
import com.example.applicationmynoteslaba4.FragmentDel;
import com.example.applicationmynoteslaba4.FragmentShow;
import com.example.applicationmynoteslaba4.FragmentUpdate;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles = new String[]{"Show", "Add", "Del", "Update"};

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentShow();
            case 1:
                return new FragmentAdd();
            case 2:
                return new FragmentDel();
            case 3:
                return new FragmentUpdate();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4; // Четыре фрагмента
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
