package com.lak.tunebreaduser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
//import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.location.Location;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.lak.tunebreaduser.Util.AppConfig;
//import com.example.policectcapp.Util.AppConfig;
import com.lak.tunebreaduser.Util.RealLocation;
import com.lak.tunebreaduser.Util.Location;
//import com.example.policectcapp.Util.RealLocation;
import com.lak.tunebreaduser.Util.TaskLoadedCallback;
import com.lak.tunebreaduser.Util.UserClusterRendered;
//import com.example.policectcapp.Util.UserClusterRendered;
import com.lak.tunebreaduser.Util.GeofenceHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.List;

public class MainMapsFragment extends Fragment implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    private static final String TAG = "MainMapsFragment";
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;
    LocationRequest locationRequest;
    private ClusterManager<RealLocation> clusterManager;
    private float GEOFENCE_RADIUS = 200;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";
//    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
//    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;

    public DatabaseReference mDatabase;
    AppConfig appConfig;
    Marker userLocationMarker;
    SearchView searchView;
    Location locationObj;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(),
//                        ScanQRCode.class));
//            }
//        });
        appConfig = new AppConfig(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        startLocationUpdates();

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
    //    private void zoomToUserLocation() {
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
//        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                try {
//                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
//                    //            mMap.addMarker(new MarkerOptions().position(latLng));
//                } catch (Exception ex) {
//                    Log.d("Error", "" + ex);
//                }
//            }
//        });
//    }
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            try {
                setUpClusterer();
                setUserLocationMarker(locationResult.getLastLocation());
//                addCircle(locationResult.getLastLocation(), GEOFENCE_RADIUS);
                appConfig.setRealLng(String.valueOf(locationResult.getLastLocation().getLongitude()));
                appConfig.setRealLat(String.valueOf(locationResult.getLastLocation().getLatitude()));
                Log.d(TAG, "onLocationResult: " + locationResult.getLastLocation());
                if (mMap != null) {
                    setUserLocationMarker(locationResult.getLastLocation());
                }
            }catch (Exception ex){
                Log.d("Error", "Error : " + ex);
            }
        }

    };

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }
    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            // you need to request permissions...
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

//        // Add a marker in Sydney and move the camera
//        LatLng eiffel = new LatLng(48.8589, 2.29365);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel, 16));

        enableUserLocation();

//        mMap.setOnMapLongClickListener(this);

//        String userID = appConfig.setLoggedUserID();
//        if (userID != null) {
        mDatabase.child("location").child("0774603387").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Log.d("DB", dataSnapshot.getValue().toString());
                    locationObj = dataSnapshot.getValue(Location.class);
                    LatLng latLng = new LatLng(Double.parseDouble(locationObj.latitude),Double.parseDouble(locationObj.longitude));
                   handleMapLongClick(latLng);
                } else {
                    Log.i("DB", "Location Doesn't Available");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        } else {
//            Log.e("DB", "User ID is null");
//        }
        clusterManager = new ClusterManager<RealLocation>(getContext(), mMap);

    }




    private void handleMapLongClick(LatLng latLng) {
        Log.d("myLog", "Called...!");
        mMap.clear();
        addMarker(latLng);
        addCircle(latLng, GEOFENCE_RADIUS);
//            addGeofence(latLng, GEOFENCE_RADIUS);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

    }

    private void addGeofence(LatLng latLng, float radius) {
        Log.d("myLog", "Adding......!"+GEOFENCE_ID);
        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"Geo Fence Added..!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String errorMessage = geofenceHelper.getErrorString(e);
                Log.d(TAG,errorMessage);
            }
        });
    }


    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.customer));
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0,0));
        circleOptions.fillColor(Color.argb(64, 255, 0,0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }

    private void setUpClusterer() {
        // Position the map.
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)


        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        final UserClusterRendered clusterRenderer = new UserClusterRendered(getContext(), mMap, clusterManager);
        clusterManager.setAnimation(true);
        clusterManager.setRenderer(clusterRenderer);
        // Add cluster items (markers) to the cluster manager.
        addItems(); //want this for show
    }
    private void addItems() {
        // Set some lat/lng coordinates to start with.
        mDatabase.child("realLocation/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Log.d("DBU", dataSnapshot.getValue().toString());
                    clusterManager.clearItems();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //realLocationObj = child.getValue(RealLocationObj.class);
                        LatLng position = new LatLng(ds.child("position/latitude").getValue(Double.class), ds.child("position/longitude").getValue(Double.class));
                        RealLocation offsetItem = new RealLocation(position, ds.child("title").getValue(String.class));
                        clusterManager.addItem(offsetItem);
                    }

                } else Log.i("DB", "Details Doesn't Available");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setUserLocationMarker(android.location.Location lastLocation) {
        LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        if(userLocationMarker == null) {
            //Create a new marker
            Log.d("Called","InsidesetUserLocationMarker"+userLocationMarker);
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(latLng);
//            markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.policecar));
//            markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.customer));
//            markerOptions1.rotation(lastLocation.getBearing());
//            markerOptions1.anchor((float) 0.5, (float) 0.5);
//            userLocationMarker = mMap.addMarker(markerOptions1);
            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        } else {
            //use the previously created marker
            userLocationMarker.setPosition(latLng);
            userLocationMarker.setRotation(lastLocation.getBearing());
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onTaskDone(Object... values) {

    }
//    private void addCircle(android.location.Location lastLocation, float radius) {
//        LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
//        CircleOptions circleOptions = new CircleOptions();
//        circleOptions.center(latLng);
//        circleOptions.radius(radius);
//        circleOptions.strokeColor(Color.argb(255, 255, 0,0));
//        circleOptions.fillColor(Color.argb(64, 255, 0,0));
//        circleOptions.strokeWidth(4);
//        mMap.addCircle(circleOptions);
//    }
}


