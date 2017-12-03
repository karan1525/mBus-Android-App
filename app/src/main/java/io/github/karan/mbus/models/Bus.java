package io.github.karan.mbus.models;

/**
 * A class representing all the information
 * stored by a Bus object
 */

public class Bus {

    private Long number;
    private String from;
    private String to;
    private int price;
    private String deptTime;
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
    public Bus(Long number, String from, String to, String dept, String arrive, int seats, int price){
        this.number = number;
        this.from = from;
        this.to = to;
        deptTime = dept;
        arriveTime = arrive;
        this.seats = seats;
        this.price = price;
    }

    public Bus() {

    }

    /**
     * @return identifier of a bus object
     **/
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDeptTime() {
        return deptTime;
    }

    public void setDeptTime(String deptTime) {
        this.deptTime = deptTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
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
