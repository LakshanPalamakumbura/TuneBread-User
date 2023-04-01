//package com.lak.tunebreaduser;
////package com.example.policectcapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.lak.tunebreaduser.Util.AppConfig;
////import com.example.policectcapp.Util.AppConfig;
//
//public class OrganizationMain extends AppCompatActivity {
//    Button btnAccount;
//    Button btnRegister;
//    TextView txtLogout;
//    AppConfig appConfig;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_organization_main);
//        appConfig = new AppConfig(this);
//        txtLogout = (TextView) findViewById(R.id.txtLogout);
//        btnAccount = (Button)findViewById(R.id.btnAccount);
//        btnRegister = (Button)findViewById(R.id.btnRegister);
//        txtLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrganizationMain.this.startActivity(new Intent(OrganizationMain.this, UserLogin.class));
//                ((Activity) OrganizationMain.this).finish();
//                appConfig.setUserLoggedOut();
//            }
//        });
//        btnAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             OrganizationMain.this.startActivity(new Intent(OrganizationMain.this, AccountSetting.class));
//            }
//        });
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrganizationMain.this.startActivity(new Intent(OrganizationMain.this, RegisterFirst.class));
//            }
//        });
//    }
//}