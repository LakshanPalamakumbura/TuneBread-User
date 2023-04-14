package com.lak.tunebreaduser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.lak.tunebreaduser.Util.FirebaseDB;
import com.lak.tunebreaduser.Util.AppConfig;
import com.lak.tunebreaduser.Util.RealLocation;
import com.lak.tunebreaduser.Util.Validator;


public class UserRegister extends AppCompatActivity {
    EditText txtFname;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtConfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Button next1btn = (Button) findViewById(R.id.next1_btn);
        Button cancle = (Button) findViewById(R.id.cancle_btn);
        txtFname = (EditText)findViewById(R.id.txtUserName);
        txtEmail = (EditText)findViewById(R.id.txtUserEmail);
        txtPassword = (EditText)findViewById(R.id.txtRegPassword);
        txtConfPassword = (EditText)findViewById(R.id.txtRegConfPassword);

        next1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDB firebaseDB = new FirebaseDB(UserRegister.this);
                firebaseDB.registerNewUser(UserRegister.this,
                        getIntent().getStringExtra(txtFname.getText().toString()),
//                        getIntent().getStringExtra("lname"),
                        getIntent().getStringExtra(txtEmail.getText().toString()),
                        getIntent().getStringExtra(txtPassword.getText().toString()),
                        getIntent().getStringExtra(txtConfPassword.getText().toString()));
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                UserRegister.this.startActivity(new Intent(UserRegister.this, UserLogin.class));
                ((Activity) UserRegister.this).finish();
            }
        });
    }



}
