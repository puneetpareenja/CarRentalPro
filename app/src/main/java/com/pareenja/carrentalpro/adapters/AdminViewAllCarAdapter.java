package com.pareenja.carrentalpro.adapters;

import android.annotation.SuppressLint;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.models.Car;

public class AdminViewAllCarAdapter extends FirestoreRecyclerAdapter<Car, AdminViewAllCarAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public AdminViewAllCarAdapter(@NonNull FirestoreRecyclerOptions<Car> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull Car car) {
        viewHolder.carNameTextView.setText(car.getBrand() + " " + car.getCarModel());
        viewHolder.carColorTextView.setText(car.getColor());
        viewHolder.carVinTextView.setText(car.getVinNumber());
        viewHolder.carCapacityTextView.setText(String.valueOf(car.getCapacity()));

        NumberFormat format = NumberFormat.getCurrencyInstance();

        viewHolder.carPriceTextView.setText(
                format.format(car.getPricePerHour())
                        + " / "
                        + format.format(car.getPricePerDay()));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.car_item_view, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView carImageView;
        TextView carNameTextView;
        TextView carColorTextView;
        TextView carPriceTextView;
        TextView carVinTextView;
        TextView carCapacityTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            carImageView = itemView.findViewById(R.id.image_view_car_image);
            carNameTextView = itemView.findViewById(R.id.text_view_car_name);
            carColorTextView = itemView.findViewById(R.id.text_view_car_color);
            carPriceTextView = itemView.findViewById(R.id.text_view_car_price);
            carVinTextView = itemView.findViewById(R.id.text_view_car_vin);
            carCapacityTextView = itemView.findViewById(R.id.text_view_car_capacity);

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