package com.lak.tunebreaduser;
//package com.example.policectcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.lak.tunebreaduser.Util.AppConfig;

public class RegisterFirst extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txtFname;
    EditText txtLname;
    EditText txtNIC;
    EditText txtEmail;
    EditText txtMobile;
    String Utype;

    AppConfig appConfig;

    private Spinner txtUtype;
//    private static final String[] paths = {"Organize Employee", "Essential services"};
private static final String[] paths = {"Driver 1", "driver 2", "driver 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);

        Button next1btn = (Button) findViewById(R.id.next1_btn);
        Button cancle = (Button) findViewById(R.id.cancle_btn);
        txtFname = (EditText)findViewById(R.id.txtUserName);
        txtLname = (EditText)findViewById(R.id.txtUserEmail);
        txtNIC = (EditText)findViewById(R.id.txtRegPassword);
        txtEmail = (EditText)findViewById(R.id.txtRegConfPassword);
        txtMobile = (EditText)findViewById(R.id.txtMobile);
        txtUtype = (Spinner)findViewById(R.id.txtUtype);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterFirst.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtUtype.setAdapter(adapter);
        txtUtype.setOnItemSelectedListener(this);
        next1btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              // FirebaseDB firebaseDB = new FirebaseDB(RegisterFirst.this);
             // firebaseDB.checkAndCreateUser(RegisterFirst.this, txtFname.getText().toString(), txtLname.getText().toString(), txtOccupation.getText().toString(), txtNIC.getText().toString(), txtEmail.getText().toString(),txtMobile.getText().toString());
                // Do something in response to button click
//                appConfig.setDriverphone(txtMobile.getText());
//                appConfig.setLoggedUserID(txtMobile.getText().toString());
                Intent intent = new Intent(getBaseContext(), RegisterSecond.class);
                intent.putExtra("fname", txtFname.getText().toString());
                intent.putExtra("lname", txtLname.getText().toString());
                intent.putExtra("Utype", Utype);
                intent.putExtra("nic", txtNIC.getText().toString());
                intent.putExtra("email", txtEmail.getText().toString());
                intent.putExtra("mobile", txtMobile.getText().toString());
                startActivity(intent);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                RegisterFirst.super.finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                Utype = "Employee";
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                Utype = "Essential services";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}