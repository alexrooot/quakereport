package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class EarthquakeAdapter extends ArrayAdapter<EarthquakeConstructor> {

    public  EarthquakeAdapter (Context context, List<EarthquakeConstructor> earthquakeConstructors){
        super(context, 0, earthquakeConstructors);
    }

    
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    android.R.layout.earthquake_list_item, parent,false);
        }

        EarthquakeConstructor currentEarthquake = getItem(position);

        TextView magnitudeview = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeview.setText(currentEarthquake.getmMagnitude());

        TextView locationview = (TextView) listItemView.findViewById(R.id.location);
        locationview.setText(currentEarthquake.getmLocation());

        TextView dateview = (TextView) listItemView.findViewById(R.id.date);
        dateview.setText(currentEarthquake.getmDate());


        
        return listItemView;
    }
}
