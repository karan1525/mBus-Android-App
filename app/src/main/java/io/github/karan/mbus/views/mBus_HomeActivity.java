package io.github.karan.mbus.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.karan.mbus.R;

public class mBus_HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int mYear, mMonth, mDay;
    private Button continueButton;
    private Spinner mFromSpinner;
    private EditText dateEditText;
    private EditText totalPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__home_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView =  navigationView.getHeaderView(0);

        TextView mAccountName = headerView.findViewById(R.id.account_name);
        TextView mAccountEmail = headerView.findViewById(R.id.account_email);

        mAccountName.setText(mBus_SignInActivity.userName);
        mAccountEmail.setText(mBus_SignInActivity.userEmail);

        mFromSpinner = findViewById(R.id.from_spinner);
        String[] categories = new String[]{"San Francisco", "Santa Clara", "San Jose", "Santa Clarita"};
        ArrayAdapter<String> myAdapt = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,categories);
        myAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFromSpinner.setAdapter(myAdapt);

        dateEditText = findViewById(R.id.date_content_view);
        continueButton = findViewById(R.id.continue_button);
        totalPeople = findViewById(R.id.total_people_content_view);
        setupDate();
        setupContinueButton();

    }

    private void setupDate() {
        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //To show current date in the date picker
                Calendar mCurrentDate = Calendar.getInstance();
                mYear = mCurrentDate.get(Calendar.YEAR);
                mMonth = mCurrentDate.get(Calendar.MONTH);
                mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(mBus_HomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker,
                                          int selectedYear, int selectedMonth, int selectedDay) {

                        String selectedDate = selectedMonth + "/" + selectedDay + "/" +  selectedYear;
                        dateEditText.setText(selectedDate);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });
    }

    private void setupContinueButton() {

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mBus_HomeActivity.this,
                        mBus_AvailableBusesActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<String> information = new ArrayList<>();

                information.add(0, mFromSpinner.getSelectedItem().toString()); //starting
                information.add(1, "Pleasanton"); //ending
                information.add(2, totalPeople.getText().toString()); //totalPeople
                information.add(3, dateEditText.getText().toString());

                bundle.putStringArrayList("info", information);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mbus_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), mBus_ProfileActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_available_buses) {
            Intent intent = new Intent(getApplicationContext(), mBus_AvailableBusesActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_weather) {
            Intent intent = new Intent(getApplicationContext(), mBus_WeatherActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_sign_out) {
            signOut();

        } else if (id == R.id.nav_information) {
            Intent intent = new Intent(getApplicationContext(), mBus_InformationActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(getApplicationContext(), mBus_HelpActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.check_maps) {
            Intent intent = new Intent(getApplicationContext(), mBus_LocationActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.next_trip) {
            Intent intent = new Intent(getApplicationContext(), mBus_NextBusActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_SIGN_IN);
        final Intent intent = new Intent(this, mBus_SignInActivity.class);
        signInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "You have successfully logged out!",
                                Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                });
    }
}
