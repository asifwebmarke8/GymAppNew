package com.fazal.dreamsolutions.gymappnew.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fazal.dreamsolutions.gymappnew.FragmentsTrainee.TraineeLoginFragment;
import com.fazal.dreamsolutions.gymappnew.FragmentsTrainer.TrainerLoginFragment;
import com.fazal.dreamsolutions.gymappnew.R;

public class MainFragment extends Fragment {
    View view;
    Button btnTrainer, btnTrainee;
    Fragment fragment;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.main_fragment, container, false);

        btnTrainer = view.findViewById(R.id.trainer_btn);
        btnTrainee = view.findViewById(R.id.trainee_btn);

        btnTrainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new TraineeLoginFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
        btnTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new TrainerLoginFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null).commit();

            }
        });

        return view;
    }

}
