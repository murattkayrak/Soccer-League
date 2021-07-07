package com.example.soccerleague;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ESPNParser extends AsyncTask {

    private final String baseURL; //root URL
    private List<Team> teamList;
    private League league;

    private MainActivity mainActivity;

    public ESPNParser(MainActivity mainActivity) {
        baseURL = "https://www.espn.com/football/teams";
        teamList = new ArrayList<>();
        this.mainActivity = mainActivity;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            league = new League("league test");

            Document document = Jsoup.connect(baseURL).timeout(0).get();
            Elements teamRow = document.getElementsByClass("ContentList__Item");

            Log.d("JSOUP", teamRow.toString());
            int i = 0;
            teamList.clear();

            for (Element element : teamRow ) {

                String teamName = element.getElementsByClass("di clr-gray-01 h5").text();
                Log.d("JSOUPbb name " + i, teamName); // team name
                i++;

                if (!teamName.isEmpty()) {
                    Team team = new Team(teamName, league, "pictureLink");
                    teamList.add(team);

                    Log.d("JSOUPbb picture link " + i, element.getElementsByClass("AnchorLink").select("img").first().attr("abs:src").toString()); // team picture

                }

            }

            league.setTeamCount(teamList.size());

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        mainActivity.setList(teamList);
    }

}
