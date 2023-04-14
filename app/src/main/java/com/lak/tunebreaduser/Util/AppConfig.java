package com.lak.tunebreaduser.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private SharedPreferences sharedPreferences;
    Context context;
    static SharedPreferences.Editor editor;

    public AppConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("EMPTY_SLOT", Context.MODE_PRIVATE);
    }

    public void setAppIntroFinished() {
        editor = sharedPreferences.edit();
        editor.putBoolean("INTRO_FINISHED", true);
        editor.apply();
    }

    public boolean isAppIntroFinished() {
        return sharedPreferences.getBoolean("INTRO_FINISHED", false);
    }

    public void setUserLoggedIn() {
        editor = sharedPreferences.edit();
        editor.putBoolean("LOGGED_IN", true);
        editor.apply();
    }

    public void setUserLoggedOut() {
        editor = sharedPreferences.edit();
        editor.putBoolean("LOGGED_IN", false);
        editor.apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("LOGGED_IN", false);
    }

    public void setLoggedUserID(String userID) {
        editor = sharedPreferences.edit();
        editor.putString("LOGGED_USER_ID", userID);
        editor.apply();
    }

    // i create below
//    public void setDriverphone(String driverphone) {
//        editor = sharedPreferences.edit();
//        editor.putString("LOGGED_DRIVER_PHONE", driverphone);
//        editor.apply();
//    }
//    public String setDriverphone(String driverphone) {
//        editor = sharedPreferences.edit();
//        editor.putString("LOGGED_DRIVER_PHONE", driverphone);
//        editor.apply();
//        return sharedPreferences.getString("LOGGED_USER_ID", driverphone);
//    }

    public String getLogedUserID() {return sharedPreferences.getString("LOGGED_USER_ID", null); }


    public void setUserAuthorize() {
        editor = sharedPreferences.edit();
        editor.putBoolean("USER_AUTHORIZE", true);
        editor.apply();
    }
    public void setUserUnAuthorize() {
        editor = sharedPreferences.edit();
        editor.putBoolean("USER_AUTHORIZE", false);
        editor.apply();
    }
    public boolean getUserAuthorize(){return sharedPreferences.getBoolean("USER_AUTHORIZE",false);}
    public void setLng(String points) {
        editor = sharedPreferences.edit();
        editor.putString("LNG",  points);
        editor.apply();
    }
    public void setLat(String points) {
        editor = sharedPreferences.edit();
        editor.putString("LAT",  points);
        editor.apply();
    }
    public String getlat() {
        return sharedPreferences.getString("LAT", null);
    }
    public String getlng() {
        return sharedPreferences.getString("LNG", null);
    }
    public void setRealLng(String lng) {
        editor = sharedPreferences.edit();
        editor.putString("RLNG",  lng);
        editor.apply();
    }
    public void setRealLat(String lat) {
        editor = sharedPreferences.edit();
        editor.putString("RLAT",  lat);
        editor.apply();
    }
    public String getRealat() {
        return sharedPreferences.getString("RLAT", null);
    }
    public String getRealng() {
        return sharedPreferences.getString("RLNG", null);
    }
    public void setUserType(String user) {
        editor = sharedPreferences.edit();
        editor.putString("USERTYPE",  user);
        editor.apply();
    }
    public String getUserType() {
        return sharedPreferences.getString("USERTYPE", null);
    }


}

