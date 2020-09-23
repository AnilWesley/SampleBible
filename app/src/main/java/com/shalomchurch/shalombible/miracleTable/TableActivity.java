package com.shalomchurch.shalombible.miracleTable;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.shalomchurch.shalombible.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    StringBuilder buf;
    List<String> miracles,matthew,mark,luke,john,index;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Miracles of Jesus");


        buf=new StringBuilder();
        InputStream json= null;
        String str;
        BufferedReader in= null;
        try {

            json = getAssets().open("miracles.json");
            in = new BufferedReader(new InputStreamReader(json, "UTF-8"));

            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            JSONArray jsonObj = new JSONArray(String.valueOf(buf));
            index=new ArrayList<>();
            miracles=new ArrayList<>();
            matthew=new ArrayList<>();
            mark=new ArrayList<>();
            luke=new ArrayList<>();
            john =new ArrayList<>();
            for(int i=0;i<jsonObj.length();i++){
                index.add(""+jsonObj.getJSONArray(i).get(0));
                miracles.add(""+jsonObj.getJSONArray(i).get(1));
                matthew.add(""+jsonObj.getJSONArray(i).get(2));
                mark.add(""+jsonObj.getJSONArray(i).get(3));
                luke.add(""+jsonObj.getJSONArray(i).get(4));
                john.add(""+jsonObj.getJSONArray(i).get(5));
            }

            ListView listView=findViewById(R.id.miraclelist);
            listView.setAdapter(new TableList(TableActivity.this,index,miracles,matthew,mark,luke,john));


        } catch (JSONException e) {
            e.printStackTrace();
        }






        mAdView = new AdView(TableActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_home_footer));

        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdLeftApplication() {
            }


            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bible, menu);
        return true;
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
