package com.pareenja.carrentalpro.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.models.Car;
import com.pareenja.carrentalpro.models.Person;
import com.pareenja.carrentalpro.models.Reservation;

public class CustomerViewReservationAdapter extends FirestoreRecyclerAdapter<Reservation, CustomerViewReservationAdapter.ViewHolder> {

    private static final String TAG = "CustomerViewReservation";
    
    private CustomerViewReservationAdapter.OnItemClickListener listener;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    public CustomerViewReservationAdapter(@NonNull FirestoreRecyclerOptions<Reservation> options) {
        super(options);
        Log.d(TAG, "CustomerViewReservationAdapter: Inside Constructor");
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Reservation reservation) {
        viewHolder.startDateTextView.setText(reservation.getBookingStartDate().toString());
        viewHolder.endDateTextView.setText(reservation.getBookingEndDate().toString());
        if (reservation.isByHour()) {
            viewHolder.bookingTypeTextView.setText("By Hour");
        } else {
            viewHolder.bookingTypeTextView.setText("By Day");
        }
        viewHolder.costTextView.setText("$" + reservation.getPrice());
        viewHolder.depositTextView.setText(("$" + reservation.getDeposit()));
        viewHolder.totalTextView.setText("$" + reservation.getTotal());

        firebaseFirestore.collection("cars")
                .document(reservation.getCarId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Car car = documentSnapshot.toObject(Car.class);
                        viewHolder.carNameTextView.setText(car.getBrand() + " " + car.getCarModel());
                    }
                });

        firebaseFirestore.collection("users")
                .document(reservation.getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Person person = documentSnapshot.toObject(Person.class);
                        viewHolder.userNameTextView.setText(person.getName());
                        viewHolder.userEmailTextView.setText(person.getEmail());
                    }
                });

    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.reservation_item_view, parent, false);
        return new ViewHolder(view);
    }


    public void setOnItemClickListener(CustomerViewReservationAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView carImageView;
        TextView carNameTextView;
        TextView userNameTextView;
        TextView userEmailTextView;
        TextView startDateTextView;
        TextView endDateTextView;
        TextView bookingTypeTextView;
        TextView costTextView;
        TextView depositTextView;
        TextView totalTextView;
        TextView bookingByTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: Inside ViewHolderConstructor");

            carImageView = itemView.findViewById(R.id.image_view_car_image);
            carNameTextView = itemView.findViewById(R.id.text_view_car_name);
            userNameTextView = itemView.findViewById(R.id.text_view_username);
            userEmailTextView = itemView.findViewById(R.id.text_view_user_email);
            startDateTextView = itemView.findViewById(R.id.text_view_start_date);
            endDateTextView = itemView.findViewById(R.id.text_view_end_date);
            bookingTypeTextView = itemView.findViewById(R.id.label_booking_type);
            costTextView = itemView.findViewById(R.id.text_view_cost);
            depositTextView = itemView.findViewById(R.id.text_view_deposit);
            totalTextView = itemView.findViewById(R.id.text_view_total);
            bookingByTextView = itemView.findViewById(R.id.text_view_booking_by);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION
                            && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
}
