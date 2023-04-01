package com.lak.tunebreaduser;
//package com.example.policectcapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lak.tunebreaduser.Util.Alert;
//import com.example.policectcapp.Util.Alert;
import com.lak.tunebreaduser.Util.NotificationHelper;
//import com.example.policectcapp.Util.NotificationHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAlert extends AppCompatActivity {
    private RecyclerView mAlertList;
    private DatabaseReference mDatabase;
    NotificationHelper notificationHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_alert);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("alerts");
        mDatabase.keepSynced(true);
        mAlertList = (RecyclerView)findViewById(R.id.alertRecycleView);
        Button backbtn = (Button)findViewById(R.id.back_btn);
        mAlertList.setHasFixedSize(true);
        mAlertList.setLayoutManager(new LinearLayoutManager(this));
        notificationHelper = new NotificationHelper(this);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d("ViewValue","Called");
        FirebaseRecyclerOptions<Alert> options =
                new FirebaseRecyclerOptions.Builder<Alert>()
                        .setQuery(mDatabase, Alert.class)
                        .build();
        FirebaseRecyclerAdapter <Alert, AlertViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Alert, AlertViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull AlertViewHolder holder, int position, @NonNull Alert model) {
               holder.setVehicleNumber(model.getVehicleNumber());
               holder.setMobile(model.getUserMob());
               holder.settime(model.getDate());
               holder.setVehicleType(model.getVehicleType());
            }

            @NonNull
            @Override
            public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alert_row, parent, false);
                Log.d("ViewValue",view.toString());
                return new AlertViewHolder(view);
            }

            @Override
            public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
                super.onAttachedToRecyclerView(recyclerView);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                Log.d("Changed","Data Changed..!");
                notificationHelper.sendHighPriorityNotification("Alert", "Please Check Alert", UserAlert.class);

            }
        };
        firebaseRecyclerAdapter.startListening();
        mAlertList.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    public static class AlertViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setVehicleNumber(String VehicleNumber){
            TextView vehicle_number = (TextView)mView.findViewById(R.id.txtVehicleNumber);
            vehicle_number.setText(VehicleNumber);
        }
        public void setMobile(String mobile){
            TextView mobile_number = (TextView)mView.findViewById(R.id.txtMobile);
            mobile_number.setText(mobile);
        }
        public void settime(String time){
            TextView date_time = (TextView)mView.findViewById(R.id.txtTime);
            date_time.setText(time);
        }
        public void setVehicleType(String vehicleType){
            TextView date_time = (TextView)mView.findViewById(R.id.txtVehicleType);
            date_time.setText(vehicleType);
        }
    }
}