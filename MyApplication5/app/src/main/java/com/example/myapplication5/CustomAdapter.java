package com.example.myapplication5;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> list;

    CustomAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater =
                    LayoutInflater.from(context);

            view = inflater.inflate(R.layout.custom_item, parent, false);
        }

        TextView txt = view.findViewById(R.id.txtItem);
        txt.setText(list.get(i));

        return view;
    }
}