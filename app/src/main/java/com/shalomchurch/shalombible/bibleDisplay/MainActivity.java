package com.shalomchurch.shalombible.bibleDisplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.shalomchurch.shalombible.bookNames.BookNames;
import com.shalomchurch.shalombible.bookmarks.BookmarkList;
import com.shalomchurch.shalombible.R;
import com.shalomchurch.shalombible.SharedPreference2;
import com.shalomchurch.shalombible.SingleLangSelect;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinner,spinner1,spinner2;
    BookNames bookNames=new BookNames(MainActivity.this);
    StringBuilder buf;
    ListView listView;
    String sam;
    String textBook,textChapter;
    android.app.AlertDialog dialog ;
    SharedPreference2 sharedPreference1;

    String[] listviewTitle = new String[]{
            "Add Bookmark", "Share", "Cancel",
    };


    int[] listviewImage = new int[]{
            R.drawable.ic_note_add_black_24dp, R.drawable.ic_share_black_24dp, R.drawable.ic_cancel_black_24dp,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        final String folder=loadState("lag");
        getSupportActionBar().setTitle(folder+ " Bible");

        spinner=findViewById(R.id.spinner1);
        spinner1=findViewById(R.id.spinner2);
        spinner2=findViewById(R.id.spinner3);
        listView=findViewById(R.id.lv);
        sharedPreference1 = new SharedPreference2();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                TextView tv = (TextView) arg1.findViewById(R.id.textView2);
                TextView tv1 = (TextView) arg1.findViewById(R.id.textView);
                final String text=tv.getText().toString().trim();
                final String textVerse=tv1.getText().toString().trim();
                String book= textBook+"--"+textChapter+":"+textVerse;

                sam=text+"-->"+book;

                addDailog();



                return true;
            }
        });

        buf=new StringBuilder();
        InputStream json= null;
        String str;
        BufferedReader in= null;
        try {

            json = getAssets().open(folder+"/bible.json");
            in = new BufferedReader(new InputStreamReader(json, "UTF-8"));

            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                bookNames.BookNameArrays(folder));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setChapterSpinner(position);
                textBook=(bookNames.BookNameArrays(folder))[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setChapterSpinner(0);
                textBook=(bookNames.BookNameArrays(folder))[0];
            }
        });



    }

    public void setChapterSpinner(final int pos){

        final List<String> chapterList=new ArrayList<>();


        try {
            JSONObject jsonObj = new JSONObject(String.valueOf(buf));

            for(int i=1;i<=jsonObj.getJSONArray("Book").getJSONObject(pos).getJSONArray("Chapter").length();i++) {

                chapterList.add(""+i);
            }

            ArrayAdapter<String> chapterdataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    chapterList);
            chapterdataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

            spinner1.setAdapter(chapterdataAdapter);


            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    setVerseSpinner(pos,position);
                    textChapter=chapterList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    setVerseSpinner(pos,0);
                    textChapter=chapterList.get(0);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void setVerseSpinner(int bpos,int cpos){

        List<String> verseList=new ArrayList<>();
        List<String> verseListName=new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(String.valueOf(buf));

            for(int i=1;i<=jsonObj.getJSONArray("Book").getJSONObject(bpos)
                    .getJSONArray("Chapter").getJSONObject(cpos).getJSONArray("Verse").length();i++) {

                verseList.add(""+i);
                verseListName.add(jsonObj.getJSONArray("Book").getJSONObject(bpos)
                        .getJSONArray("Chapter").getJSONObject(cpos).getJSONArray("Verse")
                        .getJSONObject(i-1).getString("Verse"));
            }

            ArrayAdapter<String> versedataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    verseList);
            versedataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

            spinner2.setAdapter(versedataAdapter);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, verseListName);

            //listView.setAdapter(adapter);
            listView.setAdapter(new CustomList(MainActivity.this,verseListName));



            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        listView.setSelection(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    listView.setSelection(0);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
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
                    sharedPreference1.addFavorite(MainActivity.this, sam);
                    dialog.dismiss();
                }
                else if(position==2){
                    dialog.dismiss();
                }
            }
        });
        dialog = new android.app.AlertDialog.Builder(this)
                .setView(view1)
                .create();
        dialog.show();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id==R.id.bookmark){
            Intent intent = new Intent(MainActivity.this, BookmarkList.class);
            // Start next activity
            startActivity(intent);

        }
        if (id==R.id.settings){
            Intent intent = new Intent(MainActivity.this, SingleLangSelect.class);
            // Start next activity
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bookmark, menu);
        return true;
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
