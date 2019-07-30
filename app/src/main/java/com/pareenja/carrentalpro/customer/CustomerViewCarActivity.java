package com.pareenja.carrentalpro.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_car);

        Car car = getIntent().getParcelableExtra("car");

        initLayout();
        carModelTextView.setText(car.getCarModel());
        carBrandTextView.setText(car.getBrand());
        carColorTextView.setText(car.getColor());
        carCapacityTextView.setText(String.valueOf(car.getCapacity()));
        carPricePerHours.setText(String.valueOf(car.getPricePerHour()));
        carPricePerDay.setText(String.valueOf(car.getPricePerDay()));
    }

    private void initLayout() {
        carImageView = findViewById(R.id.image_view_car_image);
        carModelTextView = findViewById(R.id.text_view_car_model);
        carBrandTextView = findViewById(R.id.text_view_car_brand);
        carColorTextView = findViewById(R.id.text_view_car_color);
        carCapacityTextView = findViewById(R.id.text_view_car_capacity);
        carPricePerHours = findViewById(R.id.text_view_price_per_hour);
        carPricePerDay = findViewById(R.id.text_view_price_per_day);
    }

}
