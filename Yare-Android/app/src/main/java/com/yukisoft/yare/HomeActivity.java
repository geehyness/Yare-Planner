package com.yukisoft.yare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.yukisoft.yare.Fragments.DayFragment;
import com.yukisoft.yare.Fragments.ProfileFragment;
import com.yukisoft.yare.Fragments.SettingsFragment;
import com.yukisoft.yare.Fragments.WeekFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView navDay, navWeek, navSettings, navProfile, navOpen;
    private boolean isOpen = true;
    ConstraintLayout nav;

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
            navProfile.setImageResource(R.drawable.user_inactive);
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
                navProfile.setImageResource(R.drawable.user_inactive);
                navSettings.setImageResource(R.drawable.settings_inactive);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navWeek:
                selectedFragment = new WeekFragment();

                navDay.setImageResource(R.drawable.day_inactive);
                navWeek.setImageResource(R.drawable.week_active);
                navProfile.setImageResource(R.drawable.user_inactive);
                navSettings.setImageResource(R.drawable.settings_inactive);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navProfile:
                selectedFragment = new ProfileFragment();

                navDay.setImageResource(R.drawable.day_inactive);
                navWeek.setImageResource(R.drawable.week_inactive);
                navProfile.setImageResource(R.drawable.user_active);
                navSettings.setImageResource(R.drawable.settings_inactive);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navSettings:
                selectedFragment = new SettingsFragment();

                navDay.setImageResource(R.drawable.day_inactive);
                navWeek.setImageResource(R.drawable.week_inactive);
                navProfile.setImageResource(R.drawable.user_inactive);
                navSettings.setImageResource(R.drawable.settings_active);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                break;

            case R.id.navOpen:
                if (isOpen) {
                    isOpen = false;
                    slideOut(nav);
                } else {
                    isOpen = true;
                    slideIn(nav);
                }
                break;
        }
    }

    // slide the view from below itself to the current position
    public void slideIn(View view){
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
    public void slideOut(View view){
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
}
