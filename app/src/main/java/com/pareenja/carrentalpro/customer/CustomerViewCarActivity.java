package com.pareenja.carrentalpro.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.models.Car;

public class CustomerViewCarActivity extends AppCompatActivity {

    ImageView carImageView;
    TextView carModelTextView;
    TextView carBrandTextView;
    TextView carColorTextView;
    TextView carCapacityTextView;
    TextView carPricePerHours;
    TextView carPricePerDay;
    MaterialButton reserveCarButton;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_car);

        car = getIntent().getParcelableExtra("car");

        initLayout();
        carModelTextView.setText(car.getCarModel());
        carBrandTextView.setText(car.getBrand());
        carColorTextView.setText(car.getColor());
        carCapacityTextView.setText(String.valueOf(car.getCapacity()));
        carPricePerHours.setText("$ " + car.getPricePerHour());
        carPricePerDay.setText("$ " + car.getPricePerDay());
    }

    private void initLayout() {
        carImageView = findViewById(R.id.image_view_car_image);
        carModelTextView = findViewById(R.id.text_view_car_model);
        carBrandTextView = findViewById(R.id.text_view_car_brand);
        carColorTextView = findViewById(R.id.text_view_car_color);
        carCapacityTextView = findViewById(R.id.text_view_car_capacity);
        carPricePerHours = findViewById(R.id.text_view_price_per_hour);
        carPricePerDay = findViewById(R.id.text_view_price_per_day);
        reserveCarButton = findViewById(R.id.button_reserve_button);

        reserveCarButton.setOnClickListener(onClickListener);
    }

    MaterialButton.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CustomerViewCarActivity.this, ReserveCarActivity.class);
            intent.putExtra("car", car);
            startActivity(intent);
        }
    };

}
