package com.pareenja.carrentalpro.admin;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.adapters.ViewAllCarAdapter;
import com.pareenja.carrentalpro.models.Car;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminReservationFragment extends Fragment {


    public AdminReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_reservation, container, false);
    }



}
