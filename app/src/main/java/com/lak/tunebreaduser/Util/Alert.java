//package com.example.policectcapp.Util;
package com.lak.tunebreaduser.Util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.Random;

public class Alert {
    private String AlertID;
    private String Date;
    private String UserMob;
    private String VehicleNumber;
    private String VehicleType;


    public Alert() {
    }

    public Alert(String alertID, String date, String userMob, String vehicleNumber, String vehicleType) {
        AlertID = alertID;
        Date = date;
        UserMob = userMob;
        VehicleNumber = vehicleNumber;
        VehicleType = vehicleType;
    }

    public String getAlertID() {
        return AlertID;
    }

    public void setAlertID(String alertID) {
        AlertID = alertID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUserMob() {
        return UserMob;
    }

    public void setUserMob(String userMob) {
        UserMob = userMob;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }
}
