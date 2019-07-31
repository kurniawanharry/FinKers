package com.harry.finkers.FragmentHomeTab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harry.finkers.FragmentHome.HomeFragment;
import com.harry.finkers.R;

public class FragmentTabHome extends Fragment {

    View v;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment_home_tab,container,false);

        tabLayout = (TabLayout)v.findViewById(R.id.tablayout_home);
        viewPager = (ViewPager)v.findViewById(R.id.viewpager_home);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.AddFragment(new HomeFragment(),"All");
        viewPagerAdapter.AddFragment(new HomeFragment(),"Nike");
        viewPagerAdapter.AddFragment(new HomeFragment(),"Adidas");
        viewPagerAdapter.AddFragment(new HomeFragment(),"Converse");
        viewPagerAdapter.AddFragment(new HomeFragment(),"Vans");
        viewPagerAdapter.AddFragment(new HomeFragment(),"Jordan");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return v;
    }
}
