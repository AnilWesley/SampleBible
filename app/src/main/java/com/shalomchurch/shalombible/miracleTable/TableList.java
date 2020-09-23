package com.shalomchurch.shalombible.miracleTable;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.shalomchurch.shalombible.R;

import java.util.List;

public class TableList implements ListAdapter {
    Context  context;
    List<String> index,miracles,matthew,mark,luke,john;
    TextView textView,textView2,textView3,textView4,textView5;


    TableList(Context context, List<String> index, List<String> miracles, List<String> matthew,
              List<String> mark, List<String> luke,List<String> john){
        this.context=context;
        this.index=index;
        this.miracles=miracles;
        this.matthew=matthew;
        this.mark=mark;
        this.luke=luke;
        this.john=john;



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
        return index.size();
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
        View listviewitem=layoutInflater.inflate(R.layout.table_view,null,true);
        textView=(TextView)listviewitem.findViewById(R.id.miracle);
        textView2=(TextView)listviewitem.findViewById(R.id.matthew);
        textView3=(TextView)listviewitem.findViewById(R.id.mark);
        textView4=(TextView)listviewitem.findViewById(R.id.luke);
        textView5=(TextView)listviewitem.findViewById(R.id.john);

        textView.setText(index.get(i)+" . "+ miracles.get(i));
        textView2.setText(matthew.get(i));
        textView3.setText(mark.get(i));
        textView4.setText(luke.get(i));
        textView5.setText(john.get(i));

        return  listviewitem;


    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return index.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
