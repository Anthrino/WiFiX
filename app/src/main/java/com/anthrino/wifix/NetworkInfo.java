package com.anthrino.wifix;

import java.io.Serializable;

/**
 * Created by Johns on 06-04-2017.
 */

class NetworkInfo implements Serializable {


    private String SSID;
    private String BSSID;
    private String timestamp;
    private int frequency;
    private int linkspeed;
    private int level;


    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getLinkspeed() {
        return linkspeed;
    }

    public void setLinkspeed(int linkspeed) {
        this.linkspeed = linkspeed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
