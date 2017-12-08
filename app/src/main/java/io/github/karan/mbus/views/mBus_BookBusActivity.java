package io.github.karan.mbus.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import io.github.karan.mbus.R;
import io.github.karan.mbus.database.BusDBOperations;
import io.github.karan.mbus.models.Bus;
import io.github.karan.mbus.models.User;

public class mBus_BookBusActivity extends AppCompatActivity {

    private BusDBOperations mBusOps;
    private Button cancelButton;
    private Button okayButton;
    private int mSelectedBusNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__book_bus);

        cancelButton = findViewById(R.id.trip_cancel);
        okayButton = findViewById(R.id.trip_ok);
        TextView promptMessage = findViewById(R.id.prompt_msg);

        mBusOps = new BusDBOperations(this);

        if (getIntent() != null &&
                getIntent().getExtras() != null) {

            String selected = getIntent().getExtras().getString("Bus");
            promptMessage.append(selected);

            mSelectedBusNumber = Integer.parseInt(parseIntent(selected));
        }

        onButtonCancel();
        onButtonOkay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBusOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBusOps.close();
    }

    private String parseIntent(String stringToParse) {

        String[] array = stringToParse.split(":");

        return array[1].substring(3,4);
    }

    private void onButtonOkay() {
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bus currentBus = mBusOps.getBus(mSelectedBusNumber);
                currentBus.setSeats(currentBus.getSeats() - 1);
                mBusOps.updateBus(currentBus);

                User currentUser = mBusOps.getUser(1);
                currentUser.setBusBooked(String.valueOf(mSelectedBusNumber));
                currentUser.setTripsTaken(currentUser.getTripsTaken() + 1);

                mBusOps.updateUser(currentUser);

                String success = "Thank you for choosing mBus! \n Your spot is secured!";

                TastyToast.makeText(getApplicationContext(), success,
                        TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                Intent intent = new Intent(mBus_BookBusActivity.this,
                        mBus_HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void onButtonCancel() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TastyToast.makeText(getApplicationContext(), "Trip cancelled by user",
                        TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                Intent intent = new Intent(mBus_BookBusActivity.this, mBus_HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
