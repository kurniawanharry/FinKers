package com.harry.finkers.FragmentPost;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harry.finkers.R;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    View v;

    private ArrayList<HomeItemTranding> lstHomeTranding;

    private RecyclerView getMyrecyclerViewTranding;
    private RecycleViewAdapterHomeTranding mAdapater;

    private DatabaseReference mDatabaseref;
    private FirebaseAuth mAuth;

    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment_post, container, false);

        mProgressBar = (ProgressBar)v.findViewById(R.id.progress_circle);

        getMyrecyclerViewTranding = (RecyclerView) v.findViewById(R.id.Recycleview_HomeTranding);
        getMyrecyclerViewTranding.setHasFixedSize(true);
        getMyrecyclerViewTranding.setLayoutManager(new LinearLayoutManager(getActivity()));

        lstHomeTranding = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();

        mDatabaseref = FirebaseDatabase.getInstance().getReference("sneaker");

        mDatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    HomeItemTranding uploud = postSnapshot.getValue(HomeItemTranding.class);
                    lstHomeTranding.add(uploud);
                }
                mAdapater = new RecycleViewAdapterHomeTranding(getActivity(), lstHomeTranding);
                getMyrecyclerViewTranding.setAdapter(mAdapater);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
