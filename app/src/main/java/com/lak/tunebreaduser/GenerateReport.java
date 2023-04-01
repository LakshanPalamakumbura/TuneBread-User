package com.lak.tunebreaduser;
//package com.example.policectcapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lak.tunebreaduser.Util.Alert;
//import com.example.policectcapp.Util.Alert;
import com.lak.tunebreaduser.Util.NotificationHelper;
//import com.example.policectcapp.Util.NotificationHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;

public class GenerateReport extends AppCompatActivity {
    private RecyclerView mAlertList;
    private DatabaseReference mDatabase;
    private Button btnReport;
    private String data = "\n"+"                            Date                                           Vehicle Number               Vehicle Type                 Mobile     "+"\n";
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("alerts");
        mDatabase.keepSynced(true);
        mAlertList = (RecyclerView)findViewById(R.id.alertRecycleView);
        mAlertList.setHasFixedSize(true);
        mAlertList.setLayoutManager(new LinearLayoutManager(this));
        btnReport = (Button)findViewById(R.id.genReportbtn);
        ImageView imgback = (ImageView)findViewById(R.id.img_back);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Print","\n"+ data);
                createMyPDF();
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActivityCompat.requestPermissions(GenerateReport.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

    }

    @Override
    protected void onStart() {
        Log.d("ViewValue","Called");
        FirebaseRecyclerOptions<Alert> options =
                new FirebaseRecyclerOptions.Builder<Alert>()
                        .setQuery(mDatabase, Alert.class)
                        .build();
        FirebaseRecyclerAdapter<Alert, UserAlert.AlertViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Alert, UserAlert.AlertViewHolder>
                (options) {
            //String data = "\n"+"     Date                                 Vehicle Number                      Vehicle Type                    Mobile     "+"\n";
            @Override
            protected void onBindViewHolder(@NonNull UserAlert.AlertViewHolder holder, int position, @NonNull Alert model) {
                holder.setVehicleNumber(model.getVehicleNumber());
                holder.setMobile(model.getUserMob());
                holder.settime(model.getDate());
                holder.setVehicleType(model.getVehicleType());
                data = data + "\n" + model.getDate()+"                      "+model.getVehicleNumber()+"                  "+model.getVehicleType()+"                "+model.getUserMob();
                setData(data);
            }

            @NonNull
            @Override
            public UserAlert.AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alert_row, parent, false);
                Log.d("ViewValue",view.toString());
                return new UserAlert.AlertViewHolder(view);
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

            }
        };
        firebaseRecyclerAdapter.startListening();
        mAlertList.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }
    public void createMyPDF(){

        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(700,1400,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String myString = getData();
        int x = 10, y=25;
        for (String line:myString.split("\n")){
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y+=myPaint.descent()-myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/myPDFFile.pdf";
        File myFile = new File(myFilePath);
        try {
            myPdfDocument.writeTo(new FileOutputStream(myFile));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        myPdfDocument.close();
        Toast.makeText(GenerateReport.this, "Report Save to Storage Successfully", Toast.LENGTH_SHORT).show();
    }
}
