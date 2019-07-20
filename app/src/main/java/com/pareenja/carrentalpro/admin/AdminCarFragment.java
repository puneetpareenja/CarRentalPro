package com.pareenja.carrentalpro.admin;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.adapters.ViewAllCarAdapter;
import com.pareenja.carrentalpro.models.Car;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminCarFragment extends Fragment {

    private static final String TAG = "AdminCarFragment";
    ArrayList<Car> carArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference carRef = db.collection("cars");
    private ViewAllCarAdapter viewAllCarAdapter;
    private View layout;
    private ExtendedFloatingActionButton fab;

    public AdminCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_admin_car, container, false);
        fab = layout.findViewById(R.id.fab_add_new_car);
        fab.setOnClickListener(fabListener);
        setUpRecyclerView();
        return layout;
    }

    private ExtendedFloatingActionButton.OnClickListener fabListener =
            v -> {
                Intent intent = new Intent(getContext(), AddCarActivity.class);
                startActivity(intent);
            };

    private void setUpRecyclerView() {
        Query query = carRef;

        FirestoreRecyclerOptions<Car> carFirestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Car>()
                        .setQuery(query, Car.class)
                        .build();

        Log.d(TAG, "setUpRecyclerView: Everything is working fine");
        viewAllCarAdapter = new ViewAllCarAdapter(carFirestoreRecyclerOptions);
        RecyclerView recyclerView = layout.findViewById(R.id.admin_car_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(viewAllCarAdapter);

        viewAllCarAdapter.setOnItemClickListener(new ViewAllCarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Car car = documentSnapshot.toObject(Car.class);
                car.setId(documentSnapshot.getId());

//                Intent intent = new Intent(ViewCarsActivity.this, CarDetailsActivity.class);
//                intent.putExtra("car", car);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        viewAllCarAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewAllCarAdapter.stopListening();
    }

}
