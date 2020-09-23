package com.shalomchurch.shalombible.bookmarks;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.shalomchurch.shalombible.R;
import com.shalomchurch.shalombible.SharedPreference2;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter implements ListAdapter {
   Context context;
    TextView textView;
    TextView textView2;
    SharedPreference2 sharedPreference;
    List<String> favorites = new ArrayList<>();
    public BookmarkAdapter(BookmarkList context, List<String> my_arrayContent) {
        this.context=context;
        this.favorites=my_arrayContent;
        sharedPreference=new SharedPreference2();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public View getView( final int i, final View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listviewitem=layoutInflater.inflate(R.layout.card_view,null,true);
        favorites = sharedPreference.getFavorites(context);

        textView=(TextView)listviewitem.findViewById(R.id.textView);
        textView2=(TextView)listviewitem.findViewById(R.id.textView2);
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "fonts/gautami.ttf");
        textView2 .setTypeface(font);

        textView.setText(""+(i+1));

        textView2.setText(favorites.get(i));


        return  listviewitem;


    }


}
