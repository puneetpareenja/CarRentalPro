package com.pareenja.carrentalpro;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImageView;
    TextView userNameTextView;

    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference imageStorageRef;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initActivity();
    }

    private void initActivity() {
        profileImageView = findViewById(R.id.circular_image_view_profile);
        userNameTextView = findViewById(R.id.text_view_username);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        imageStorageRef = firebaseStorage
                .getReference()
                .child("images/" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());

        imageStorageRef.getFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        profileImageView.setImageURI(imageUri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                profileImageView.setImageDrawable(getDrawable(R.drawable.default_user));

            }
        });

        userNameTextView.setText(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getDisplayName());

    }
}
