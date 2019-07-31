package com.harry.finkers.FragmentPost;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harry.finkers.FragmentHome.HomeItemBrand;
import com.harry.finkers.R;

import java.util.List;

public class RecycleViewAdapterHomeTranding extends RecyclerView.Adapter<RecycleViewAdapterHomeTranding.MyViewHolder> {

    private Context mContext;
    private List<HomeItemTranding> mData;

    RequestOptions options;

    public RecycleViewAdapterHomeTranding(Context mContext, List<HomeItemTranding> mData) {
        this.mContext = mContext;
        this.mData = mData;

        options = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
    }


    @NonNull
    @Override
    public RecycleViewAdapterHomeTranding.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        v = mInflator.inflate(R.layout.activity_recycleview_tranding, viewGroup,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        HomeItemTranding homeItem = mData.get(i);

        String judul = homeItem.getTitle();
        String gambar = homeItem.getImageUrl();

        myViewHolder.textView_judul.setText(judul);
        Glide.with(mContext).load(gambar).apply(options).into(myViewHolder.imageView_shoes);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_judul;
        ImageView imageView_shoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_judul = (TextView)itemView.findViewById(R.id.user_title_post);
            imageView_shoes = (ImageView)itemView.findViewById(R.id.imageview_home_tranding);
        }
    }
}
