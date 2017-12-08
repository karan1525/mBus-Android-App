package io.github.karan.mbus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.karan.mbus.models.Bus;
import io.github.karan.mbus.models.User;

/**
 * A class to do CRUD operations
 * on the database
 */

@SuppressWarnings("unused")
public class BusDBOperations {

    private static final String LOGTAG = "BUS_MANAGEMENT_SYSTEM";

    @NonNull
    private final SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;


    private static final String[] allBusColumns = {
            BusDBHandler.COLUMN_ID,
            BusDBHandler.COLUMN_FROM,
            BusDBHandler.COLUMN_TO,
            BusDBHandler.COLUMN_DEPARTURE,
            BusDBHandler.COLUMN_ARRIVAL,
            BusDBHandler.COLUMN_SEATS,
            BusDBHandler.COLUMN_PRICE
    };

    private static final String[] allUserColumns = {
            BusDBHandler.COLUMN_USER_ID,
            BusDBHandler.COLUMN_NAME,
            BusDBHandler.COLUMN_GENDER,
            BusDBHandler.COLUMN_BOOKED,
            BusDBHandler.COLUMN_TRIPS
    };

    public BusDBOperations(Context context) {
        dbhandler = new BusDBHandler(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @NonNull
    //CREATE DATA
    public User addUser(@NonNull User userToAdd) {
        ContentValues values = new ContentValues();
        values.put(BusDBHandler.COLUMN_NAME, userToAdd.getName());
        values.put(BusDBHandler.COLUMN_GENDER, userToAdd.getGender());
        values.put(BusDBHandler.COLUMN_BOOKED, userToAdd.getBusBooked());
        values.put(BusDBHandler.COLUMN_TRIPS, userToAdd.getTripsTaken());
        long insertId = database.insert(BusDBHandler.TABLE_USERS, null, values);
        userToAdd.setUserId(insertId);
        return userToAdd;
    }

    // Getting single User
    @NonNull
    //READ DATA
    public User getUser(long id) {

        Cursor cursor = database.query(BusDBHandler.TABLE_USERS, allUserColumns,
                BusDBHandler.COLUMN_USER_ID + "=?",new String[]{String.valueOf(id)},
                null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //public User(long userId, String name, String gender, String busBooked, String tripsTaken)

        assert cursor != null;
        User u = new User(Long.parseLong(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3),
                cursor.getString(4));

        cursor.close();

        return u;
    }

    @NonNull
    public List<User> getAllUsers() {

        Cursor cursor = database.query(BusDBHandler.TABLE_USERS, allUserColumns,
                null,null,null, null, null);

        List<User> users = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                User user = new User();
                user.setUserId(cursor.getLong(cursor.getColumnIndex(
                        BusDBHandler.COLUMN_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(
                        BusDBHandler.COLUMN_NAME)));
                user.setGender(cursor.getString(cursor.getColumnIndex(
                        BusDBHandler.COLUMN_GENDER)));
                user.setBusBooked(cursor.getString(cursor.getColumnIndex(
                        BusDBHandler.COLUMN_BOOKED)));
                user.setTripsTaken(cursor.getString(cursor.getColumnIndex(
                        BusDBHandler.COLUMN_TRIPS)));
                users.add(user);
            }
        }

        cursor.close();
        // return All Employees
        return users;
    }

    // Updating User
    public int updateUser(@NonNull User user) {

        ContentValues values = new ContentValues();
        values.put(BusDBHandler.COLUMN_NAME, user.getName());
        values.put(BusDBHandler.COLUMN_GENDER, user.getGender());
        values.put(BusDBHandler.COLUMN_BOOKED, user.getBusBooked());
        values.put(BusDBHandler.COLUMN_TRIPS, user.getTripsTaken());

        // updating row
        return database.update(BusDBHandler.TABLE_USERS, values,
                BusDBHandler.COLUMN_USER_ID
                        + "=?",new String[] { String.valueOf(user.getUserId())});
    }

    //Removing user
    public void removeUser(@NonNull User user) {

        database.delete(BusDBHandler.TABLE_USERS,
                BusDBHandler.COLUMN_USER_ID + "=" + user.getUserId(), null);
    }

    @NonNull
    public Bus addBus(@NonNull Bus busToAdd) {
        ContentValues values = new ContentValues();
        values.put(BusDBHandler.COLUMN_FROM, busToAdd.getFrom());
        values.put(BusDBHandler.COLUMN_TO, busToAdd.getTo());
        values.put(BusDBHandler.COLUMN_DEPARTURE, busToAdd.getDeptTime());
        values.put(BusDBHandler.COLUMN_ARRIVAL, busToAdd.getArriveTime());
        values.put(BusDBHandler.COLUMN_SEATS, busToAdd.getSeats());
        values.put(BusDBHandler.COLUMN_PRICE, busToAdd.getPrice());
        long insertId = database.insert(BusDBHandler.TABLE_BUSES, null, values);
        busToAdd.setNumber(insertId);

        return busToAdd;
    }

    @NonNull
    public Bus getBus(long id) {

        Cursor cursor = database.query(BusDBHandler.TABLE_BUSES, allBusColumns,
                BusDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},
                null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Bus b = new Bus(Long.parseLong(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),
                cursor.getString(4), Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)));

        cursor.close();

        // return bus
        return b;
    }

