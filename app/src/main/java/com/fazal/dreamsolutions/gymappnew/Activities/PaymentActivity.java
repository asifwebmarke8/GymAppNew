package com.fazal.dreamsolutions.gymappnew.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fazal.dreamsolutions.gymappnew.Fragments.PaymentType;
import com.fazal.dreamsolutions.gymappnew.R;

public class PaymentActivity extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PaymentType(), "PaymentType").commit();
    }
}