package com.anthrino.wifix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_FINE_LOCATION = 0;
    private WifiManager wifiManager;
    private List<ScanResult> wireless_apns;
    private WifiReceiver wifiReceiver;
    private networkDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAdapter = new networkDBAdapter(this);
//        requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Wifi was disabled. Connecting..", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }
        wifiReceiver = new WifiReceiver();

        Button refbtn = (Button) findViewById(R.id.refbtn);
        refbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wifiManager.isWifiEnabled()) {
                    Toast.makeText(MainActivity.this, "Wifi was disabled. Connecting..", Toast.LENGTH_SHORT).show();
                    wifiManager.setWifiEnabled(true);
                }
                Log.i("debug", "Scanning");
                wifiManager.startScan();
            }
        });

        Button showallbtn = (Button) findViewById(R.id.showall);
        showallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("debug", "Show all");
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("entries", dbAdapter.DisplayAllWAPs());
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, WAPLister.class);
                startActivity(intent);
            }
        });

        Button showspecbtn = (Button) findViewById(R.id.showspec);
        showspecbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("debug", "WAP History");
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("entries", dbAdapter.SearchWAPs(wifiManager.getConnectionInfo().getSSID()));
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, WAPLister.class);
                startActivity(intent);
            }
        });

        Button insertbtn = (Button) findViewById(R.id.insert);
        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("debug", "Inserting");
                if (dbAdapter.InsertWAP(wifiManager.getConnectionInfo()) > 0)
                    Toast.makeText(getApplicationContext(), "Insertion Successful", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Insertion failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onPause() {
        unregisterReceiver(wifiReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void Refresh_Networks(View view)
//    {
//        Intent intent = new Intent(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//        sendBroadcast(intent);
//        wireless_apns = wifiManager.getScanResults();
//        Log.d("debug", "Refresh_Networks: "+wireless_apns.toString());
////
//        if (findViewById(R.id.network_fragment_container) != null) // check if stock_fragment_container is present in current view
//        {
//            networkFragment firstFragment = new networkFragment();
//            Bundle args = new Bundle();
////                args = getIntent().getExtras();
////                Log.d("debug", "Putting parceable to bundle fragment");
//            firstFragment.setArguments(args);
//            firstFragment.attachList(wireless_apns);
//
////                Log.d("debug", "Fragment setting successful");
//            getFragmentManager().beginTransaction().replace(R.id.stock_fragment_container, firstFragment, "networkfrag").commit();
//            Log.d("debug", "List generation successful");
//        }
//        Toast.makeText(this, "Loaded Successfully", Toast.LENGTH_SHORT).show();
//
//    }

    class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("debug", "in onReceive");
            WifiInfo connInfo = wifiManager.getConnectionInfo();
            TextView ssid = (TextView) findViewById(R.id.ssid);
            TextView bssid = (TextView) findViewById(R.id.bssid);
            TextView freq = (TextView) findViewById(R.id.frequency);
            TextView level = (TextView) findViewById(R.id.level);
            TextView link_speed = (TextView) findViewById(R.id.linkspeed);

            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
//                Toast.makeText(context, "Scan results available", Toast.LENGTH_SHORT).show();
                wireless_apns = wifiManager.getScanResults();
                if (wireless_apns.size() > 0)
                    Toast.makeText(context, "Scan results Successful", Toast.LENGTH_SHORT).show();
                else
                    Log.d("debug", "Refresh_Networks: " + wireless_apns.toString());
            }

            ssid.setText(connInfo.getSSID());
            bssid.setText(" :\t\t\t " + connInfo.getBSSID());
            level.setText(" :\t\t\t " + String.valueOf(connInfo.getRssi()) + " dBm");
            freq.setText(" :\t\t\t " + String.valueOf(connInfo.getFrequency()) + " MHz");
            link_speed.setText(" :\t\t\t " + String.valueOf(connInfo.getLinkSpeed()) + " Mbps");
            Toast.makeText(context, "Loaded Successfully", Toast.LENGTH_SHORT).show();

        }
    }
}
