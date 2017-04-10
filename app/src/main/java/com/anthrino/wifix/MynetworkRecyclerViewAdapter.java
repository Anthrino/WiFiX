package com.anthrino.wifix;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anthrino.wifix.dummy.DummyContent.DummyItem;
import com.anthrino.wifix.networkFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MynetworkRecyclerViewAdapter extends RecyclerView.Adapter<MynetworkRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<NetworkInfo> wireless_apns;
    private final OnListFragmentInteractionListener mListener;

    public MynetworkRecyclerViewAdapter(ArrayList<NetworkInfo> items, OnListFragmentInteractionListener listener) {
        wireless_apns = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_network, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.ssid.setText(wireless_apns.get(position).getSSID());
        holder.timestamp.setText(wireless_apns.get(position).getTimestamp());
        holder.frequency.setText(String.valueOf(wireless_apns.get(position).getFrequency()) + " MHz");
        holder.level.setText(String.valueOf(wireless_apns.get(position).getLevel()) + " dBm");
        holder.linkspeed.setText(String.valueOf(wireless_apns.get(position).getLinkspeed()) + " Mbps");

//        holder.apnView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.apnView);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return wireless_apns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View apnView;
        public final TextView ssid;
        public final TextView timestamp;
        public final TextView frequency;
        public final TextView linkspeed;
        public final TextView level;

        public ViewHolder(View view) {
            super(view);
            apnView = view;
            ssid = (TextView) view.findViewById(R.id.ssid);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            frequency = (TextView) view.findViewById(R.id.frequency);
            level = (TextView) view.findViewById(R.id.level);
            linkspeed = (TextView) view.findViewById(R.id.linkspeed);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + ssid.getText() + "'";
        }
    }
}
