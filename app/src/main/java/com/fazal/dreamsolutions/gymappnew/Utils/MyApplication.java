package com.fazal.dreamsolutions.gymappnew.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fazal.dreamsolutions.gymappnew.Objects.ModelLogin;
import com.fazal.dreamsolutions.gymappnew.R;

import br.vince.easysave.EasySave;
import dmax.dialog.SpotsDialog;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Akshay Raj on 7/17/2016.
 * Snow Corporation Inc.
 * www.snowcorp.org
 */
public class MyApplication extends MultiDexApplication {
    public static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;


    Context mContext;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private static final String IS_LOGIN = "IsLoggedIn";
    static final String MYPREFERENCES = "MyPrefs";



    ModelLogin modelLogin = new ModelLogin();


    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = getApplicationContext();
        mInstance = this;

        editor = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public ModelLogin getLoginSession() {
        return new EasySave(getApplicationContext()).retrieveModel("User", ModelLogin.class);

    }
    public void onShowLoading()
    {
        new SpotsDialog(getApplicationContext(), R.style.Custom).show();
    }
    public void onHideLoading()
    {
        new SpotsDialog(getApplicationContext(), R.style.Custom).hide();
    }

    public void createLoginSession(ModelLogin modelLogin) {
        editor.putBoolean(IS_LOGIN, true);
        new EasySave(getApplicationContext()).saveModel("User", modelLogin);
        editor.apply();
    }

    public void clearUserPreference(Context mContext) {

        this.mContext = mContext;
        editor.clear();
        editor.commit();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


}