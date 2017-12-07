package io.github.karan.mbus.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.github.karan.mbus.R;
import io.github.karan.mbus.database.BusDBOperations;
import io.github.karan.mbus.models.Bus;

public class mBus_BusDetailActivity extends AppCompatActivity {


    private BusDBOperations mBusOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__bus_detail);

        mBusOps = new BusDBOperations(this);
        mBusOps.open();

        if (getIntent() != null &&
                getIntent().getExtras() != null) {

            String selected = getIntent().getExtras().getString("Bus");
            int mSelectedBusNumber = Integer.parseInt(parseIntent(selected));

            getBusFromDB(mSelectedBusNumber);
        }

        Configuration configuration = getResources().getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, mBus_WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void getBusFromDB(int busNumber) {
        Bus currentBus = mBusOps.getBus(busNumber);
        populateData(currentBus);
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, mBus_HomeActivity.class);
        startActivity(intent);
        finish();

    }

    private void populateData(Bus currentBus) {
        TextView busNumber = findViewById(R.id.bus_number_content);
        TextView depart = findViewById(R.id.departure_content);
        TextView arrive = findViewById(R.id.arrival_content);
        TextView price = findViewById(R.id.price_content);
        TextView seats = findViewById(R.id.seats_content);
        TextView from = findViewById(R.id.from_content);
        TextView to = findViewById(R.id.to_content);

        busNumber.setText(String.valueOf(currentBus.getNumber()));
        depart.setText(currentBus.getDeptTime());
        arrive.setText(currentBus.getArriveTime());
        price.setText(String.valueOf(currentBus.getPrice()));
        seats.setText(String.valueOf(currentBus.getSeats()));
        from.setText(currentBus.getFrom());
        to.setText(currentBus.getTo());

    }

    private String parseIntent(String stringToParse) {

        String[] array = stringToParse.split(":");

        return array[1].substring(3,4);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBusOps.close();
    }
}
