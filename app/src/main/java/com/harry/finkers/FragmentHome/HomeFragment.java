package com.harry.finkers.FragmentHome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.harry.finkers.FragmentPost.HomeItemTranding;
import com.harry.finkers.FragmentPost.RecycleViewAdapterHomeTranding;
import com.harry.finkers.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View v;

    private ArrayList<HomeItemBrand> lstHomeBrand;
    private RecyclerView getMyrecyclerViewBrand;
    private RequestQueue mRequestQueue;
    private RecycleViewAdapterHomeBrand mdapterHomeBrand;
    private ProgressBar progressBar2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment_home, container, false);

        lstHomeBrand = new ArrayList<>();

        progressBar2 = (ProgressBar)v.findViewById(R.id.progress_bar_home2);

        getMyrecyclerViewBrand = (RecyclerView) v.findViewById(R.id.Recycleview_HomeBrand);
        //getMyrecyclerViewBrand.setHasFixedSize(true);
        getMyrecyclerViewBrand.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        mRequestQueue = Volley.newRequestQueue(getContext());

        parseJSON();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void parseJSON() {
        String url = "https://api.myjson.com/bins/15qjdt";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("sepatu");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String namaSepatu = hit.getString("nama");
                                String gambarSepatu = hit.getString("gambar");
                                String detailSepatu = hit.getString("deskripsi");

                                lstHomeBrand.add(new HomeItemBrand(namaSepatu, gambarSepatu,detailSepatu));
                            }

                            mdapterHomeBrand = new RecycleViewAdapterHomeBrand(getActivity(), lstHomeBrand);
                            getMyrecyclerViewBrand.setAdapter(mdapterHomeBrand);
                            progressBar2.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


}
