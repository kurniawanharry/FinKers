package com.harry.finkers.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.harry.finkers.FragmentAccount.SignUpActivity;
import com.harry.finkers.FragmentAccount.SingInActivity;
import com.harry.finkers.MainActivity;
import com.harry.finkers.R;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager introPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabindicator;
    private Button btnNext;
    int position = 0;
    private Button btnSkip;
    private Button btnStarted;
    private Animation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //Kode untuk tampil sekali
        SharedPreferences preferences = getSharedPreferences("ActivityPref", Context.MODE_PRIVATE);
        if(preferences.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, SingInActivity.class);
            startActivity(intent);
            finish();
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("activity_executed", true);
            editor.commit();
        }


        //Fungsi
        btnNext = (Button)findViewById(R.id.btn_next_intro);
        btnSkip = (Button)findViewById(R.id.btn_skip_intro);
        tabindicator = findViewById(R.id.tab_indicator);
        btnStarted = findViewById(R.id.btn_intro_started);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);


        //List Intro
        final List<IntroItem> mList = new ArrayList<>();
        mList.add(new IntroItem("You can find any kind of Sneakers", R.drawable.intro1));
        mList.add(new IntroItem("You can also Uploud your Sneakers, to show it to Other people.", R.drawable.intro2));
        mList.add(new IntroItem("Trade Sneakers", R.drawable.intro3));

        introPager = findViewById(R.id.viewpager_intro);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        introPager.setAdapter(introViewPagerAdapter);

        //setup tab layout
        tabindicator.setupWithViewPager(introPager);

        //Button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = introPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    introPager.setCurrentItem(position);
                }
                if (position == mList.size()-1) {

                    loadlastintro();
                }
            }
        });
        //tab indicator
        tabindicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1){
                    loadlastintro();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SingInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SingInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
    private void loadlastintro() {
        btnNext.setVisibility(View.INVISIBLE);
        btnStarted.setVisibility(View.VISIBLE);
        tabindicator.setVisibility(View.INVISIBLE);

        //Button animation
        btnStarted.setAnimation(animation);

    }
}
