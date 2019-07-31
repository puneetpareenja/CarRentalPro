package com.pareenja.carrentalpro.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pareenja.carrentalpro.R;
import com.pareenja.carrentalpro.admin.AdminCarFragment;
import com.pareenja.carrentalpro.admin.AdminEmployeeFragment;
import com.pareenja.carrentalpro.admin.AdminMenuFragment;
import com.pareenja.carrentalpro.admin.AdminReservationFragment;
import com.pareenja.carrentalpro.admin.AdminUserFragment;

public class CustomerViewActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        initLayout();
    }

    private void initLayout() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.customer_fragment_container, new CustomerCarFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new CustomerCarFragment();

                    switch (item.getItemId()) {
                        case R.id.customer_item_view_cars:
                            selectedFragment = new CustomerCarFragment();
                            break;
                        case R.id.customer_item_view_reservations:
                            selectedFragment = new CustomerReservationFragment();
                            break;
                        case R.id.customer_item_view_profile:
                            selectedFragment = new CustomerProfileFragment();
                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.customer_fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
            };

}
