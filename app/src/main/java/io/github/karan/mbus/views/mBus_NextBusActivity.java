package io.github.karan.mbus.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.Random;

import io.github.karan.mbus.R;
import io.github.karan.mbus.database.BusDBOperations;
import io.github.karan.mbus.models.Bus;


/**
 * Code citation for gestures:
 * https://github.com/alphamu/android-gestures-tutorial
 */
public class mBus_NextBusActivity extends AppCompatActivity implements OnGestureListener,
        OnDoubleTapListener {

    private TextView busId;
    private TextView from;
    private TextView to;
    private TextView departure;
    private TextView arrival;
    private TextView seats;
    private TextView price;

    private TextView gestures;
    private GestureDetector mGestureDetector;

    private BusDBOperations busOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__next_bus);

        final String MBUS_CHOSEN = "Thank you for choosing mBus";

        busId = findViewById(R.id.bus_number_content_view);
        from = findViewById(R.id.trip_from_content_view);
        to = findViewById(R.id.trip_to_textView);
        departure = findViewById(R.id.depart_content_view);
        arrival = findViewById(R.id.arrival_content_view);
        seats = findViewById(R.id.seat_content_view);
        price = findViewById(R.id.price_contentView);
        gestures = findViewById(R.id.tap_gesture_textView);

        mGestureDetector = new GestureDetector(this, this);
        mGestureDetector.setOnDoubleTapListener(this);

        TastyToast.makeText(getApplicationContext(), MBUS_CHOSEN,
                TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

        busOps = new BusDBOperations(this);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        prepend("onDoubleTap() ptrs:" + e.getPointerCount());

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        prepend("onDoubleTapEvent() ptrs:" + e.getPointerCount());

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle("Gesture Test TextView");
        builder.setMessage("Would you like to go back to previous activity?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }

                Intent intent = new Intent(mBus_NextBusActivity.this,
                        mBus_HomeActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing
            }
        });
        builder.show();

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        prepend("onSingleTapConfirmed() ptrs:" + e.getPointerCount());

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        prepend("onDown() ptrs:" + e.getPointerCount());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        prepend("onFling() ptrs:e1:" + e1.getPointerCount() + " e2:" + e2.getPointerCount());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        prepend("onLongPress() ptrs:" + e.getPointerCount());

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        //e1 The first down motion event that started the scrolling.
        //e2 The move motion event that triggered the current onScroll.
        prepend("onScroll() ptrs:e1:" + e1.getPointerCount() + " e2:" + e2.getPointerCount());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        prepend("onShowPress() ptrs:" + e.getPointerCount());

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        prepend("onSingleTapUp() ptrs:" + e.getPointerCount());
        return true;
    }


    private void prepend(String method) {
        StringBuilder s = new StringBuilder(gestures.getText());

        s.insert(0, '\n');
        s.insert(0, method);
        gestures.setText(s);
    }

    protected void onResume() {
        super.onResume();
        busOps.open();
        populateDataFromDB();
    }

    protected void onPause() {
        super.onPause();
        busOps.close();
    }

    private void populateDataFromDB() {

        Bus currentUserBus = busOps.getBus(generateRandom());

        busId.setText(String.valueOf(currentUserBus.getNumber()));
        from.setText(currentUserBus.getFrom());
        to.setText(currentUserBus.getTo());
        departure.setText(currentUserBus.getDeptTime());
        arrival.setText(currentUserBus.getArriveTime());
        seats.setText(String.valueOf(generateRandomSeat()));
        price.setText(String.valueOf(currentUserBus.getPrice()));

    }

    private int generateRandom() {
        Random rand = new Random();

        return rand.nextInt(6) + 1;
    }

    private String generateRandomSeat() {

        Random rand = new Random();
        int row = rand.nextInt(15) + 1;
        int column = rand.nextInt(4) + 1;

        if (column == 1) {

            return String.valueOf(row) + "A";

        } else if (column == 2) {

            return String.valueOf(row) + "B";

        } else if (column == 3) {

            return String.valueOf(row) + "C";

        } else {

            return String.valueOf(row) + "D";

        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, mBus_HomeActivity.class);
        startActivity(intent);
        finish();

    }
}
