package com.fazal.dreamsolutions.gymappnew.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.fazal.dreamsolutions.gymappnew.Fragments.Chat_Users;
import com.fazal.dreamsolutions.gymappnew.Fragments.MainFragment;
import com.fazal.dreamsolutions.gymappnew.FragmentsTrainee.TraineeHistoryFragment;
import com.fazal.dreamsolutions.gymappnew.FragmentsTrainer.NearByTrainers;
import com.fazal.dreamsolutions.gymappnew.FragmentsTrainer.TrainerProfileFragment;
import com.fazal.dreamsolutions.gymappnew.R;
import com.fazal.dreamsolutions.gymappnew.Utils.MyApplication;

public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView btnDrawer;

    MyApplication myApplication;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        myApplication = (MyApplication) getApplicationContext();


        if (myApplication.isLoggedIn()) {
            if (myApplication.getLoginSession().getToken().getUserType().toLowerCase().contains("ee")) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new NearByTrainers()).commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TraineeHistoryFragment()).commit();
            }


        } else {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MainFragment()).commit();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        validiateUser();

    }

    public void validiateUser() {
        Menu nav_Menu = navigationView.getMenu();
        try {
            if (myApplication.getLoginSession().getToken().getUserType().contains("ee")) {

                nav_Menu.findItem(R.id.nav_profile).setVisible(false);
                nav_Menu.findItem(R.id.nav_history).setVisible(true);
            } else {
                nav_Menu.findItem(R.id.nav_history).setVisible(false);
                nav_Menu.findItem(R.id.nav_profile).setVisible(true);

            }
        } catch (Exception Ex) {

        }
    }


    public void OpenOpenOrCloseDrawer() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                super.onBackPressed();
            }

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        try {

            if (id == R.id.nav_home) {

                if (myApplication.getLoginSession().getToken().getUserType().contains("ee")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new NearByTrainers()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new TraineeHistoryFragment()).commit();
                }

            } else if (id == R.id.nav_logout) {

                myApplication.logoutUser();

                Intent intent = new Intent(getApplicationContext(), MainDrawerActivity.class);
                startActivity(intent);
                finish();


            } else if (id == R.id.nav_profile) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TrainerProfileFragment()).addToBackStack("tag").commit();

            } else if (id == R.id.nav_history) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TraineeHistoryFragment()).addToBackStack("tag").commit();

            } else if (id == R.id.Chat) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Chat_Users()).addToBackStack("tag").commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } catch (Exception Ex) {

        }


        return true;
    }
}