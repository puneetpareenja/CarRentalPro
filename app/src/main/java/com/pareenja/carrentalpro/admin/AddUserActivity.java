package com.pareenja.carrentalpro.admin;

import android.app.Activity;
import android.os.Bundle;

import com.pareenja.carrentalpro.R;

public class AddUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initLayout();
    }

    private void initLayout() {

    }

   /* private void initActivityAsDialog() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.9));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }*/


}
