package io.github.karan.mbus.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.Random;

import io.github.karan.mbus.R;
import io.github.karan.mbus.database.BusDBOperations;
import io.github.karan.mbus.models.Bus;

public class mBus_NextBusActivity extends AppCompatActivity {

    private TextView busId;
    private TextView from;
    private TextView to;
    private TextView departure;
    private TextView arrival;
    private TextView seats;
    private TextView price;

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

        TastyToast.makeText(getApplicationContext(), MBUS_CHOSEN,
                TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

        busOps = new BusDBOperations(this);

        populateDataFromDB();
    }

    protected void onResume() {
        super.onResume();
        busOps.open();
    }

    protected void onPause() {
        super.onPause();
        busOps.close();
    }

    private void populateDataFromDB() {

        Bus currentUserBus = busOps.getBus(1);

        busId.setText(String.valueOf(currentUserBus.getNumber()));
        from.setText(currentUserBus.getFrom());
        to.setText(currentUserBus.getTo());
        departure.setText(currentUserBus.getDeptTime());
        arrival.setText(currentUserBus.getArriveTime());
        seats.setText(generateRandomSeat());
        price.setText(currentUserBus.getPrice());

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
}
