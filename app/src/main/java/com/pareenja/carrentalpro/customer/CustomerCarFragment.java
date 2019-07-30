package com.pareenja.carrentalpro.customer;


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
import com.pareenja.carrentalpro.adapters.AdminViewAllCarAdapter;
import com.pareenja.carrentalpro.adapters.CustomerViewAllCarAdapter;
import com.pareenja.carrentalpro.models.Car;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerCarFragment extends Fragment {

    private static final String TAG = "AdminCarFragment";
    ArrayList<Car> carArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference carRef = db.collection("cars");
    private CustomerViewAllCarAdapter customerViewAllCarAdapter;
    private View layout;

    public CustomerCarFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_customer_car, container, false);
        setUpRecyclerView();
        return layout;
    }

    private void setUpRecyclerView() {
        Query query = carRef
                .whereEqualTo("broken", false)
                .whereEqualTo("booked", false)
                .whereEqualTo("reserved", false);

        FirestoreRecyclerOptions<Car> carFirestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Car>()
                        .setQuery(query, Car.class)
                        .build();

        Log.d(TAG, "setUpRecyclerView: Everything is working fine");
        customerViewAllCarAdapter = new CustomerViewAllCarAdapter(carFirestoreRecyclerOptions);
        RecyclerView recyclerView = layout.findViewById(R.id.customer_car_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(customerViewAllCarAdapter);

        customerViewAllCarAdapter.setOnItemClickListener(new CustomerViewAllCarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Car car = documentSnapshot.toObject(Car.class);
                car.setId(documentSnapshot.getId());

                Intent intent = new Intent(getContext(), CustomerViewCarActivity.class);
                intent.putExtra("car", car);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        customerViewAllCarAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        customerViewAllCarAdapter.stopListening();
    }
}
