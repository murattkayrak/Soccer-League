package com.example.soccerleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DrawFixtureActivity extends AppCompatActivity {

    public static ProgressBar progressBar;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Team> teamList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_fixture);

        progressBar = findViewById(R.id.loadingPanel);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

//        drawFixture(); // maç eşleşmeleri yapılarak sonuçlar db aktarılacak
        DatabaseHelper databaseHelper = new DatabaseHelper(DrawFixtureActivity.this);
        databaseHelper.addAllMatches(drawFixture());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        int i;
        for (i = 0; i < teamList.size() - 5; i++) { // takım sayısı düzeltilince -5 ortadan kaldırılmalı
            adapter.addFragment(new MatchFragment(i), String.valueOf(i));
        }
        viewPager.setAdapter(adapter);


        // aşağıdaki blok otomatize edilecek
        // takım listesi boyutu kadar ekleme yapılacak, position i, title week i+1 olarak gönderilecek

//        adapter.addFragment(new MatchFragment(1), "COMMPANY 0");
//        adapter.addFragment(new MatchFragment(2), "SPAM 0");
//        adapter.addFragment(new MatchFragment(3), "PERSONAL 1");
//        adapter.addFragment(new MatchFragment(4), "COMMPANY 1");
//        adapter.addFragment(new MatchFragment(5), "SPAM 1");
//        adapter.addFragment(new MatchFragment(6), "PERSONAL 2");
//        adapter.addFragment(new MatchFragment(7), "COMMPANY 2");
//        adapter.addFragment(new MatchFragment(8), "SPAM 2");
    }

    public ArrayList<Match> drawFixture() {

        ArrayList<Match> matchList = new ArrayList<>();
        teamList.clear();
        matchList.clear();

        DatabaseHelper databaseHelper = new DatabaseHelper(DrawFixtureActivity.this);
        teamList = databaseHelper.getAllTeams();

//        teamList = new ESPNParser().execute(); // db helper eklenecek / ordan takım listesi alınacak / dbHelper.getteamlist()

        int i = 0, j = 0, size = teamList.size();
//        size = 20; // 20 takım olmasına rağmen 25 görünüyor, manuel müdahale edildi

        for ( i = 0; i < size; i++ ) {

            for ( j = 0; j < size; j++) {

//                if ( i != j ) {
//                    Match match = new Match(teamList.get(i), teamList.get(j), i, "score", "date", "time"); // i ve j ile
                int homeIndex = (i + j) % ( size - 1 );
                int awayIndex = (size - 1 -j + i ) % ( size - 1 );

                if ( homeIndex != awayIndex ) { // iki kere eşitleniyorlar, beklenen 1
                    Match match = new Match(teamList.get(homeIndex), teamList.get(awayIndex), i, "score", "date", "time");
                    matchList.add(match);

                }
            }
        }

        /*
        for (Team teamHome : teamList) {
            Log.d("MAINteamlist", teamHome.getName());

            for ( Team teamAway : teamList) {
                if ( i != j) {
                    Match match = new Match(teamHome, teamAway, 74, "score", "date", "time");
                    matchList.add(match);
                }
            }

        }
        */

        Log.d("MAINteamlist", "teamlistsize: " + size + " matchlist size:  " + matchList.size());

        progressBar.setVisibility(View.INVISIBLE);

        return matchList;
    }

}


class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}