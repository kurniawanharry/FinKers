package com.harry.finkers.FragmentAccount;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.harry.finkers.FragmentPost.HomeItemTranding;
import com.harry.finkers.MainActivity;
import com.harry.finkers.R;
import com.harry.finkers.SplashScreen.SplashScreen;

import java.util.UUID;

public class UserAddData extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;

    private Button btnAddImage;
    private Button btnPostData;
    private ImageView imageViewData;
    private EditText editTextTitle;
    private Button btnBackPrevious;
    private ProgressBar progressBar;
    private TextView textView;

    private Uri mImageUri;


    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    private StorageTask storageTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        btnAddImage = findViewById(R.id.btnAddImageData);
        btnPostData = findViewById(R.id.btnPostAddData);
        btnBackPrevious = findViewById(R.id.btnBackToProfil);
        imageViewData = findViewById(R.id.addImageData);
        editTextTitle = findViewById(R.id.EditeTextAddTitleData);
        progressBar = findViewById(R.id.progress_bar_add);
        textView = findViewById(R.id.namaPenggunaFab);

        FirebaseUser currentuser;

        mAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference("sneaker");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("sneaker");

        mAuth = FirebaseAuth.getInstance();

        btnBackPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnPostData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTask != null && storageTask.isInProgress()) {
                    Toast.makeText(UserAddData.this, "Uploud In Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploudFile();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Glide.with(this).load(mImageUri).into(imageViewData);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploudFile() {
        if (mImageUri != null) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);

            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));


            storageTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setIndeterminate(false);
                            progressBar.setProgress(0);
                        }
                    }, 500);

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadUrl = uriTask.getResult();

                    Toast.makeText(UserAddData.this, "Upload Success", Toast.LENGTH_SHORT).show();

                    HomeItemTranding uploud = new HomeItemTranding(editTextTitle.getText().toString().trim(), downloadUrl.toString());

                    String uploudId = mDatabaseReference.push().getKey();

                    mDatabaseReference.child(uploudId).setValue(uploud);

                    openActivity();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserAddData.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(this, "You haven't Selected any File", Toast.LENGTH_SHORT).show();
        }
    }

    private void openActivity() {
        Intent intent = new Intent(UserAddData.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
