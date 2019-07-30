package com.pareenja.carrentalpro.customer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.models.Car;

public class ReserveCarActivity extends Activity {

    Car car;

    ImageView carImageView;
    TextView carNameTextView;
    TextView carColorTextView;
    TextView carCapacityTextView;
    TextView carPriceTextView;

    Spinner reservationTypeSpinner;
    MaterialButton reservationTimeButton;

    RelativeLayout orderInformationLayout;
    TextView toTextView;
    TextView fromTextView;
    TextView priceTextView;
    TextView calculatedPriceTextView;
    TextView depositTextView;
    TextView totalTextView;

    MaterialButton confirmReservationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_car);

        car = getIntent().getParcelableExtra("car");
        initLayout();

    }

    private void initLayout() {
        carImageView = findViewById(R.id.image_view_car_image);
        carNameTextView = findViewById(R.id.text_view_car_name);
        carColorTextView = findViewById(R.id.text_view_car_color);
        carCapacityTextView = findViewById(R.id.text_view_car_capacity);
        carPriceTextView = findViewById(R.id.text_view_car_price);

        reservationTypeSpinner = findViewById(R.id.spinner_res_type);
        reservationTypeSpinner.setOnItemSelectedListener(onItemSelectedListener);

        reservationTimeButton = findViewById(R.id.material_button_res_time);
        reservationTimeButton.setOnClickListener(onClickListener);

        orderInformationLayout = findViewById(R.id.orderInformation);
        toTextView = findViewById(R.id.text_view_to);
        fromTextView = findViewById(R.id.text_view_from);
        priceTextView = findViewById(R.id.text_view_price);
        calculatedPriceTextView = findViewById(R.id.label_calculated_price);
        depositTextView = findViewById(R.id.text_view_deposit);
        totalTextView = findViewById(R.id.text_view_total);

        confirmReservationButton = findViewById(R.id.button_confirm_reservation);
        confirmReservationButton.setOnClickListener(onClickListener);

        orderInformationLayout.setVisibility(View.GONE);
    }

    MaterialButton.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.material_button_res_time:
                    break;
                case R.id.button_confirm_reservation:
                    break;
            }
        }
    };

    Spinner.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
