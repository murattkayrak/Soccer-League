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
//            Elements teamRow = document.getElementsByClass("mt3");

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
//                    Log.d("JSOUPbb picture " + i, element.select("div.mt3").first().select("img").first().absUrl("data-original")); // team picture
//                    Log.d("JSOUPbb picture x " + i, element.select("img").first().absUrl("src")); // team picture

//            Log.d("JSOUP", );
//                String pictureLink = element.select("div.pl3").first().toString();
//                String pictureLink = element.select("img").first().absUrl("src");
//                Log.d("JSOUP pictureLink", pictureLink);

                }


            }

            league.setTeamCount(teamList.size());








//            Log.d("JSOUP", );
//            Log.d("JSOUP", );
//            Log.d("JSOUP", );
//            Log.d("JSOUP", );
//            Log.d("JSOUP", );





        }
        catch (IOException e) {
            e.printStackTrace();
        }


//        return teamList;
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        mainActivity.setList(teamList);
//        DatabaseHelper databaseHelper = new DatabaseHelper(TeamListActivity.listContext);
//        databaseHelper.addAllTeams(teamList);


    }

    public List<Team> getTeamNames() {
        return null;
    }



}
