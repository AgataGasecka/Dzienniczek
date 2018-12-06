package com.example.agata.dzienniczekpacjenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<Pomiar> pomiary;
    private Context context;

    public ListAdapter(List<Pomiar> pomiary, Context context) {
        this.pomiary = pomiary;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pomiary.size();
    }

    @Override
    public Object getItem(int position) {
        return pomiary.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.items, parent, false);
        }

        TextView data = convertView.findViewById(R.id.textViewData);
        TextView godzina = convertView.findViewById(R.id.textViewGodzina);
        TextView wynikPomiaru = convertView.findViewById(R.id.textViewPomiar);

        data.setText(pomiary.get(position).getData());
        godzina.setText(pomiary.get(position).getGodzina());
        wynikPomiaru.setText(pomiary.get(position).getWynik());

        return convertView;
    }
}
