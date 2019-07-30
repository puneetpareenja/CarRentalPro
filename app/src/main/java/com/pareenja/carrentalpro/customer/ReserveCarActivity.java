package com.pareenja.carrentalpro.customer;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.models.Car;
import com.pareenja.carrentalpro.models.Reservation;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class ReserveCarActivity extends AppCompatActivity {

    private static final String TAG = "ReserveCarActivity";

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

    FragmentManager fragmentManager;

    Reservation reservation;
    Date fromDate;
    Date toDate;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

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
        carNameTextView.setText(car.getBrand() + " " + car.getCarModel());

        carColorTextView = findViewById(R.id.text_view_car_color);
        carColorTextView.setText(car.getColor());

        carCapacityTextView = findViewById(R.id.text_view_car_capacity);
        carCapacityTextView.setText(String.valueOf(car.getCapacity()));

        carPriceTextView = findViewById(R.id.text_view_car_price);
        carPriceTextView.setText("$" + car.getPricePerHour() + " /  $" + car.getPricePerDay());

        reservationTypeSpinner = findViewById(R.id.spinner_res_type);
        reservationTypeSpinner.setOnItemSelectedListener(onItemSelectedListener);

        reservationTimeButton = findViewById(R.id.material_button_res_time);
        reservationTimeButton.setOnClickListener(onClickListener);

        orderInformationLayout = findViewById(R.id.orderInformation);
        toTextView = findViewById(R.id.text_view_to);
        fromTextView = findViewById(R.id.text_view_from);
        priceTextView = findViewById(R.id.text_view_price);
        calculatedPriceTextView = findViewById(R.id.text_view_calculated_price);
        depositTextView = findViewById(R.id.text_view_deposit);
        totalTextView = findViewById(R.id.text_view_total);

        confirmReservationButton = findViewById(R.id.button_confirm_reservation);
        confirmReservationButton.setOnClickListener(onClickListener);

        orderInformationLayout.setVisibility(View.GONE);

        fragmentManager = getSupportFragmentManager();
        reservation = new Reservation();

        fromDate = new Date();
        toDate = new Date();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    MaterialButton.OnClickListener onClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.material_button_res_time:
                    Calendar now = Calendar.getInstance();

                    TimePickerDialog toTimePicker = TimePickerDialog.newInstance(
                            onTimeSetListener,
                            Calendar.HOUR_OF_DAY,
                            Calendar.MINUTE,
                            false);
                    toTimePicker.setTitle("Select Reservation To Time");
                    toTimePicker.setAccentColor(getColor(R.color.colorPrimaryDark));
                    toTimePicker.show(fragmentManager, "toTime");

                    DatePickerDialog toDatePicker = DatePickerDialog.newInstance(
                            onDateSetListener,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH));
                    toDatePicker.setTitle("Select Reservation To Date");
                    toDatePicker.setMinDate(now);
                    toDatePicker.setAccentColor(getColor(R.color.colorPrimaryDark));
                    toDatePicker.show(fragmentManager, "toDate");


                    TimePickerDialog fromTimePicker = TimePickerDialog.newInstance(
                            onTimeSetListener,
                            Calendar.HOUR_OF_DAY,
                            Calendar.MINUTE,
                            false);
                    fromTimePicker.setTitle("Select Reservation From Time");
                    fromTimePicker.setAccentColor(getColor(R.color.colorPrimaryDark));
                    fromTimePicker.show(fragmentManager, "fromTime");

                    DatePickerDialog fromDatePicker = DatePickerDialog.newInstance(
                            onDateSetListener,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH));
                    fromDatePicker.setTitle("Select Reservation From Date");
                    fromDatePicker.setMinDate(now);
                    fromDatePicker.setAccentColor(getColor(R.color.colorPrimaryDark));
                    fromDatePicker.show(fragmentManager, "fromDate");

                    break;
                case R.id.button_confirm_reservation:
                    firebaseFirestore.collection("reservation").add(reservation)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(ReserveCarActivity.this, "Reservation Confirmed", Toast.LENGTH_SHORT).show();
                            car.setBooked(true);
                            firebaseFirestore.collection("cars").document(car.getId()).update("booked", true);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ReserveCarActivity.this, "Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            switch (view.getTag()) {
                case "fromDate":
                    fromDate.setYear(year);
                    fromDate.setMonth(monthOfYear);
                    fromDate.setDate(dayOfMonth);
                    break;
                case "toDate":
                    toDate.setYear(year);
                    toDate.setMonth(monthOfYear);
                    toDate.setDate(dayOfMonth);
                    break;
            }
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            switch (view.getTag()) {
                case "fromTime":
                    fromDate.setHours(hourOfDay);
                    fromDate.setMinutes(minute);
                    fromDate.setSeconds(second);
                    break;
                case "toTime":
                    toDate.setHours(hourOfDay);
                    toDate.setMinutes(minute);
                    toDate.setSeconds(second);
                    updateReservation();
                    break;
            }
        }
    };

    Spinner.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                reservation.setByHour(false);
            } else {
                reservation.setByHour(true);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            reservation.setByHour(false);
        }
    };


    public void updateReservation() {
        double price;
        double calculatedPrice;
        double total;
        double deposit;

        reservation.setUserId(firebaseAuth.getUid());
        reservation.setCarId(car.getId());
        reservation.setBookedById(firebaseAuth.getUid());
        reservation.setBooked(true);
        reservation.setBookingStartDate(fromDate);
        reservation.setBookingEndDate(toDate);

        long diff = toDate.getTime() - fromDate.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (reservation.isByHour()) {
            price = car.getPricePerHour();
            calculatedPrice = price * (diffHours + 1);
            deposit = calculatedPrice / 2;
            total = calculatedPrice + deposit;
            priceTextView.setText("$ "+ price +" per hour");
        } else {
            price = car.getPricePerDay();
            calculatedPrice = price * (diffDays + 1);
            deposit = calculatedPrice / 2;
            total = calculatedPrice + deposit;
            priceTextView.setText("$ "+ price +" per day");

        }

        reservation.setPrice(calculatedPrice);
        reservation.setDeposit(deposit);
        reservation.setTotal(total);

        Log.d(TAG, "updateReservation: " + reservation);

        orderInformationLayout.setVisibility(View.VISIBLE);
        toTextView.setText(toDate.toString());
        fromTextView.setText(fromDate.toString());
        calculatedPriceTextView.setText("$ " + calculatedPrice);
        depositTextView.setText("$ " +deposit);
        totalTextView.setText("$ " +total);

    }
}
