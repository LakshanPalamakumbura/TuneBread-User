//package com.example.policectcapp.Util;
package com.lak.tunebreaduser.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class RealLocation implements ClusterItem {
    private  LatLng position;
    private  String title;

    public RealLocation() {
    }
    public RealLocation(LatLng position, String title) {
        this.position = position;
        this.title = title;
    }
    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }
}
