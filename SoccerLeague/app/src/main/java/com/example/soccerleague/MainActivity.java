package com.example.soccerleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button getDataButton;
    private Button teamListButton;
    private Button drawFixtureButton;
    private Button switch_btn;
    private Button systemDarkMode;
    private UiModeManager uiModeManager;

    private List<Team> teamList = new ArrayList<>();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getSharedPreferences("0",MODE_PRIVATE);
        final SharedPreferences.Editor editor1 = sharedPreferences.edit();
//        boolean systemDarkMode;
//        if (AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM == -1) { // bakılacak
//            systemDarkMode = false;
//        }
//        else {
//            systemDarkMode = true;
//        }
//        Log.d("darkMode", "" + systemDarkMode);

        final boolean isNightModeOn = sharedPreferences.getBoolean("NightMode", false); // systemDarkMode

        getDataButton = findViewById(R.id.getDataButton);
        teamListButton = findViewById(R.id.teamListButton);
        drawFixtureButton = findViewById(R.id.drawFixtureButton);
        switch_btn = findViewById(R.id.switch_btn);
        systemDarkMode = findViewById(R.id.systemDarkMode);

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent( MainActivity.this, DrawFixtureActivity.class);
//                startActivity(intent); // rename edilecek

//                ESPNParser espnParser = new ESPNParser();
//                espnParser.getTeamNames();

                new ESPNParser(MainActivity.this).execute();

            }
        });

        teamListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, TeamListActivity.class);
                startActivity(intent); // rename edilecek

            }
        });

        drawFixtureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, DrawFixtureActivity.class);
                startActivity(intent); // rename edilecek

            }
        });

        if(isNightModeOn){ // if(systemDarkMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switch_btn.setText("DISABLE DARK MODE");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            switch_btn.setText("ENABLE DARK MODE");
        }

        switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor1.putBoolean("NightMode", false);
                    editor1.apply();

                    Toast toast = Toast.makeText(getApplicationContext(), "DARK MODE ENABLED.", Toast.LENGTH_LONG);
                    toast.show();
                    switch_btn.setText("DISABLE DARK MODE");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor1.putBoolean("NightMode", true);
                    editor1.apply();

                    Toast toast = Toast.makeText(getApplicationContext(), "DARK MODE DISABLED.", Toast.LENGTH_LONG);
                    toast.show();
                    switch_btn.setText("ENABLE DARK MODE");
                }

            }
        });

        systemDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES ) {
                    // enabled dark mdode in system settings
                    Log.d("systemmDarkMode", "dark");

                    if(isNightModeOn){

                        Toast toast = Toast.makeText(getApplicationContext(), "SAME MODE WITH SYSTEM SETTINGS.", Toast.LENGTH_LONG);
                        toast.show();

                    } else {

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor1.putBoolean("NightMode", true);
                        editor1.apply();

                        Toast toast = Toast.makeText(getApplicationContext(), "DARK MODE DISABLED.", Toast.LENGTH_LONG);
                        toast.show();
                        switch_btn.setText("ENABLE DARK MODE");
                    }

                }
                else {
                    // disabled dark mdode in system settings
                    Log.d("systemmDarkMode", "lght");

                    if(isNightModeOn){

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor1.putBoolean("NightMode", false);
                        editor1.apply();

                        Toast toast = Toast.makeText(getApplicationContext(), "DARK MODE ENABLED.", Toast.LENGTH_LONG);
                        toast.show();
                        switch_btn.setText("DISABLE DARK MODE");

                    } else {

                        Toast toast = Toast.makeText(getApplicationContext(), "SAME MODE WITH SYSTEM SETTINGS.", Toast.LENGTH_LONG);
                        toast.show();

                    }

                }

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                Toast toast = Toast.makeText(getApplicationContext(), "SYSTEM DARK MODE FOLLOWED.", Toast.LENGTH_LONG);

            }
        });

















    }

    public void setList(List<Team> list) {
        this.teamList = list;

        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        databaseHelper.addAllTeams(teamList);
    }
}
