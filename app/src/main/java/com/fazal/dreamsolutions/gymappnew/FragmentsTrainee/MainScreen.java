package com.fazal.dreamsolutions.gymappnew.FragmentsTrainee;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fazal.dreamsolutions.gymappnew.R;

public class MainScreen extends Fragment {
    View view;


    public MainScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_blank, container, false);

        return view;
    }

}
