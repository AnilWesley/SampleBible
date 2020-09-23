package com.shalomchurch.shalombible;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.shalomchurch.shalombible.bibleDisplay.MainActivity;
import com.shalomchurch.shalombible.bookNames.BookNames;
import com.shalomchurch.shalombible.miracleTable.TableActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ImageView btnEng,btnTel;
    List<String> lagList,selList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask

        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        btnEng=(ImageView) findViewById(R.id.imageView);
        btnTel=(ImageView)findViewById(R.id.imageView2);

        lagList=new BookNames(this).LagList();

        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("Folder",loadState("lag"));
                startActivity(intent);

                         }

        });

        btnTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,TableActivity.class));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bible, menu);
        return true;
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back Again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
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
