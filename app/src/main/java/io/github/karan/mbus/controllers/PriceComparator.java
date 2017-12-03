package io.github.karan.mbus.controllers;

import java.util.Comparator;

import io.github.karan.mbus.models.Bus;

/**
 * This class implements Comparator.
 */
public class PriceComparator implements Comparator<Bus> {
    /**
     * This method compares two Bus objects by its price.
     * @param o1 first bus object
     * @param o2 second bus object
     * @return 1 if first bus's ticket price is greater than second bus
     * @return 0 if first and second bus's ticket price is the same
     * @return -1 if first's ticket price is less than second bus
     **/
    public int compare(Bus o1, Bus o2) {

        if (o1.price > o2.price)
            return 1;
        else if (o1.price < o2.price)
            return -1;
        else
            return 0;
    }
}
