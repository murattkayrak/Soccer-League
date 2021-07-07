package com.example.soccerleague;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {

    private List<Match> matchList = new ArrayList<>();

    public MatchAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView cardHomeTeam;
        private TextView cardScore;
        private TextView cardAwayTeam;
        private TextView cardDate;
        private TextView cardTime;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            cardHomeTeam = view.findViewById(R.id.cardHomeTeam);
            cardScore = view.findViewById(R.id.cardScore);
            cardAwayTeam = view.findViewById(R.id.cardAwayTeam);
            cardDate = view.findViewById(R.id.cardDate);
            cardTime = view.findViewById(R.id.cardTime);

        }

        @Override
        public void onClick(View view) {

        }


        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    @NonNull
    @Override
    public MatchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchAdapter.MyViewHolder holder, int position) {

        holder.cardHomeTeam.setText(String.valueOf(matchList.get(position).getHomeTeam().getName()));
        holder.cardScore.setText(String.valueOf(matchList.get(position).getScore()));
        holder.cardAwayTeam.setText(String.valueOf(matchList.get(position).getAwayTeam().getName()));
        holder.cardDate.setText(String.valueOf(matchList.get(position).getDate()));
        holder.cardTime.setText(String.valueOf(matchList.get(position).getTime()));

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }
}
