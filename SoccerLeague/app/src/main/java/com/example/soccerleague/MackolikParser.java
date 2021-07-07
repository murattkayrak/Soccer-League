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

public class MackolikParser extends AsyncTask {

    private final String baseURL; //root URL
    private List<Team> teamList;
    private League league;

    private MainActivity mainActivity;

    public MackolikParser(MainActivity mainActivity) {
        baseURL = "https://www.mackolik.com/puan-durumu/t%C3%BCrkiye-s%C3%BCper-lig/482ofyysbdbeoxauk19yg7tdt";
        teamList = new ArrayList<>();
        this.mainActivity = mainActivity;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            league = new League("league test");

            Document document = Jsoup.connect(baseURL).timeout(0).get();
            Elements teamRow = document.getElementsByClass("p0c-competition-tables__team-name p0c-competition-tables__team-name--full");
            Elements teamImageLinks = document.getElementsByClass("p0c-competition-tables__link");

            Log.d("JSOUP", teamRow.toString());
            Log.d("JSOUPqq", teamImageLinks.toString());
            int i = 0;
            teamList.clear();

            for (Element element : teamRow ) {

                String teamName = element.select("span").first().text();
                Log.d("JSOUPbb name " + i, teamName); // team name

                if (!teamName.isEmpty()) {
                    String pictureLink = teamImageLinks.get(i).select("img").first().attr("src");
                    Team team = new Team(teamName, league, pictureLink);
                    teamList.add(team);

                    Log.d("JSOUPbb picture link " + i, pictureLink); // team picture

                }
                i++;


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
