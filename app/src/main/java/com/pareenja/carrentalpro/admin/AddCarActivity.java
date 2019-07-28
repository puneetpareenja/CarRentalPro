package com.pareenja.carrentalpro.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.models.Car;

public class AddCarActivity extends Activity {

    private static final String TAG = "AddCarFragment";
    private TextInputLayout vinEditText;
    private TextInputLayout modelEditText;
    private TextInputLayout brandEditText;
    private TextInputLayout colorEditText;
    private TextInputLayout capacityEditText;
    private TextInputLayout pricePerHourEditText;
    private TextInputLayout pricePerDayEditText;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        initActivity();
    }

    /*  private void initActivityAsDialog() {
          DisplayMetrics displayMetrics = new DisplayMetrics();
          getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

          int width = displayMetrics.widthPixels;
          int height = displayMetrics.heightPixels;

          getWindow().setLayout((int) (width * 0.9), (int) (height * 0.9));
          getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      }
  */

    private void initActivity() {
        vinEditText = findViewById(R.id.text_input_car_vin);
        modelEditText = findViewById(R.id.text_input_car_model);
        brandEditText = findViewById(R.id.text_input_car_brand);
        colorEditText = findViewById(R.id.text_input_car_color);
        capacityEditText = findViewById(R.id.text_input_car_capacity);
        pricePerHourEditText = findViewById(R.id.text_input_car_price_per_hour);
        pricePerDayEditText = findViewById(R.id.text_input_car_price_per_day);
        Button buttonAddCar = findViewById(R.id.material_button_add_car);

        buttonAddCar.setOnClickListener(onClickListener);

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.material_button_add_car) {
                if (isInputValid()) {
                    final Car car = new Car();
                    car.setVinNumber(vinEditText.getEditText().getText().toString());
                    car.setBrand(brandEditText.getEditText().getText().toString());
                    car.setCarModel(modelEditText.getEditText().getText().toString());
                    car.setColor(colorEditText.getEditText().getText().toString());
                    car.setCapacity(Integer.parseInt(capacityEditText.getEditText().getText().toString()));
                    car.setPricePerHour(Double.parseDouble(pricePerHourEditText.getEditText().getText().toString()));
                    car.setPricePerDay(Double.parseDouble(pricePerDayEditText.getEditText().getText().toString()));
                    car.setBooked(false);
                    car.setBroken(false);
                    car.setReserved(false);

                    firebaseFirestore
                            .collection("cars")
                            .add(car)
                            .addOnSuccessListener(
                                    new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(AddCarActivity.this,
                                                    car.getBrand() + " " + car.getCarModel() + " Added",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddCarActivity.this,
                                            e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }
    };

    public boolean isInputValid() {

        boolean returnValue = true;

        if (brandEditText.getEditText().getText().toString().equals("")) {
            brandEditText.setError("Required");
            returnValue = false;
        }

        if (modelEditText.getEditText().getText().toString().equals("")) {
            modelEditText.setError("Required");
            returnValue = false;
        }

        if (vinEditText.getEditText().getText().toString().equals("")) {
            vinEditText.setError("Required");
            returnValue = false;
        }

        if (colorEditText.getEditText().getText().toString().equals("")) {
            colorEditText.setError("Required");
            returnValue = false;
        }

        if (capacityEditText.getEditText().getText().toString().equals("")) {
            capacityEditText.setError("Required");
            returnValue = false;
        }

        if (pricePerHourEditText.getEditText().getText().toString().equals("")) {
            pricePerHourEditText.setError("Required");
            returnValue = false;
        }

        if (pricePerDayEditText.getEditText().getText().toString().equals("")) {
            pricePerDayEditText.setError("Required");
            returnValue = false;
        }

        return returnValue;
    }
}
