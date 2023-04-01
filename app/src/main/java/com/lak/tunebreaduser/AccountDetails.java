package com.lak.tunebreaduser;
//package com.example.policectcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lak.tunebreaduser.Util.AppConfig;
//import com.example.policectcapp.Util.AppConfig;
import com.lak.tunebreaduser.Util.User;
//import com.example.policectcapp.Util.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountDetails extends AppCompatActivity {
    TextView txtName;
TextView txtEmail;
TextView txtNIC;
TextView txtMobile;
TextView txtVehicleType;
TextView txtVehicleNumber;
TextView txtFromDate;
TextView txtToDate;
Button btnOK;
public DatabaseReference mDatabase;
AppConfig appConfig;
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        appConfig = new AppConfig(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtName = (TextView)findViewById(R.id.txtChangeName);
        txtEmail = (TextView)findViewById(R.id.txtChangeEmail);
        txtNIC = (TextView)findViewById(R.id.txtRegPassword);
        txtMobile = (TextView)findViewById(R.id.txtMobileNumber);
        txtVehicleType = (TextView)findViewById(R.id.txtVehicleType);
        txtVehicleNumber = (TextView)findViewById(R.id.txtVehicleNumber);
        txtFromDate = (TextView)findViewById(R.id.txtfromDate);
        txtToDate = (TextView)findViewById(R.id.txtToDate);
        btnOK = (Button)findViewById(R.id.btnOk);
        mDatabase.child("users").child(getIntent().getStringExtra("userCheck")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        Log.d("DBU", dataSnapshot.getValue().toString());
                        user = dataSnapshot.getValue(User.class);
                        txtName.setText(user.fname + " " + user.lname);
                        txtEmail.setText(""+user.email);
                        txtMobile.setText(getIntent().getStringExtra("userCheck"));
                        txtVehicleType.setText(""+user.vehicleType);
                        txtVehicleNumber.setText(""+user.vehicleNumber);
                        txtFromDate.setText(""+user.fromDate);
                        txtToDate.setText(""+user.toDate);
                        txtNIC.setText(user.NIC);
                    } else Log.i("DB", "Details Doesn't Available");
                }catch (Exception ex){
                    Log.i("DB", "Details Doesn't Available");
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AccountDetails.this.finish();
            }
        });
    }
}