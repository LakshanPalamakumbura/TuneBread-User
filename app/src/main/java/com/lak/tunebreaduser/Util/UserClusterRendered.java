//package com.example.policectcapp.Util;
package com.lak.tunebreaduser.Util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.lak.tunebreaduser.R;
//import com.example.policectcapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class UserClusterRendered extends DefaultClusterRenderer<RealLocation> {
    public UserClusterRendered(Context context, GoogleMap map, ClusterManager<RealLocation> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull RealLocation item, @NonNull MarkerOptions markerOptions) {
   //     super.onBeforeClusterItemRendered(item, markerOptions);
        final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.greencar);
        markerOptions.icon(markerDescriptor).snippet(item.getTitle());
//        markerOptions.title(item.getTitle());
    }


}
