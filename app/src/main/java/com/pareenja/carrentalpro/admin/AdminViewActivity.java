package com.pareenja.carrentalpro.admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pareenja.carrentalpro.R;

public class AdminViewActivity
        extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        initLayout();
    }

    private void initLayout() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.admin_fragment_container, new AdminCarFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new AdminCarFragment();

                    switch (item.getItemId()) {
                        case R.id.admin_item_view_cars:
                            selectedFragment = new AdminCarFragment();
                            break;
                        case R.id.admin_item_view_employees:
                            break;
                        case R.id.admin_item_view_menu:
                            break;
                        case R.id.admin_item_view_reservations:
                            break;
                        case R.id.admin_item_view_users:
                            selectedFragment = new AdminUserFragment();
                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.admin_fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
            };
}
