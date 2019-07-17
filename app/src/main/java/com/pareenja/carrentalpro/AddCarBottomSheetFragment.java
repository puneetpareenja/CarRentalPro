package com.pareenja.carrentalpro;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCarBottomSheetFragment extends SuperBottomSheetFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_add_car_bottom_sheet, container, false);
    }

}
