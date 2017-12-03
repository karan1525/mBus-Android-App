package io.github.karan.mbus.controllers;

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
        int time1 = Integer.parseInt(o1.getDeptTime());
        int time2 = Integer.parseInt(o2.getDeptTime());

        if (time1 > time2)
            return 1;
        else if (time1 < time2)
            return -1;
        else
            return 0;
    }
}
