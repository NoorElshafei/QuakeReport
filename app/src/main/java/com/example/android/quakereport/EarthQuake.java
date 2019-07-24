package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EarthQuake {
    private String name,URL;
    private double mag;
    private long time;


    public EarthQuake(String name, double mag, long time,String URL) {
        this.name = name;
        this.mag = mag;
        this.time=time;
        this.URL=URL;
    }

    public EarthQuake() {

    }

    public String getName() {
        return name;
    }

    public double getMag() {
        return mag;
    }

    public long getTime() {
        return time;
    }

    public String getURL() {
        return URL;
    }

    public String convertDate(Date date) {

        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String dateToDisplay=dateFormat.format(date);
       return dateToDisplay;
    }
    public String convertTime(Date time) {

        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
        String dateToDisplay=timeFormat.format(time);
        return dateToDisplay;
    }
}
