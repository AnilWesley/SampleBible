package com.shalomchurch.shalombible.bibleDisplay;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.shalomchurch.shalombible.R;

import java.util.List;

public class CustomList implements ListAdapter {
    Context  context;
    List<String> list;
    TextView textView;
    TextView textView2;


    CustomList(Context context, List<String> list){
        this.context=context;
        this.list=list;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listviewitem=layoutInflater.inflate(R.layout.card_view,null,true);
        textView=(TextView)listviewitem.findViewById(R.id.textView);
        textView2=(TextView)listviewitem.findViewById(R.id.textView2);
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "fonts/gautami.ttf");
        textView2 .setTypeface(font);

        textView.setText(""+(i+1));

        textView2.setText(list.get(i));

        return  listviewitem;


    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
