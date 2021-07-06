package com.example.soccerleague;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button getDataButton;
    private Button teamListButton;
    private Button drawFixtureButton;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataButton = findViewById(R.id.getDataButton);
        teamListButton = findViewById(R.id.teamListButton);
        drawFixtureButton = findViewById(R.id.drawFixtureButton);

        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, DrawFixtureActivity.class);
                startActivity(intent); // rename edilecek

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



















    }
}
