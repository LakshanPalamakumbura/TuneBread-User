package com.lak.tunebreaduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import com.lak.tunebreaduser.Util.Alert;
//import com.example.policectcapp.Util.Alert;
import com.lak.tunebreaduser.Util.AppConfig;
//import com.example.policectcapp.Util.AppConfig;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    Fragment fragment=null;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolBar);
        appConfig = new AppConfig(getBaseContext());
        if(!appConfig.isUserLoggedIn())
        {
            startActivity(new Intent(MainActivity.this,UserLogin.class));
            MainActivity.this.finish();
        }
//        else{
//            if(!"police".equals(appConfig.getUserType()))
//            {
//                startActivity(new Intent(MainActivity.this,OrganizationMain.class));
//                MainActivity.this.finish();
//            }
//
//        }
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id=menuItem.getItemId();

            switch (id)
            {
                case R.id.register:
                    startActivity(new Intent(MainActivity.this,
                            RegisterFirst.class));
                    break;
                case R.id.account:
                    startActivity(new Intent(MainActivity.this,
                            AccountSetting.class));
                    break;
                case R.id.alert:
                    startActivity(new Intent(MainActivity.this,
                            UserAlert.class));
                    break;
                case R.id.report:
                    startActivity(new Intent(MainActivity.this,
                            GenerateReport.class));
                    break;
                case R.id.logout:
                    startActivity(new Intent(MainActivity.this,UserLogin.class));
                    this.finish();
                    appConfig.setUserLoggedOut();
                    break;
                default:
                    return true;
            }
            return true;
        });
        fragment=new MainMapsFragment();
        loadFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }

}