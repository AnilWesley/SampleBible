package com.shalomchurch.shalombible;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;



public class SplashScreen extends AppCompatActivity {
    int a = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //code that displays the content in full screen mode
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(loadState("access").equals("no")){
                    startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashScreen.this,SingleLangSelect.class));
                    finish();
                }


            }
        },a);
    }

    private String loadState(String key)
    {
        String state = null;
        try
        {
            SharedPreferences prefs = getSharedPreferences("com.shalomchurch.shalombible", Context.MODE_PRIVATE);
            state = prefs.getString(key, "");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return state;
    }
}