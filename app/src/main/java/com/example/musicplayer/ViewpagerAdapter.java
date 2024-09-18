package com.example.musicplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;



public class ViewpagerAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }


    void addFragment(Fragment fragment,String title){
    fragments.add(fragment);
    titles.add(title);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }




    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public String getPageTitle(int position){
        return titles.get(position);
    }
}




