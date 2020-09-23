package com.shalomchurch.shalombible.bookmarks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.shalomchurch.shalombible.R;
import com.shalomchurch.shalombible.SharedPreference2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookmarkList extends AppCompatActivity {
    String sam;
    Activity context = this;

    ListView favoriteList;
    SharedPreference2 sharedPreference;
    List<String> favorites = new ArrayList<>();
    String[] listviewTitle = new String[]{
            "Remove Bookmark", "Share", "Cancel",
    };


    int[] listviewImage = new int[]{
            R.drawable.ic_note_add_black_24dp, R.drawable.ic_share_black_24dp, R.drawable.ic_cancel_black_24dp,
    };
    AlertDialog dialog;
    int pos;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Bookmarks");
        sharedPreference = new SharedPreference2();

        favorites = sharedPreference.getFavorites(context);
        favoriteList = (ListView) findViewById(R.id.lvbook);

        if (favorites == null) {
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {

            if (favorites.size() == 0) {
                showAlert(
                        getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }


            if (favorites != null) {

                favoriteList.setAdapter(new BookmarkAdapter(this, favorites));

                favoriteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position1, long id) {
                        pos = parent.getPositionForView(view);
                        TextView tv = (TextView) view.findViewById(R.id.textView2);
                        sam = tv.getText().toString().trim();
                        addDailog();
                        return false;
                    }
                });


            }
        }



        mAdView = new AdView(BookmarkList.this);
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

    public void showAlert(String title, String message) {
        if (context != null && !context.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }



    protected void addDailog() {


        final View view1 = LayoutInflater.from(this).inflate(R.layout.alerdialog_layout, null);
        final ListView listView = view1.findViewById(R.id.list_view);
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 3; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title"};
        int[] to = {R.id.iv1, R.id.tv1};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.item_dialog, from, to);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==1){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, sam);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }else if (position==0){


                    sharedPreference.removeFavorite(BookmarkList.this, favorites.get(pos));
                    Intent intent = getIntent();
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    Toast.makeText(context, "Removed Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else if(position==2){
                    dialog.dismiss();
                }
            }
        });
        dialog = new AlertDialog.Builder(this)
                .setView(view1)
                .create();
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bible, menu);
        return true;
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
}
