package com.fazal.dreamsolutions.gymappnew.FragmentsTrainer;


import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fazal.dreamsolutions.gymappnew.Activities.MainDrawerActivity;
import com.fazal.dreamsolutions.gymappnew.Objects.ModelNearByTrainers;
import com.fazal.dreamsolutions.gymappnew.R;
import com.fazal.dreamsolutions.gymappnew.Utils.GPSTracker;
import com.fazal.dreamsolutions.gymappnew.Utils.GeneralUtils;
import com.fazal.dreamsolutions.gymappnew.Utils.MyApplication;
import com.fazal.dreamsolutions.gymappnew.Utils.NearByTrainerAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


public class NearByTrainers extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    double dblLat, dblLon;
    SpotsDialog spotsDialog;
    MyApplication myApplication;
    TextView no_data_found;

    public NearByTrainers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.nearby_trainer, container, false);
        myApplication = (MyApplication) getActivity().getApplicationContext();
        spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        no_data_found = view.findViewById(R.id.no_data_found);
        no_data_found.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        SmartLocation.with(getActivity()).location()
                .start(new OnLocationUpdatedListener() {

                    @Override
                    public void onLocationUpdated(Location location) {
                        dblLat = location.getLatitude();
                        dblLon = location.getLongitude();

                    }
                });

        Log.e("Location : ", "" + dblLat + " " + dblLon);
        ((MainDrawerActivity)getActivity()).validiateUser();

        view.findViewById(R.id.drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainDrawerActivity) getActivity()).OpenOpenOrCloseDrawer();
            }
        });

        callApi();
        return view;
    }

    private void callApi() {
        spotsDialog.show();
        GPSTracker gpsTracker=new GPSTracker(getActivity());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = GeneralUtils.URL + "api/Trainer/"+gpsTracker.getLatitude()+"/"+gpsTracker.getLongitude()+"/GetNearbyTrainers";
        gpsTracker.stopUsingGPS();
        Log.e("Location : ", "" + dblLat + " " + dblLon);
        //   final String mRequestBody = "username="+etEmail.getText().toString().trim()+"&password="+etPassword.getText().toString().trim()+"&grant_type=password";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                spotsDialog.dismiss();
                if (response.contains("Email")) {
                    no_data_found.setVisibility(View.INVISIBLE);
                    Log.e("response", "Login");
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    ModelNearByTrainers[] nearByTrainers = gson.fromJson(response, ModelNearByTrainers[].class);
                    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    //   recyclerView.setAdapter( adapter );
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new NearByTrainerAdapter(getActivity(), nearByTrainers);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                } else if (!response.contains("Email")) {
                    Log.e("response", "Not Login");
                    no_data_found.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Nor Login", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                no_data_found.setVisibility(View.VISIBLE);
                Log.e("LOG_VOLLEY", error.toString());
               // Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myApplication.getLoginSession().getToken().getAccess_token());
                Log.e("access_token", myApplication.getLoginSession().getToken().getAccess_token());
                return headers;
            }

        };

        requestQueue.add(stringRequest);
    }

}
