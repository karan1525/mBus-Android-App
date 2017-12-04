package io.github.karan.mbus.controllers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import io.github.karan.mbus.models.Bus;

/**
 * This class implements Comparator.
 */
public class TimeComparator implements Comparator<Bus> {

    /**
     * This method compares two Bus objects by its departure time.
     * @param o1 first bus object
     * @param o2 second bus object
     * @return 1 if first bus's time is greater than second bus
     * @return 0 if first and second bus's time is the same
     * @return -1 if first's bus time is less than second bus
     **/

    public int compare(Bus o1, Bus o2) {
        int time1 = convertTimeTo24Hours(o1.getDeptTime());
        int time2 = convertTimeTo24Hours(o2.getDeptTime());

        if (time1 > time2)
            return 1;
        else if (time1 < time2)
            return -1;
        else
            return 0;
    }

    private int convertTimeTo24Hours(String timeToConvert) {

        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mma");

        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

        String toReturn = "";

        try {
           toReturn = date24Format.format(date12Format.parse(timeToConvert));
        } catch (Exception e) {
            e.printStackTrace();
        }

        toReturn = toReturn.replace(":", "");
        Log.d("Test", toReturn);

        return Integer.parseInt(toReturn);
    }
}
