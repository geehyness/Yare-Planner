package com.yukisoft.yare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yukisoft.yare.Fragments.DayFragment;
import com.yukisoft.yare.Fragments.ProfileFragment;
import com.yukisoft.yare.Fragments.SettingsFragment;
import com.yukisoft.yare.Fragments.WeekFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView navDay, navWeek, navSettings, navProfile, navOpen, closeTaskPanel;
    private boolean isOpenNav = true, isOpenTaskAdder = false;
    ConstraintLayout nav;
    ScrollView addTaskPanel;
    FloatingActionButton btnAddTask;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nav = findViewById(R.id.nav);
        navDay = findViewById(R.id.navDay);
        navWeek = findViewById(R.id.navWeek);
        navProfile = findViewById(R.id.navProfile);
        navSettings = findViewById(R.id.navSettings);
        navOpen = findViewById(R.id.navOpen);
        navOpen.setZ(3f);

        addTaskPanel = findViewById(R.id.addTaskPanel);
        btnAddTask = findViewById(R.id.btnAddTask);
        closeTaskPanel = findViewById(R.id.btnCloseTask);
        container = findViewById(R.id.fragment_container);

        setupInputPanel();

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setZ(1f);
                addTaskPanel.setZ(2f);
                slideActivityAddIn();
                btnAddTask.hide();
            }
        });
        closeTaskPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = HomeActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }

                slideActivityAddOut();
                btnAddTask.show();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        //container.bringToFront();
                        container.setZ(2f);
                        addTaskPanel.setZ(1f);
                    }
                }, 500);
            }
        });

        navDay.setOnClickListener(this);
        navWeek.setOnClickListener(this);
        navProfile.setOnClickListener(this);
        navSettings.setOnClickListener(this);
        navOpen.setOnClickListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DayFragment()).commit();
            navDay.setImageResource(R.drawable.day_active);
            navWeek.setImageResource(R.drawable.week_inactive);
            navProfile.setImageResource(R.drawable.profile_inactive);
            navSettings.setImageResource(R.drawable.settings_inactive);
        }
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;

        switch (v.getId()){
            case R.id.navDay:
                selectedFragment = new DayFragment();

                navDay.setImageResource(R.drawable.day_active);
                navWeek.setImageResource(R.drawable.week_inactive);
                navProfile.setImageResource(R.drawable.profile_inactive);
                navSettings.setImageResource(R.drawable.settings_inactive);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navWeek:
                selectedFragment = new WeekFragment();

                navDay.setImageResource(R.drawable.day_inactive);
                navWeek.setImageResource(R.drawable.week_active);
                navProfile.setImageResource(R.drawable.profile_inactive);
                navSettings.setImageResource(R.drawable.settings_inactive);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navProfile:
                selectedFragment = new ProfileFragment();

                navDay.setImageResource(R.drawable.day_inactive);
                navWeek.setImageResource(R.drawable.week_inactive);
                navProfile.setImageResource(R.drawable.profile_active);
                navSettings.setImageResource(R.drawable.settings_inactive);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navSettings:
                selectedFragment = new SettingsFragment();

                navDay.setImageResource(R.drawable.day_inactive);
                navWeek.setImageResource(R.drawable.week_inactive);
                navProfile.setImageResource(R.drawable.profile_inactive);
                navSettings.setImageResource(R.drawable.settings_active);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navOpen:
                if (isOpenNav) {
                    isOpenNav = false;
                    slideNavOut(nav);
                } else {
                    isOpenNav = true;
                    slideNavIn(nav);
                }
                break;
        }
    }

    // slide the view from below itself to the current position
    public void slideNavIn(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                -view.getWidth(),                 // fromXDelta
                0,                 // toXDelta
                0,           // fromYDelta
                0);                // toYDelta
        animate.setDuration(10);
        animate.setFillAfter(true);
        view.startAnimation(animate);

        navOpen.setImageResource(R.drawable.ic_left);
    }

    // slide the view from its current position to below itself
    public void slideNavOut(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                -view.getWidth(),                 // toXDelta
                0,                 // fromYDelta
                0); // toYDelta
        animate.setDuration(10);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);

        navOpen.setImageResource(R.drawable.ic_right);
    }

    // slide the view from below itself to the current position
    public void slideActivityAddIn(){
        addTaskPanel.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                addTaskPanel.getWidth(),                 // fromXDelta
                0,                 // toXDelta
                0,           // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        addTaskPanel.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideActivityAddOut(){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                addTaskPanel.getWidth(),                 // toXDelta
                0,                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        addTaskPanel.startAnimation(animate);
        //addTaskPanel.setVisibility(View.GONE);
    }

    public void setupInputPanel(){
        container.setZ(2f);
        addTaskPanel.setZ(1f);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                slideActivityAddOut();
            }
        }, 500);
    }
}
