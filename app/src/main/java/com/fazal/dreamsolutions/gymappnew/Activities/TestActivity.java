package com.fazal.dreamsolutions.gymappnew.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fazal.dreamsolutions.gymappnew.FragmentsTrainee.TraineeHistoryFragment;
import com.fazal.dreamsolutions.gymappnew.FragmentsTrainee.TraineeLoginFragment;
import com.fazal.dreamsolutions.gymappnew.FragmentsTrainer.TrainerLoginFragment;
import com.fazal.dreamsolutions.gymappnew.R;

public class TestActivity extends AppCompatActivity {
    Fragment fragment;
    Button trainer_history, trainee_history, payment, login1, login2, register1, register2, profile, dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        trainee_history = findViewById(R.id.training_history);
        trainee_history = findViewById(R.id.training_history);
        payment = findViewById(R.id.payment);
        login1 = findViewById(R.id.trainee_login);
        login2 = findViewById(R.id.login_trainer);
        register1 = findViewById(R.id.register_trainee);
        register2 = findViewById(R.id.register_trainer);
        profile = findViewById(R.id.profile);
        dashboard = findViewById(R.id.dashboard);

        trainee_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TraineeHistoryFragment()).addToBackStack("tag").commit();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
        trainee_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TraineeHistoryFragment()).addToBackStack("tag").commit();
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TrainerLoginFragment()).addToBackStack("tag").commit();
            }
        });
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TraineeLoginFragment()).addToBackStack("tag").commit();
            }
        });
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TraineeLoginFragment()).addToBackStack("tag").commit();
            }
        });
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TrainerLoginFragment()).addToBackStack("tag").commit();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

}