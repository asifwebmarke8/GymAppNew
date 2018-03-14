package com.fazal.dreamsolutions.gymappnew.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fazal.dreamsolutions.gymappnew.Activities.MainDrawerActivity;
import com.fazal.dreamsolutions.gymappnew.Objects.Booking;
import com.fazal.dreamsolutions.gymappnew.Objects.BookingList;
import com.fazal.dreamsolutions.gymappnew.Objects.Bookings;
import com.fazal.dreamsolutions.gymappnew.Objects.ModelNearByTrainers;
import com.fazal.dreamsolutions.gymappnew.Objects.Person;
import com.fazal.dreamsolutions.gymappnew.R;
import com.fazal.dreamsolutions.gymappnew.Utils.GeneralUtils;
import com.fazal.dreamsolutions.gymappnew.Utils.MyApplication;
import com.fazal.dreamsolutions.gymappnew.Utils.NearByTrainerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat_Users extends Fragment {


    EditText Search;

    private RecyclerView mRecyclerView;
    private ChatPersonAdapter mAdapter;

    List<BookingList> List;

    MyApplication myApplication;

    Bookings bookings;
    SpotsDialog spotsDialog;


    private DatabaseReference mFirebaseRef;

    public Chat_Users() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat__users, container, false);

        spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        List = new ArrayList<>();
        List.clear();
        Search = (EditText) view.findViewById(R.id.Search);
        Search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                spotsDialog.show();

                List.clear();
                Boolean addCheck = false;
                for (BookingList bookingList : bookings.getBookingList()) {
                    addCheck = false;
                    for (BookingList bookingList1 : List) {
                        if (myApplication.getLoginSession().getToken().getToken_type().contains("ee")) {
                            if (bookingList1.getBooking().getTrainerEmail().equals(bookingList.getBooking().getTrainerEmail())) {
                                addCheck = true;
                            }
                        } else {
                            if (bookingList1.getBooking().getTraineeEmail().equals(bookingList.getBooking().getTraineeEmail())) {
                                addCheck = true;
                            }
                        }

                    }
                    if (addCheck.equals(false)) {
                        List.add(bookingList);
                    }
                }

                for (int i = 0; i < List.size() - 1; i++) {

                    if (myApplication.getLoginSession().getToken().getUserType().equals("ee")) {
                        if (List.get(i).getBooking().getTrainerEmail().equals(List.get(i + 1).getBooking().getTrainerEmail())) {
                            List.remove(i);
                        }
                    } else {
                        if (List.get(i).getBooking().getTraineeEmail().equals(List.get(i + 1).getBooking().getTraineeEmail())) {
                            List.remove(i);
                        }
                    }

                }

                if (s.length() != 0) {

                    if (myApplication.getLoginSession().getToken().getUserType().contains("ee")) {


                        for (int i = 0; i < List.size(); i++) {

                            if (!List.get(i).getBooking().getTrainerName().toLowerCase().contains(s.toString().toLowerCase())) {

                                List.remove(i);
                                mAdapter.notifyDataSetChanged();
                            }
                        }


                    } else {


                        for (int i = 0; i < List.size(); i++) {

                            if (!List.get(i).getBooking().getTraineeName().toLowerCase().contains(s.toString().toLowerCase())) {

                                List.remove(i);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                } else {
                    List.clear();
                    for (int i = 0; i < bookings.getBookingList().length; i++) {

                        if (List.size() == 0) {
                            List.add(bookings.getBookingList()[i]);

                        }
                        if (List.size() > 0) {
                            for (BookingList booking : List) {
                                if (myApplication.getLoginSession().getToken().getUserType().equals("ee")) {
                                    if (!booking.getBooking().getTrainerEmail().equals(bookings.getBookingList()[i].getBooking().getTrainerEmail())) {
                                        List.add(bookings.getBookingList()[i]);

                                    }
                                } else {
                                    if (!booking.getBooking().getTraineeEmail().equals(bookings.getBookingList()[i].getBooking().getTraineeEmail())) {
                                        List.add(bookings.getBookingList()[i]);

                                    }
                                }

                            }
                        }
                    }
                    for (int i = 0; i < List.size() - 1; i++) {

                        if (myApplication.getLoginSession().getToken().getUserType().equals("ee")) {
                            if (List.get(i).getBooking().getTrainerEmail().equals(List.get(i + 1).getBooking().getTrainerEmail())) {
                                List.remove(i);
                            }
                        } else {
                            if (List.get(i).getBooking().getTraineeEmail().equals(List.get(i + 1).getBooking().getTraineeEmail())) {
                                List.remove(i);
                            }
                        }

                    }
                    mAdapter = new ChatPersonAdapter(List, getActivity(), database);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    spotsDialog.dismiss();
                }
            }
        });
        myApplication = (MyApplication) getActivity().getApplicationContext();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvChat);


        view.findViewById(R.id.drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainDrawerActivity) getActivity()).OpenOpenOrCloseDrawer();

            }
        });
        callApi(database);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ChatPersonAdapter(List, getActivity(), database);
        mRecyclerView.setAdapter(mAdapter);

        return view;


    }


    private void callApi(final FirebaseDatabase database) {
        spotsDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = GeneralUtils.URL + "api/booking/GetTrainerBookings";

        if (myApplication.getLoginSession().getToken().getUserType().contains("ee")) {
            URL = GeneralUtils.URL + "api/booking/GetTraineeBookings";
        } else {
            URL = GeneralUtils.URL + "api/booking/GetTrainerBookings";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                List.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {
                        Gson gson = new Gson();
                        Bookings bookings1 = new Bookings();
                        bookings1 = gson.fromJson(response, Bookings.class);
                        bookings = bookings1;
                        spotsDialog.dismiss();

                        List.clear();

                        Boolean addCheck = false;
                        for (BookingList bookingList : bookings.getBookingList()) {
                            addCheck = false;
                            for (BookingList bookingList1 : List) {
                                if (myApplication.getLoginSession().getToken().getUserType().contains("ee")) {
                                    if (bookingList1.getBooking().getTrainerEmail().equals(bookingList.getBooking().getTrainerEmail())) {
                                        addCheck = true;
                                    }
                                } else {
                                    if (bookingList1.getBooking().getTraineeEmail().equals(bookingList.getBooking().getTraineeEmail())) {
                                        addCheck = true;
                                    }
                                }

                            }
                            if (addCheck.equals(false)) {
                                List.add(bookingList);
                            }
                        }

                        mAdapter = new ChatPersonAdapter(List, getActivity(), database);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();


                    }
                } catch (Exception Ex) {
                    spotsDialog.dismiss();
                    Log.e("LOG_VOLLEY", Ex.toString());
                }

//                ChatRefresh(database);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                spotsDialog.dismiss();
                Log.e("LOG_VOLLEY", error.toString());
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + myApplication.getLoginSession().getToken().getAccess_token());

                return headers;
            }

        };

        requestQueue.add(stringRequest);
    }


    public void ChatRefresh(final FirebaseDatabase database) {
        List.clear();


        if (false) {

            if (myApplication.getLoginSession().getToken().getUserType().contains("ee")) {


                for (int i = 0; i < List.size(); i++) {

                    if (!List.get(i).getBooking().getTrainerName().toLowerCase().contains("")) {

                        List.remove(i);
                        mAdapter.notifyDataSetChanged();
                    }
                }

            } else {


                for (int i = 0; i < List.size(); i++) {

                    if (!List.get(i).getBooking().getTraineeName().toLowerCase().contains("")) {

                        List.remove(i);
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }

        } else {

            for (int i = 0; i < List.size() - 1; i++) {

                if (myApplication.getLoginSession().getToken().getUserType().equals("ee")) {
                    if (List.get(i).getBooking().getTrainerEmail().equals(List.get(i + 1).getBooking().getTrainerEmail())) {
                        List.remove(i);
                    }
                } else {
                    if (List.get(i).getBooking().getTraineeEmail().equals(List.get(i + 1).getBooking().getTraineeEmail())) {
                        List.remove(i);
                    }
                }

            }
            mAdapter = new ChatPersonAdapter(List, getActivity(), database);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
}
