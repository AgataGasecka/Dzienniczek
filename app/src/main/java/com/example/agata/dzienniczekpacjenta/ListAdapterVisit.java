package com.example.agata.dzienniczekpacjenta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ListAdapterVisit extends BaseAdapter {
    private List <Visit> visits;
    private Context context;

    public ListAdapterVisit(List<Visit> visits, Context context) {
        this.visits = visits;
        this.context = context;
    }


    @Override
    public int getCount() {
        return visits.size();
    }

    @Override
    public Object getItem(int position) {
        return visits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.visits, parent, false);
        }

        TextView date = convertView.findViewById(R.id.visitDate);
        TextView hour = convertView.findViewById(R.id.visitHour);

        final String dateOfVisit = visits.get(position).getDate();
        final String hourOfVisit = visits.get(position).getHour();
        final String doctor = visits.get(position).getDoctor();
        final String place = visits.get(position).getPlace();
        final int id = visits.get(position).getID();
        date.setText(dateOfVisit);
        hour.setText(hourOfVisit);


        Button visitDetails = convertView.findViewById(R.id.button12);
        visitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,VisitDetails.class);
                intent.putExtra("selectedDate",dateOfVisit);
                intent.putExtra( "hourOfVisit", hourOfVisit);
                intent.putExtra("doctor", doctor);
                intent.putExtra("place", place);
                intent.putExtra("visitId", id);
                intent.putExtra("info", "brak");
                intent.putExtra("editMode", "yes");

                context.startActivity(intent);
            }
        });
        return convertView;
    }


}
