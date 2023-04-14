package com.lak.tunebreaduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.lak.tunebreaduser.Util.AppConfig;
//import com.example.policectcapp.Util.AppConfig;
import com.lak.tunebreaduser.Util.RealLocation;
//import com.example.policectcapp.Util.RealLocation;
import com.lak.tunebreaduser.Util.Validator;
//import com.example.policectcapp.Util.Validator;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLogin extends AppCompatActivity {
    ImageView imgViewBgLogin;
    ImageView imgLogo;
    LinearLayout layoutMiddle;
    LinearLayout layoutTop;
    LinearLayout viewCenterBottom;
    Animation bgAnim, centerLayoutAnim, fromBottom;
    Display display;
    RelativeLayout viewCenter;
    public DatabaseReference mDatabase;
    RelativeLayout viewBottom;

    EditText txtUserName;
    EditText txtPassword;
    TextView txtForgotPassword;
    Button btnSignIn;
    TextView txtSignUp;

    Vibrator vibrate;
    Animation shakeEditText;
    AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

      //  getSupportActionBar().hide();
        appConfig = new AppConfig(getBaseContext());
        centerLayoutAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_enter);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter);

        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtSignUp = findViewById(R.id.txtSignUp);


        imgViewBgLogin = findViewById(R.id.imgViewBgLogin);
        imgLogo = findViewById(R.id.imgLogo);
        display = getWindowManager().getDefaultDisplay();
        layoutMiddle = findViewById(R.id.layoutMiddle);
        layoutTop = findViewById(R.id.layoutTop);
        viewCenter = findViewById(R.id.viewCenter);
        viewBottom = findViewById(R.id.viewBottom);
        viewCenterBottom = findViewById(R.id.viewCenterBottom);

        bgAnim = AnimationUtils.loadAnimation(this, R.anim.bg_welcome_anim);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        imgViewBgLogin.animate().translationY(-display.getHeight()).setDuration(800).setStartDelay(1500);
        imgLogo.animate().alpha(0).setDuration(800).setStartDelay(1600);
        layoutMiddle.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(1600);
        layoutTop.startAnimation(bgAnim);

        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewCenter.startAnimation(centerLayoutAnim);
                btnSignIn.startAnimation(fromBottom);
                viewCenterBottom.startAnimation(fromBottom);
                viewCenter.setVisibility(View.VISIBLE);
                viewBottom.setVisibility(View.VISIBLE);
            }
        }, 1800);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtUserName.getText().toString().length()<4) {
                    showAlertDialog("Please enter a valid UserName").show();
                    initEditTextErrorAnimation(txtUserName);
                    return;
                }
                if (txtPassword.getText().toString().length() < 6) {
                    showAlertDialog("Please enter a valid Password").show();
                    initEditTextErrorAnimation(txtPassword);
                    return;
                }
                mDatabase.child("admin/").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            Log.d("DBU", dataSnapshot.getValue().toString());
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Log.d("DBData",""+ds.child("username"));
                                if(txtUserName.getText().toString().equals(ds.child("username").getValue()) &&txtPassword.getText().toString().equals(ds.child("password").getValue())){
//                                    if("police".equals(ds.child("admintype").getValue())) {
//                                        UserLogin.this.startActivity(new Intent(UserLogin.this, MainActivity.class));
//                                        ((Activity) UserLogin.this).finish();
//                                        appConfig.setUserLoggedIn();
//                                        appConfig.setUserType("police");
//                                        return;
//                                    }
//                                    else
//                                    {
//                                        UserLogin.this.startActivity(new Intent(UserLogin.this, OrganizationMain.class));
                                    UserLogin.this.startActivity(new Intent(UserLogin.this, MainActivity.class));
                                        ((Activity) UserLogin.this).finish();

                                        appConfig.setUserLoggedIn();

                                        //chek again
                                        appConfig.setLoggedUserID(txtUserName.getText().toString());
                                        appConfig.setUserType("org");
                                        return;
//                                    }
                                }
                            }
                                showAlertDialog("Please enter a valid Credentials").show();
                                initEditTextErrorAnimation(txtUserName);
                                return;

                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        });
        //sign up text code
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                UserLogin.this.startActivity(new Intent(UserLogin.this, UserRegister.class));
                ((Activity) UserLogin.this).finish();
            }
        });
    }
    private void initEditTextErrorAnimation(EditText editText) {
        shakeEditText = AnimationUtils.loadAnimation(this, R.anim.anim_shake_edit_text);
        vibrate.vibrate(20);
        editText.startAnimation(shakeEditText);
    }

    private Flashbar showAlertDialog(String message) {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .duration(1000)
                .message(message)
                .messageColor(ContextCompat.getColor(this, R.color.white))
                .backgroundColor(ContextCompat.getColor(this, R.color.errorMessageBackgroundColor))
                .showIcon()
                .iconColorFilterRes(R.color.errorMessageIconColor)
                .icon(R.drawable.ic_cross)
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(200L)
                        .slideFromLeft()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(600)
                        .slideFromLeft()
                        .accelerate())
                .build();
    }
}