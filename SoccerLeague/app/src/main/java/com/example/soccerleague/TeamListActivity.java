package com.example.soccerleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeamListActivity extends AppCompatActivity {

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



        // ---------------------------
        League league = new League("league test", 5);
        Team hometeam = new Team("hometeam", league);
        Team awayteam = new Team("awayteam", league);
        teamList.add(hometeam);
        teamList.add(awayteam);
        // ---------------------------







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
