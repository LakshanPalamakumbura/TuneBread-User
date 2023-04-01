package com.lak.tunebreaduser;
//package com.example.policectcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterSecond extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txtvehiclNumber;
    DatePicker from;
    DatePicker to;
    String Vtype;
    private Spinner txtVehicleType;
    private static final String[] paths = {"Morning", "Afternoon" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);
        Button nextbtn = (Button) findViewById(R.id.next1_btn);
        Button backbtn = (Button)findViewById(R.id.back_btn);
        txtVehicleType = (Spinner)findViewById(R.id.txtVehicleType) ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterSecond.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtVehicleType.setAdapter(adapter);
        txtVehicleType.setOnItemSelectedListener(this);
        txtvehiclNumber = (EditText)findViewById(R.id.txtVehicleNumber);
        from = (DatePicker) findViewById(R.id.datePickerFrom);
        to = (DatePicker) findViewById(R.id.datePickerTo);
        int day = from.getDayOfMonth();
        int month = from.getMonth() + 1;
        int year = from.getYear();
        String fromDate = day + "/" + month + "/" + year;
        day = to.getDayOfMonth();
        month = to.getMonth() + 1;
        year = to.getYear();
        String toDate = day + "/" + month + "/" + year;

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterLast.class);
                intent.putExtra("fname", getIntent().getStringExtra("fname"));
                intent.putExtra("lname", getIntent().getStringExtra("lname"));
                intent.putExtra("Utype", getIntent().getStringExtra("Utype"));
                intent.putExtra("nic", getIntent().getStringExtra("nic"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                intent.putExtra("vehicleType", Vtype);
                intent.putExtra("vehicleNumber", txtvehiclNumber.getText().toString());
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate",toDate);
                startActivity(intent);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                Vtype = "Morning";
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                Vtype = "Afternoon";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}