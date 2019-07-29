package com.pareenja.carrentalpro.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pareenja.carrentalpro.models.Car;
import com.pareenja.carrentalpro.models.Reservation;

public class ViewAllReservationsAdapter
        extends FirestoreRecyclerAdapter<Reservation, ViewAllReservationsAdapter.ViewHolder> {

    public ViewAllReservationsAdapter(@NonNull FirestoreRecyclerOptions<Reservation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewAllReservationsAdapter.ViewHolder viewHolder, int i, @NonNull Reservation reservation) {

    }

    @NonNull
    @Override
    public ViewAllReservationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
