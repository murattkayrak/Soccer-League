package com.example.soccerleague;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button getDataButtonESPN;
    private Button getDataButtonMackolik;
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

        final boolean isNightModeOn = sharedPreferences.getBoolean("NightMode", false);

        getDataButtonESPN = findViewById(R.id.getDataButtonESPN);
        getDataButtonMackolik = findViewById(R.id.getDataButtonMackolik);
        teamListButton = findViewById(R.id.teamListButton);
        drawFixtureButton = findViewById(R.id.drawFixtureButton);
        switch_btn = findViewById(R.id.switch_btn);
        systemDarkMode = findViewById(R.id.systemDarkMode);

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        getDataButtonESPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(0);
            }
        });

        getDataButtonMackolik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(1);
            }
        });

        teamListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, TeamListActivity.class);
                startActivity(intent);

            }
        });

        drawFixtureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Match list is creating... Please wait.", Toast.LENGTH_LONG ).show();
                Intent intent = new Intent( MainActivity.this, DrawFixtureActivity.class);
                startActivity(intent);

            }
        });

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switch_btn.setText("DISABLE DARK MODE");
        }
        else {
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

    public void alertDialog (final int index) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Getting Teams");
        builder.setMessage("Are you sure to recreate team list?");

        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(MainActivity.this, "Team list is not creating.", Toast.LENGTH_LONG ).show();

            }
        });


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if ( index == 0) {
                    new ESPNParser(MainActivity.this).execute();

                }
                else {
                    new MackolikParser(MainActivity.this).execute();

                }
                Toast.makeText(MainActivity.this, "Team list is creating... Please wait.", Toast.LENGTH_LONG ).show();

            }
        });

        builder.show();

    }
}
