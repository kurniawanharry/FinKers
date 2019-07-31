package com.harry.finkers.FragmentAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harry.finkers.R;

import java.util.zip.Inflater;

public class UserProfil extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FloatingActionButton fab;
    private TextView textView_userName;
    private Button btnEdit;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_user_profil, container, false);
        setHasOptionsMenu(true);


        textView_userName = v.findViewById(R.id.TextView_UserProfil);
        btnEdit = v.findViewById(R.id.btnEditProfile);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserItem userItem = dataSnapshot.getValue(UserItem.class);
                    textView_userName.setText(userItem.getUserName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Under Construction",Toast.LENGTH_SHORT).show();
            }
        });

        fabBtn();
        return v;
    }

    private void fabBtn(){
        fab = v.findViewById(R.id.addData);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserAddData.class));
            }
        });

    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getContext(),"Logout",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), SingInActivity.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                logout();
                break;
        }
        return true;
    }
}
