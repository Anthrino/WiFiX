package com.anthrino.wifix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class WAPLister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waplister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.network_fragment_container) != null) // check if network_fragment_container is present in current view
        {
            networkFragment firstFragment = new networkFragment();
            Bundle args = new Bundle();
            args = getIntent().getExtras();

//           args.putParcelableArrayList("netlist", getIntent().getExtras().getParcelableArrayList("entries"));
//           Log.d("debug", "Putting parcelable to bundle fragment");
            firstFragment.setArguments(args);
//                Log.d("debug", "Fragment setting successful");
//                frameLayout.setVisibility(View.VISIBLE);
            getFragmentManager().beginTransaction().replace(R.id.network_fragment_container, firstFragment, "netlistfrag").commit();
            Log.d("debug", "List generation successful");
//            if (args.getParcelableArrayList("entries").size() > 0)
//                Toast.makeText(this, "Loaded Successfully", Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
//                TextView textView = (TextView) findViewById(R.id.search_title);
//                textView.setText("Success");
        }
    }

}
