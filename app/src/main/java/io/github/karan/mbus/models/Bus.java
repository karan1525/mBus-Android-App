package io.github.karan.mbus.models;

/**
 * A class representing all the information
 * stored by a Bus object
 */

public class Bus {

    private String number;
    private String from;
    private String to;
    public int price;
    public String deptTime;
    private String arriveTime;
    private int seats;

    /**
     * @param number identifier of the Bus
     * @param from Location of starting point of bus
     * @param to Destination of bus
     * @param dept Departure time of bus
     * @param arrive Arrival time of bus
     * @param seats Number of seats available on bus
     * @param price Ticket price of bus
     **/
    public Bus(String number, String from, String to, String dept, String arrive, int seats, int price){
        this.number = number;
        this.from = from;
        this.to = to;
        deptTime = dept;
        arriveTime = arrive;
        this.seats = seats;
        this.price = price;
    }
    /**
     * @return identifier of a bus object
     **/
    public String getNumber() {
        return number;
    }

    /**
     * @return the identifier, departure time, arrival time,
     *  price, start location, and destination of bus object
     **/
    public String toString() {
        return "\n Bus Number: \t "+number +"\n Depart:\t "+deptTime+"\t \t Arrive:\t "+
                arriveTime+"\n Price:\t $"+price+"\n From:  "+ from+ "\t \t To:  "+ to;
    }
}
