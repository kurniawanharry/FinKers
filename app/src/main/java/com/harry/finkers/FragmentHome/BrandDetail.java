package com.harry.finkers.FragmentHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.harry.finkers.R;


public class BrandDetail extends AppCompatActivity {

    RequestOptions options;
    Button btnPurchase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_brand_detail);

        btnPurchase = findViewById(R.id.btnPurchase);
        options = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

        Intent intent = getIntent();
        String gambar = intent.getExtras().getString("gambar");
        String nama = intent.getExtras().getString("nama");
        String detail = intent.getExtras().getString("detail");

        ImageView imageView = findViewById(R.id.Imageview_brand_detail);
        TextView textView = findViewById(R.id.Textview_brand_Sepatu);
        TextView textView1 = findViewById(R.id.Textview_brand_Description);

        Glide.with(this).load(gambar).centerCrop().apply(options).into(imageView);
        textView.setText(nama);
        textView1.setText(detail);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BrandDetail.this, "Item is unavailable",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
