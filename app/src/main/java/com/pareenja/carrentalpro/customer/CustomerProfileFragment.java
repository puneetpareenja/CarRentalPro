package com.pareenja.carrentalpro.customer;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pareenja.carrentalpro.LoginActivity;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.models.Person;

import org.w3c.dom.Text;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    ImageView userImageView;
    TextView userNameTextView;
    TextView userEmailTextView;
    TextView userPhoneTextView;
    TextView userRoleTextView;
    MaterialButton logoutMaterialButton;

    public CustomerProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_proifile, container, false);
        setupFragment(view);
        return view;
    }

    private void setupFragment(View view) {
        userImageView = view.findViewById(R.id.circular_image_view_user);
        userNameTextView = view.findViewById(R.id.text_view_username);
        userEmailTextView = view.findViewById(R.id.text_view_email);
        userPhoneTextView = view.findViewById(R.id.text_view_phone);
        userRoleTextView = view.findViewById(R.id.text_view_person_role);
        logoutMaterialButton = view.findViewById(R.id.material_button_logout);

        logoutMaterialButton.setOnClickListener(onClickListener);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore
                .collection("users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Person person = documentSnapshot.toObject(Person.class);
                        userNameTextView.setText(person.getName());
                        userEmailTextView.setText(person.getEmail());
                        userPhoneTextView.setText(person.getPhone());
                        userRoleTextView.setText(person.getPersonRole());
                    }
                });

    }

    private MaterialButton.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firebaseAuth.signOut();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        }
    };

}
