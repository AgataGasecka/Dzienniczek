package com.example.agata.dzienniczekpacjenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListDrugAdapter extends BaseAdapter {

    private List<Drug> drugs;
    private Context context;

    public ListDrugAdapter(List<Drug> drugs, Context context) {
        this.drugs = drugs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return drugs.size();
    }

    @Override
    public Object getItem(int position) {
        return drugs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.drugs, parent, false);
        }

        TextView data = convertView.findViewById(R.id.textViewDataDrugs);
        TextView godzina = convertView.findViewById(R.id.textViewGodzinaDrugs);
        TextView nazwa = convertView.findViewById(R.id.textViewNazwaDrugs);
        TextView wynikPomiaru = convertView.findViewById(R.id.textViewPomiarDrugs);

        data.setText(drugs.get(position).getData());
        godzina.setText(drugs.get(position).getGodzina());
        nazwa.setText(drugs.get(position).getNazwa());
        wynikPomiaru.setText(drugs.get(position).getWynik());

        return convertView;
    }

}
