package com.harry.finkers.intro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harry.finkers.R;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<IntroItem> mListIntro;

    public IntroViewPagerAdapter(Context mContext, List<IntroItem> mListIntro) {
        this.mContext = mContext;
        this.mListIntro = mListIntro;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View introdisplay = inflater.inflate(R.layout.activity_intro_display, null);

        ImageView imgSlide = introdisplay.findViewById(R.id.image_intro);
        TextView desc = introdisplay.findViewById(R.id.textview_intro);

        desc.setText(mListIntro.get(position).getDescription());
        imgSlide.setImageResource(mListIntro.get(position).getIntroing());

        container.addView(introdisplay);

        return introdisplay;


    }

    @Override
    public int getCount() {
        return mListIntro.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

