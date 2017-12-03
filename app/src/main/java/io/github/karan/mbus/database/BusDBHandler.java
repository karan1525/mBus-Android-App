package io.github.karan.mbus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A class that manages all the DB
 * for the Buses
 */

public class BusDBHandler extends SQLiteOpenHelper {

    //String number, String from, String to, String dept, String arrive, int seats, int price

    private static final String DATABASE_NAME = "buses.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_BUSES = "buses";
    static final String COLUMN_ID = "busID";
    static final String COLUMN_FROM = "from";
    static final String COLUMN_TO = "to";
    static final String COLUMN_DEPARTURE = "depart";
    static final String COLUMN_ARRIVAL = "arrival";
    static final String COLUMN_SEATS = "seats";
    static final String COLUMN_PRICE = "price";

    static final String TABLE_USERS = "users";
    static final String COLUMN_USER_ID = "userID";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_GENDER = "gender";
    static final String COLUMN_BOOKED = "busBooked";
    static final String COLUMN_TRIPS = "trips";

    private static final String BUSES_TABLE =
            " CREATE TABLE " + TABLE_BUSES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FROM + " TEXT, " +
                    COLUMN_TO + " TEXT, " +
                    COLUMN_DEPARTURE + " TEXT, " +
                    COLUMN_ARRIVAL + " TEXT, " +
                    COLUMN_SEATS + " NUMERIC, " +
                    COLUMN_PRICE + " NUMBERIC" + ")";

    private static final String USERS_TABLE =
            " CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_BOOKED + " NUMERIC, " +
                    COLUMN_TRIPS + " NUMERIC" + ")";

    BusDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUSES_TABLE);
        db.execSQL(USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_BUSES);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL(BUSES_TABLE);
        db.execSQL(USERS_TABLE);
    }

}