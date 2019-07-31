package com.pareenja.carrentalpro.customer;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.adapters.CustomerViewReservationAdapter;
import com.pareenja.carrentalpro.models.Reservation;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerReservationFragment extends Fragment {

    private static final String TAG = "CustomerReservationFrag";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference reseervationRef = db.collection("reservation");
    private CustomerViewReservationAdapter customerViewReservationAdapter;
    private View view;


    public CustomerReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_customer_reservation, container, false);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        Query query = reseervationRef;
        FirestoreRecyclerOptions<Reservation> reservationFirestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Reservation>()
                        .setQuery(query, Reservation.class)
                        .build();

        Log.d(TAG, "setUpRecyclerView: Everything is working fine");
        customerViewReservationAdapter = new CustomerViewReservationAdapter(reservationFirestoreRecyclerOptions);
        RecyclerView recyclerView = view.findViewById(R.id.customer_reservation_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(customerViewReservationAdapter);

        customerViewReservationAdapter.setOnItemClickListener(new CustomerViewReservationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

            }
        });
    }
}
