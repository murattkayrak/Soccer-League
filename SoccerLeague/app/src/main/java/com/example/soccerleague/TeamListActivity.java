package com.example.soccerleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeamListActivity extends AppCompatActivity {

    public static Context listContext;

    private List<Team> teamList = new ArrayList<>();

    private RecyclerView recyclerViewTeam;
    private TeamAdapter teamAdapter;
    private Toolbar toolbarTamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        toolbarTamList = (Toolbar) findViewById(R.id.toolbarTeam);
        setSupportActionBar(toolbarTamList);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerViewTeam = findViewById(R.id.teamRecylerview);


        listContext = getApplicationContext();

        teamList.clear();
        DatabaseHelper databaseHelper = new DatabaseHelper(TeamListActivity.this);
        teamList = databaseHelper.getAllTeams();

        Log.d("teamlist", String.valueOf(teamList.size()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTeam.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);

        teamAdapter = new TeamAdapter(teamList);
        recyclerViewTeam.setAdapter(teamAdapter);
        teamAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
