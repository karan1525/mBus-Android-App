package io.github.karan.mbus.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.github.karan.mbus.R;

public class mBus_HomeActivity extends AppCompatActivity {

    private Button weatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__home_screen);

        weatherButton = findViewById(R.id.weather_check);
        openWeatherPage();
    }

    public void openWeatherPage() {
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mBus_WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
