package com.anthrino.wifix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.WifiInfo;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Johns on 05-04-2017.
 */

public class networkDBAdapter {
    private WirelessRegDb netDb;
    private SQLiteDatabase db;

    public networkDBAdapter(Context context) {
        netDb = new WirelessRegDb(context, WirelessRegDb.DATABASE_NAME, null, 1);
        db = netDb.getWritableDatabase();//get db writeable object.
        Log.d("debug", "DBAdapter initialised");
    }

    public long InsertWAP(WifiInfo WAPInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SSID", WAPInfo.getSSID());
        contentValues.put("Strength_Level", WAPInfo.getRssi());
        contentValues.put("TimeStamp", DateFormat.getDateTimeInstance().format(new Date()));
        contentValues.put("Frequency", WAPInfo.getFrequency());
        contentValues.put("Link_Speed", WAPInfo.getLinkSpeed());
        contentValues.put("BSSID", WAPInfo.getBSSID());
        Log.d("debug", "Inserting into database");
        return db.insert(netDb.NET_TABLE_NAME, null, contentValues);
    }

    public ArrayList<NetworkInfo> DisplayAllWAPs() {
        ArrayList<NetworkInfo> entries = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + netDb.NET_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            NetworkInfo WAPInfo = new NetworkInfo();
            WAPInfo.setSSID(cursor.getString(0));
            WAPInfo.setLevel(cursor.getInt(1));
            WAPInfo.setTimestamp(cursor.getString(2));
            WAPInfo.setFrequency(cursor.getInt(3));
            WAPInfo.setLinkspeed(cursor.getInt(4));
            WAPInfo.setBSSID(cursor.getString(5));
            entries.add(WAPInfo);
        }
//        Log.d("debug", "All WAPS size: "+entries.size()+"//");
        return entries;
    }

    public ArrayList<NetworkInfo> SearchWAPs(String search_key) {
        ArrayList<NetworkInfo> entries = new ArrayList<>();
        Cursor cursor = db.query(netDb.NET_TABLE_NAME, new String[]{"SSID", "Strength_Level", "TimeStamp", "Frequency", "Link_Speed", "BSSID"},
                "SSID = ?", new String[]{search_key}, null, null, null, null);
        while (cursor.moveToNext()) {
            NetworkInfo WAPInfo = new NetworkInfo();
            WAPInfo.setSSID(cursor.getString(0));
            WAPInfo.setLevel(cursor.getInt(1));
            WAPInfo.setTimestamp(cursor.getString(2));
            WAPInfo.setFrequency(cursor.getInt(3));
            WAPInfo.setLinkspeed(cursor.getInt(4));
            WAPInfo.setBSSID(cursor.getString(5));
            entries.add(WAPInfo);
        }
//        Log.d("debug", "Search WAPs size: "+entries.size()+"//");
        return entries;
    }

    class WirelessRegDb extends SQLiteOpenHelper {
        //        Database Metadata
        private static final String DATABASE_NAME = "WiFiX_DBMS";
        private static final String NET_TABLE_NAME = "NETWORK_INFO";

        //        Query Structures
        private static final String CREATE_TABLE = "CREATE TABLE " + NET_TABLE_NAME + " ( SSID VARCHAR(255), Strength_Level INTEGER," +
                "TimeStamp DATE PRIMARY KEY, Frequency INTEGER, Link_Speed INTEGER, BSSID VARCHAR(255));";

        public WirelessRegDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Log.d("debug", "onCreate: database");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }


    }
}
