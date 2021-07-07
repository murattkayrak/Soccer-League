package com.example.soccerleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "LeagueManager.db";

    private static final String TABLE_TEAM = "TEAM";
    private static final String TABLE_MATCH = "MATCH";

    private static final String COLUMN_TEAM_ID = "TEAM_ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_TEAM_PICTURE = "TEAM_PICTURE";

    private static final String COLUMN_MATCH_ID = "COLUMN_MATCH_ID";
    private static final String COLUMN_HOME_TEAM = "HOME_TEAM";
    private static final String COLUMN_AWAY_TEAM = "AWAY_TEAM";
    private static final String COLUMN_WEEK = "WEEK";
    private static final String COLUMN_SCORE = "SCORE";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_TIME = "TIME";

    private static final String CREATE_TEAM_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TEAM + " (" +
                    COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_TEAM_PICTURE + " TEXT" + ")";

    private static final String CREATE_MATCH_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MATCH + " (" +
                    COLUMN_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    COLUMN_HOME_TEAM + " TEXT," +
                    COLUMN_AWAY_TEAM + " TEXT," +
                    COLUMN_WEEK + " INTEGER," +
                    COLUMN_SCORE + " TEXT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_TIME + " TEXT" + ")";

    private static final String DROP_MATCH_TABLE = "DROP TABLE IF EXISTS " + TABLE_MATCH;
    private static final String DROP_TEAM_TABLE = "DROP TABLE IF EXISTS " + TABLE_TEAM;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TEAM_TABLE);
        db.execSQL(CREATE_MATCH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(CREATE_TEAM_TABLE);
        db.execSQL(CREATE_MATCH_TABLE);
        onCreate(db);

    }

    public void addAllTeams(List<Team> teamList) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        sqLiteDatabase.execSQL(DROP_TEAM_TABLE);
        sqLiteDatabase.execSQL(CREATE_TEAM_TABLE);

        for (Team team : teamList) {
            contentValues.put(COLUMN_NAME, team.getName());
            contentValues.put(COLUMN_TEAM_PICTURE, team.getPictureLink());
            sqLiteDatabase.insert(TABLE_TEAM, null, contentValues);

        }

        sqLiteDatabase.close();

    }

    public List<Team> getAllTeams() {

        List<Team> teamList = new ArrayList<>();
        teamList.clear();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;

        String[] columns = {
                COLUMN_NAME,
                COLUMN_TEAM_PICTURE
        };

        cursor = sqLiteDatabase.query( TABLE_TEAM,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

            do {
                Team team = new Team(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TEAM_PICTURE))
                );

                teamList.add(team);

            }
            while (cursor.moveToNext());

        }

        sqLiteDatabase.close();
        cursor.close();

        return teamList;
    }

    public void addAllMatches(List<Match> matchList) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        sqLiteDatabase.execSQL(DROP_MATCH_TABLE);
        sqLiteDatabase.execSQL(CREATE_MATCH_TABLE);

        for (Match match : matchList) {
            contentValues.put(COLUMN_HOME_TEAM, match.getHomeTeam().getName());
            contentValues.put(COLUMN_AWAY_TEAM, match.getAwayTeam().getName());
            contentValues.put(COLUMN_WEEK, match.getWeek());
            contentValues.put(COLUMN_SCORE, match.getScore());
            contentValues.put(COLUMN_DATE, match.getDate());
            contentValues.put(COLUMN_TIME, match.getTime());
            sqLiteDatabase.insert(TABLE_MATCH, null, contentValues);

        }

        sqLiteDatabase.close();


    }

    public List<Match> getWeekMatches (int week) {

        List<Match> matchList = new ArrayList<>();
        matchList.clear();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;

        String[] columns = {
                COLUMN_HOME_TEAM,
                COLUMN_AWAY_TEAM,
                COLUMN_WEEK,
                COLUMN_SCORE,
                COLUMN_DATE,
                COLUMN_TIME
        };

        String selection = COLUMN_WEEK + " = ?";

        String[] selectionArgs = {String.valueOf(week)};

        cursor = sqLiteDatabase.query( TABLE_MATCH,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        if (cursor.moveToFirst()) {
            do {
                Match match = new Match(
                        new Team(cursor.getString(cursor.getColumnIndex(COLUMN_HOME_TEAM))),
                        new Team(cursor.getString(cursor.getColumnIndex(COLUMN_AWAY_TEAM))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WEEK))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCORE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TIME))
                );

                matchList.add(match);

            }
            while (cursor.moveToNext());
        }

        sqLiteDatabase.close();
        cursor.close();

        Log.d(" matchlist size", String.valueOf(matchList.size()));

        return matchList;


    }







}