    @NonNull
    public List<Bus> getAllBuses() {

        Cursor cursor = database.query(BusDBHandler.TABLE_BUSES, allBusColumns,
        null,null,null, null, null);

        List<Bus> allBuses = new ArrayList<>();

        if (cursor.getCount() > 0 ){
            while (cursor.moveToNext()) {
                Bus bus = new Bus();
                bus.setNumber(cursor.getLong(cursor.getColumnIndex(BusDBHandler.COLUMN_ID)));
                bus.setFrom(cursor.getString(cursor.getColumnIndex(BusDBHandler.COLUMN_FROM)));
                bus.setTo(cursor.getString(cursor.getColumnIndex(BusDBHandler.COLUMN_TO)));
                bus.setDeptTime(cursor.getString(
                        cursor.getColumnIndex(BusDBHandler.COLUMN_DEPARTURE)));
                bus.setArriveTime(cursor.getString(
                        cursor.getColumnIndex(BusDBHandler.COLUMN_ARRIVAL)));
                bus.setSeats(Integer.parseInt((cursor.getString(
                        cursor.getColumnIndex(BusDBHandler.COLUMN_SEATS)))));
                bus.setPrice(Integer.parseInt((cursor.getString(
                        cursor.getColumnIndex(BusDBHandler.COLUMN_PRICE)))));
                allBuses.add(bus);
            }
        }

        cursor.close();
        // return All Buses
        return allBuses;
    }

    public int updateBus(@NonNull Bus bus) {

        ContentValues values = new ContentValues();
        values.put(BusDBHandler.COLUMN_FROM, bus.getFrom());
        values.put(BusDBHandler.COLUMN_TO, bus.getTo());
        values.put(BusDBHandler.COLUMN_DEPARTURE, bus.getDeptTime());
        values.put(BusDBHandler.COLUMN_ARRIVAL, bus.getArriveTime());
        values.put(BusDBHandler.COLUMN_SEATS, bus.getSeats());
        values.put(BusDBHandler.COLUMN_PRICE, bus.getPrice());

        // updating row
        return database.update(BusDBHandler.TABLE_BUSES, values,
                BusDBHandler.COLUMN_ID + "=?",
                new String[] { String.valueOf(bus.getNumber())});
    }

    public void removeBus(@NonNull Bus bus) {

        database.delete(BusDBHandler.TABLE_BUSES,
                BusDBHandler.COLUMN_ID + "=" +
                        bus.getNumber(), null);
    }

}
