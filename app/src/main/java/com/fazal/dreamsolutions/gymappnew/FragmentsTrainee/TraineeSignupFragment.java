package com.fazal.dreamsolutions.gymappnew.FragmentsTrainee;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fazal.dreamsolutions.gymappnew.Fragments.MainFragment;
import com.fazal.dreamsolutions.gymappnew.Objects.ModelLogin;
import com.fazal.dreamsolutions.gymappnew.R;
import com.fazal.dreamsolutions.gymappnew.Utils.GeneralUtils;
import com.fazal.dreamsolutions.gymappnew.Utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class TraineeSignupFragment extends Fragment {
    View view;
    EditText etName, etMobileNo, etPassword, etEmail;
    CheckBox checkBox;
    Button btnSignUp;
 //   ImageView ivFacebook, ivTwitter;
    TextView tvTerms, tvLogin;
    Fragment fragment;
    TextView tvOk, tvCancel;
    MyApplication myApplication;
    SpotsDialog spotsDialog;


    public TraineeSignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.trainee_signup, container, false);
        spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        myApplication = (MyApplication) getActivity().getApplicationContext();
        etName = view.findViewById(R.id.name);
        etEmail = view.findViewById(R.id.email);
        etMobileNo = view.findViewById(R.id.mobile_no);
        etPassword = view.findViewById(R.id.password);

        checkBox = view.findViewById(R.id.terms);
        tvTerms = view.findViewById(R.id.terms_and_conditions);
        tvLogin = view.findViewById(R.id.login);

        btnSignUp = view.findViewById(R.id.signup);
      //  ivFacebook = view.findViewById(R.id.facebook);
        //ivTwitter = view.findViewById(R.id.twitter);

        clickHandling();
        return view;
    }

    private void clickHandling() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Name is empty", Toast.LENGTH_SHORT).show();
                } else if (etEmail.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Email is empty", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                    Toast.makeText(getActivity(), "Incorrect Email format", Toast.LENGTH_SHORT).show();
                } else if (etMobileNo.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Mobile Number is empty", Toast.LENGTH_SHORT).show();
                } else if (etMobileNo.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), "Mobile Number is less than 6 digits", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Password is empty", Toast.LENGTH_SHORT).show();
                } else {
                    callApi();
                }

            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // test();
                fragment = new TraineeLoginFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).commit();
            }
        });
        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.terms_dialog);
                dialog.setCancelable(true);
                tvOk = (TextView) dialog.findViewById(R.id.ok);
                tvCancel = (TextView) dialog.findViewById(R.id.cancel);
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void callApi() {
        //  new SpotsDialog(getActivity(), R.style.Custom).show();
        spotsDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = GeneralUtils.URL + "api/account/register";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Email", etEmail.getText().toString());
            jsonObject.put("Password", etPassword.getText().toString());
            jsonObject.put("Name", etName.getText().toString());
            jsonObject.put("Phone", etMobileNo.getText().toString());
            jsonObject.put("UserType", "Trainee");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonObject.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   new SpotsDialog(getActivity(), R.style.Custom).hide();
                spotsDialog.dismiss();
                Log.i("LOG_VOLLEY", response);
                //  Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                if (response.contains("User with this phone number already exists!")) {
                    Log.e("response", "Phone No already exists");
                    Toast.makeText(getActivity(), "Mobile No Already Used", Toast.LENGTH_SHORT).show();
                } else if (response.toLowerCase().contains("email") && response.toLowerCase().contains("taken")) {
                    Log.e("response", "Email already exists");
                    Toast.makeText(getActivity(), "Email Already used", Toast.LENGTH_SHORT).show();
                } else if (response.contains("User Successfully Registered!")) {

                    ModelLogin login = new ModelLogin();

                    try {
                        JSONObject json = new JSONObject(response);
                        login.getToken().setAccess_token(json.getString("access_token"));
                        login.getToken().setEmail(etEmail.getText().toString());
                        login.getToken().setName(json.getString("Name"));
                        login.getToken().setPhone(json.getString("Phone"));
                        login.getToken().setUserType(json.getString("UserType"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    myApplication.createLoginSession(login);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new TraineeHistoryFragment()).commit();

                    Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_SHORT).show();
                    Log.e("response", "User registered successfully");
                } else if (response.contains("The Password must be at least 6 characters long.")) {
                    Toast.makeText(getActivity(), "Password must be 6 characters long", Toast.LENGTH_SHORT).show();
                } else if (response.toLowerCase().contains("password")) {
                    Toast.makeText(getActivity(), "Password must be long and complex", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("response", "Password error");
                    Toast.makeText(getActivity(), "password long and complex password", Toast.LENGTH_SHORT).show();
                }
             /*   if (response.contains("exists"))
                {
                    Log.e("tag", "exists");
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spotsDialog.dismiss();
                //   new SpotsDialog(getActivity(), R.style.Custom).hide();
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

        requestQueue.add(stringRequest);
    }
}
