package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;
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
                    R.layout.earquake_list_item,parent,false);
        }

        EarthquakeConstructor currentEarthquake = getItem(position);

        TextView magnitudeview = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeview.setText(currentEarthquake.getmMagnitude());


        TextView locationCompass = (TextView) listItemView.findViewById(R.id.compass);

        TextView locationview = (TextView) listItemView.findViewById(R.id.location);
        //split the location into two were of ends
        //first get the string
        //.split () is used to split into and array every time it sees ("")
        //.split ("" , 3) will limit the split number in the array to first #
        //(?<= * ) wiill keep the * data in the first array.
        String stringLocationcn = currentEarthquake.getmLocation();
        String[] locationArray = stringLocationcn.split("(?<=of)");
        switch (locationArray.length){
            case 0: locationCompass.setText("Near The");
                locationview.setText(locationArray[0]);
            break;
            case 1: locationCompass.setText(locationArray[0]);
                    locationview.setText(locationArray[1]);
            break;
            default:
                Log.d("EarthquakeAdapter","Location slip fail at EartquakeAdapter");
        }

        /*
          if (locationArray.length == 0) {
            locationCompass.setText("Near The");
            locationview.setText(locationArray[0]);
        }
        else if ( locationArray.length == 1) {
            locationCompass.setText(locationArray[0]);
            locationview.setText(locationArray[1]);
        }
            else {
                Log.d("EarthquakeAdapter","Location slip fail at EartquakeAdapter");
        }
         */
        //locationview.setText(locationArray[0]);
        //locationCompass.setText(locationArray[1]);

        TextView dateview = (TextView) listItemView.findViewById(R.id.date);
        dateview.setText(currentEarthquake.getmDate());

        TextView hourview = (TextView) listItemView.findViewById(R.id.time);
        hourview.setText(currentEarthquake.getmTime());


        
        return listItemView;
    }
}
