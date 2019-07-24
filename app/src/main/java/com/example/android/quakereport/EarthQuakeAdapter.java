package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {
    public EarthQuakeAdapter(Context context, int resource,List objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView=convertView;
        if(listView==null){
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.eaeth_quake_item, parent, false);

        }
        EarthQuake currentEarthQuake=getItem(position);




        TextView magTextView=listView.findViewById(R.id.mag);
        TextView placeTextView=listView.findViewById(R.id.place);
        TextView place1TextView=listView.findViewById(R.id.place1);
        TextView dateTextView=listView.findViewById(R.id.date);
        TextView timeTextView=listView.findViewById(R.id.time);


        String[] splitString=currentEarthQuake.getName().split("of ",2);

        if(splitString.length==1){


            place1TextView.setText("Near the");
            placeTextView.setText(splitString[0]);
        }
        else
        {
            place1TextView.setText(splitString[0]+"of");
            placeTextView.setText(splitString[1]);
        }

        DecimalFormat formatter = new DecimalFormat("0.00");
        String magString = formatter.format(currentEarthQuake.getMag());

        magTextView.setText(magString);

        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = ContextCompat.getColor(getContext(), getMagnitudeColor(currentEarthQuake.getMag()));

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        Date dateObject=new Date(currentEarthQuake.getTime());
        timeTextView.setText(currentEarthQuake.convertTime(dateObject));
        dateTextView.setText(currentEarthQuake.convertDate(dateObject));




        return listView;

    }

    private int getMagnitudeColor(double mag) {
       if (mag>=0&&mag<=2){
           return R.color.magnitude1;
       }
       else if (mag>2&&mag<=3){
            return R.color.magnitude2;
        }
       else if (mag>3&&mag<=4){
           return R.color.magnitude3;
       }
       else if (mag>4&&mag<=5){
           return R.color.magnitude4;
       }
       else if (mag>5&&mag<=6){
           return R.color.magnitude5;
       }
       else if (mag>6&&mag<7){
           return R.color.magnitude6;
       }
       else if (mag>7&&mag<=8){
           return R.color.magnitude7;
       }
       else if (mag>9&&mag<=10){
           return R.color.magnitude8;
       }
       else if (mag>=0&&mag<=2){
           return R.color.magnitude9;
       }
       else{
           return R.color.magnitude10plus;
       }

    }
}
