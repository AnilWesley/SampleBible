package com.shalomchurch.shalombible;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shalomchurch.shalombible.bookNames.BookNames;

import java.util.ArrayList;
import java.util.List;

public class SingleLangSelect extends AppCompatActivity {
    ListView myList;
    Button getChoice;
    ArrayList<String> selectedItemsList;
    List<String> lagList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_lang_select);
        myList = (ListView)findViewById(R.id.list);
        getChoice = (Button)findViewById(R.id.getchoice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, getResources().getStringArray(R.array.languages));
        myList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myList.setAdapter(adapter);
        lagList=new BookNames(this).LagList();
        ClearSelections();
        saveState("access","no");
        getChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemsList = new ArrayList<String>();

                int cntChoice = myList.getCount();

                SparseBooleanArray sparseBooleanArray = myList.getCheckedItemPositions();
                for(int i = 0; i < cntChoice; i++){
                    if(sparseBooleanArray.get(i)) {

                        selectedItemsList.add(myList.getItemAtPosition(i).toString());
                        saveState(lagList.get(i),"1");
                        saveState("lag",lagList.get(i));
                        saveState(myList.getItemAtPosition(i).toString(),"1");
                    }

                }

                if(selectedItemsList.size()==0){
                    Toast.makeText(SingleLangSelect.this, "Please select atleast", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(SingleLangSelect.this,HomeActivity.class));
                    finish();
                }
            }
        });

    }


    private void saveState(String key, String Value)
    {
        try
        {
            SharedPreferences prefs = getSharedPreferences("com.shalomchurch.shalombible", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(key, Value);
            edit.apply();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
    private void ClearSelections() {

        for(int i=0;i<lagList.size();i++){

            if(loadState(lagList.get(i)).equals("1")){
                this.myList.setItemChecked(i,true);
            }

        }

    }
}
