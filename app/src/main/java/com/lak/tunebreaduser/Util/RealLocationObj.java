package com.lak.tunebreaduser.Util;

import com.google.android.gms.maps.model.LatLng;

public class RealLocationObj {
    public LatLng position;
    public String title;

    public RealLocationObj() {
    }

    public RealLocationObj(LatLng position, String title) {
        this.position = position;
        this.title = title;
    }
}
