package io.github.karan.mbus.models;

/**
 * A class representing all the user
 * needs to save
 */

public class User {
    private long userId;
    private String name;
    private String gender;
    private String busBooked;
    private String tripsTaken;

    public User(long userId, String name, String gender, String busBooked, String tripsTaken) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.busBooked = busBooked;
        this.tripsTaken = tripsTaken;
    }

    public User() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBusBooked() {
        return busBooked;
    }

    public void setBusBooked(String busBooked) {
        this.busBooked = busBooked;
    }

    public String getTripsTaken() {
        return tripsTaken;
    }

    public void setTripsTaken(String tripsTaken) {
        this.tripsTaken = tripsTaken;
    }
}
