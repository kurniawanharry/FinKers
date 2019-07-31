package com.harry.finkers.FragmentAccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.harry.finkers.MainActivity;
import com.harry.finkers.R;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText editText_username;
    private EditText editText_email;
    private EditText editText_password;
    private Button btnSignUp;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        editText_username = findViewById(R.id.EditText_SignUp_Username);
        editText_email = findViewById(R.id.EditText_SignUp_Email);
        editText_password = findViewById(R.id.EditText_SignUp_Password);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrasiUser();
            }
        });


        Spannable();

    }

    private void registrasiUser() {

        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password less than 6", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User. . .");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            sendUserData();
                            mAuth.signOut();
                            Toast.makeText(SignUpActivity.this, "Registered Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(SignUpActivity.this, SingInActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(SignUpActivity.this, "Registered Failed,Please Try again", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(mAuth.getUid());
        UserItem userItem = new UserItem(editText_username.getText().toString().trim());
        myRef.setValue(userItem);
    }

    private void Spannable() {
        SpannableString spannableString = new SpannableString("have an Account? Sign in");
        final StyleSpan bs = new StyleSpan(Typeface.BOLD);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SingInActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.WHITE);

            }
        };

        spannableString.setSpan(bs, 17, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, 17, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        TextView textView = findViewById(R.id.textview_signin_signup);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }


}
