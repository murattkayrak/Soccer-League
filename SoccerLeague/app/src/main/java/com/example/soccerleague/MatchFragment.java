package com.example.soccerleague;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment {

    private List<Match> matchList = new ArrayList<>();
    private int position;
    private RecyclerView recyclerView;
    private MatchAdapter matchAdapter;
    private TextView tvFragText;

    public MatchFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        matchList.clear();
        matchList = databaseHelper.getWeekMatches(this.position);

        recyclerView = v.findViewById(R.id.fragmentRecylerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);

        matchAdapter = new MatchAdapter(matchList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(matchAdapter);
        matchAdapter.notifyDataSetChanged();

        tvFragText = v.findViewById(R.id.tvFragText);
        tvFragText.append(String.valueOf(this.position + 1));

        Log.d("fragment", "this.position= " + this.position );

        return v;
    }
}
