package com.example.android.quakereport;

import android.content.Context;
import android.nfc.Tag;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import android.widget.Toast;

import java.util.List;

// click listener for recycler adapter
// make class class RecyclerViewHolder extends......
//        you need to tell Gradel to use implementation 'com.android.support:design:27.1.1'


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
        String eqUrl = currentEarthquake.getmUrl();
        //convertView.setTag( eqUrl);

        TextView magnitudeview = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeview.setText(currentEarthquake.getmMagnitude());
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeview.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        // method is below; out side the overwrite method for inflater.
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);




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
            case 1: locationCompass.setText("Near The");
                locationview.setText(locationArray[0]);
            break;
            case 2: locationCompass.setText(locationArray[0]);
                    locationview.setText(locationArray[1]);
            break;
            default:
                Log.v("EarthquakeAdapter","Location slip fail at EartquakeAdapter");
        }

        /*Using the switch case instead
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

    // this class will just return a color that was given in the layout resources but its called above
    private int getMagnitudeColor(String getmMagnitude) {
        // convert the string to double with Double.valueOf()
        double mag =Double.valueOf(getmMagnitude);
        //get a default color just in case for the return int value
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        //round the double number just avoid if statements
        int magFloor = (int) Math.floor(mag);

        switch (magFloor){
            case 0:

            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;

            default:
                color = R.color.magnitude10plus;
                break;


        }
        return ContextCompat.getColor(getContext(), color);
    }
}
