package com.lak.tunebreaduser.Util;

import com.google.maps.android.clustering.ClusterItem;

public class Location {

    public String UserMob;
    public String longitude;
    public String latitude;

    Location(){

    }

    public Location(String userMob, String longitude, String latitude) {
        UserMob = userMob;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
