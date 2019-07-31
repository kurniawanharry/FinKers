package com.harry.finkers.FragmentHome;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harry.finkers.R;

import java.util.List;

public class RecycleViewAdapterHomeBrand extends RecyclerView.Adapter<RecycleViewAdapterHomeBrand.MyViewHolder> {

    private Context mContext;
    private List<HomeItemBrand> mData;
    RequestOptions options;

    public RecycleViewAdapterHomeBrand(Context mContext, List<HomeItemBrand> mData) {
        this.mContext = mContext;
        this.mData = mData;

        //Request Options for Glide
        options = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
    }

    @NonNull
    @Override
    public RecycleViewAdapterHomeBrand.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        v = layoutInflater.inflate(R.layout.activity_recycleview_brand, viewGroup, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterHomeBrand.MyViewHolder myViewHolder, int i) {
        final HomeItemBrand homeItemBrand = mData.get(i);

        String namaSepatu = homeItemBrand.getNama();
        String gambarSepatu = homeItemBrand.getImage();

        myViewHolder.textView_name.setText(namaSepatu);
        Glide.with(mContext).load(gambarSepatu).apply(options).into(myViewHolder.imageView_img);

        myViewHolder.view_contrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BrandDetail.class);
                intent.putExtra("nama",homeItemBrand.getNama());
                intent.putExtra("gambar",homeItemBrand.getImage());
                intent.putExtra("detail",homeItemBrand.getDetail());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_name;
        ImageView imageView_img;
        LinearLayout view_contrainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_name = (TextView)itemView.findViewById(R.id.Textview_brand);
            imageView_img = (ImageView)itemView.findViewById(R.id.Imageview_brand);
            view_contrainer = (LinearLayout)itemView.findViewById(R.id.containerBrand);

        }
    }
}
