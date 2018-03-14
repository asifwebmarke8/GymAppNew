package com.fazal.dreamsolutions.gymappnew.Fragments;

//  import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fazal.dreamsolutions.gymappnew.Activities.MainActivity;
import com.fazal.dreamsolutions.gymappnew.R;
import com.fazal.dreamsolutions.gymappnew.Utils.GeneralUtils;
import com.fazal.dreamsolutions.gymappnew.Utils.MyApplication;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CreditCard extends Fragment {


    EditText Card_Number, Date_Year, ZIP, Code;
    String tempStringPhone, tempStringPhone2;
    char[] stringArray, stringArray2;
    Button btnProceedToPayment;
   // ModelNearByTrainers modelNearByTrainers;
    ImageView Image;
    TextView Charge, available_hours, available_days, address,hoursBooking;
    RatingBar rating;
    Boolean Day = false, Time = false;
    MyApplication myApplication;
    SpotsDialog spotsDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_card, container, false);


        Card_Number = (EditText) view.findViewById(R.id.CardNumber);
        Date_Year = (EditText) view.findViewById(R.id.Date_Year);
        ZIP = (EditText) view.findViewById(R.id.ZIP);
        Code = (EditText) view.findViewById(R.id.Code);
        myApplication = (MyApplication) getActivity().getApplicationContext();
        //modelNearByTrainers = (ModelNearByTrainers) getArguments().getSerializable("info");
        spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);


        Card_Number.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //XXX do something
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Card_Number.getText().toString();

                if (Card_Number.getText().length() == 5 ||
                        Card_Number.getText().length() == 10 ||
                        Card_Number.getText().length() == 15) {
                    tempStringPhone = Card_Number.getText().toString() + "-";
                    char c = tempStringPhone.charAt(tempStringPhone.length() - 2);

                    if (c != '-') {
                        stringArray = tempStringPhone.toCharArray();
                        stringArray[tempStringPhone.length() - 2] = stringArray[tempStringPhone.length() - 1];
                        stringArray[tempStringPhone.length() - 1] = c;

                        //code to convert charArray back to String..
                        tempStringPhone = new String(stringArray);
                        Card_Number.setText(tempStringPhone);
                        Card_Number.setSelection(tempStringPhone.length());
                        tempStringPhone = null;
                    }
                }
            }
        });
        Date_Year.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //XXX do something
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Date_Year.getText().toString();

                if (Date_Year.getText().length() == 3) {
                    tempStringPhone2 = Date_Year.getText().toString() + "/";
                    char c = tempStringPhone2.charAt(tempStringPhone2.length() - 2);

                    if (c != '-') {
                        stringArray2 = tempStringPhone2.toCharArray();
                        stringArray2[tempStringPhone2.length() - 2] = stringArray2[tempStringPhone2.length() - 1];
                        stringArray2[tempStringPhone2.length() - 1] = c;

                        //code to convert charArray back to String..
                        tempStringPhone2 = new String(stringArray2);
                        Date_Year.setText(tempStringPhone2);
                        Date_Year.setSelection(tempStringPhone2.length());
                        tempStringPhone2 = null;
                    }
                }
            }
        });


        view.findViewById(R.id.proceed_to_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (Card_Number.getText().toString().length() != 19) {
                    Card_Number.setError("invalid Card No");
                    return;
                }
                if (Date_Year.getText().toString().length() != 5) {
                    Card_Number.setError("invalid Expiry");
                    return;
                }
                if (Code.getText().toString().length() != 3) {
                    Code.setError("invalid CVC");
                    return;
                }
                if (ZIP.getText().toString().length() == 0) {
                    ZIP.setError("invalid billing address");
                    return;
                }
                BookTrainer();

                String[] Split = Date_Year.getText().toString().split("/");
                Card card = new Card(Card_Number.getText().toString(), Integer.parseInt(Split[0]), Integer.parseInt("20" + Split[0]), Code.getText().toString());

                card.validateNumber();
                card.validateCVC();

                if (!card.validateCard()) {

                    Stripe stripe = new Stripe(getActivity(), "pk_test_6pRNASCoBOKtIshFeQd4XMUh");
                    stripe.createToken(
                            card,
                            new TokenCallback() {
                                public void onSuccess(Token token) {
                                    // Send token to your server
                                }

                                public void onError(Exception error) {
                                    // Show localized error message
                                    Toast.makeText(getActivity(),
                                            "Error!",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                    );
                }
                else {

                }
            }
        });


        return view;
    }
    private void BookTrainer() {
        spotsDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject jsonObject = new JSONObject();


        try {
//modelNearByTrainers.getEmail()
            jsonObject.put("Trainer", GeneralUtils.email);
            jsonObject.put("DateFrom", GeneralUtils.date);
            jsonObject.put("TimeFrom", GeneralUtils.time2);
            jsonObject.put("TimeTo", GeneralUtils.hours);
            jsonObject.put("Method", "Stripe");
            Log.e("values",GeneralUtils.email+GeneralUtils.date);

        } catch (JSONException Ex) {

        }

        final String mRequestBody = jsonObject.toString();
        String URL = GeneralUtils.URL + "api/booking/PlaceBooking";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                spotsDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {
                        Toast.makeText(getActivity(), "Trainer Booked Success", Toast.LENGTH_LONG).show();
                    }
                    else if (response.contains("Trainer successfully booked"))
                    {
                        Toast.makeText(getActivity(), "Trainer Booked Success", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Trainer not ready to book", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException Ex) {
                    Toast.makeText(getActivity(), "Trainer not available at this time", Toast.LENGTH_LONG).show();

                }
               /* try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200) {
                        Toast.makeText(getActivity(), "Trainer Booked Success", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException Ex) {
                    Toast.makeText(getActivity(), "Trainer not available at this time", Toast.LENGTH_LONG).show();

                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                Log.e("LOG_VOLLEY", error.toString());

                Toast.makeText(getActivity(), "Payment Error", Toast.LENGTH_SHORT).show();
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

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };

      //  stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}