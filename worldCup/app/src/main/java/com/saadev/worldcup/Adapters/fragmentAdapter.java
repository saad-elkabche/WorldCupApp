package com.saadev.worldcup.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.saadev.worldcup.matches_frag;
import com.saadev.worldcup.teams_frag;

import java.util.ArrayList;

public class fragmentAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list=new ArrayList<>();
        ArrayList<String> listTitles=new ArrayList<>();




    public fragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        list.add(new matches_frag());
        list.add(new teams_frag());
        listTitles.add("Matches");
        listTitles.add("Teams");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
