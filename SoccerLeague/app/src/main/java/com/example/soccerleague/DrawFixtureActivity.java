package com.example.soccerleague;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
        for (i = 0; i < teamList.size(); i++) {
            adapter.addFragment(new MatchFragment(i), String.valueOf(i+1));
        }
        viewPager.setAdapter(adapter);

    }

    public ArrayList<Match> drawFixture() {

        ArrayList<Match> matchList = new ArrayList<>();
        teamList.clear();
        matchList.clear();

        DatabaseHelper databaseHelper = new DatabaseHelper(DrawFixtureActivity.this);
        teamList = databaseHelper.getAllTeams();

        int i, j, size = teamList.size();

        for ( i = 0; i < size; i++ ) {

            for ( j = 0; j < size; j++) {

                int homeIndex = (i + j) % ( size - 1 );
                int awayIndex = (size - 1 -j + i ) % ( size - 1 );

                if ( homeIndex != awayIndex ) {
                    Match match = new Match(teamList.get(homeIndex), teamList.get(awayIndex), i, "X : X", "date", "time");
                    matchList.add(match);
                }
            }
        }

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