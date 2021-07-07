package com.example.soccerleague;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {

    private List<Team> teamList = new ArrayList<>();

    public TeamAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView listTeamName;
        public ImageView teamImage;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            listTeamName = view.findViewById(R.id.listTeamName);
            teamImage = view.findViewById(R.id.teamImage);

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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listTeamName.setText(teamList.get(position).getName());
        String imageURL = teamList.get(position).getPictureLink();
        Log.d("imageURL", " kontrol " + imageURL);
        if (imageURL != null) {

            //put here picaso image load code
            Picasso.with(holder.teamImage.getContext())
                    .load(imageURL)
                    .placeholder(R.drawable.emptyicon)
                    .error(R.drawable.emptyicon)
                    .fit()
                    .into(holder.teamImage);

        }
        else {
            holder.teamImage.setImageResource(R.drawable.emptyicon);
        }
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }
}
