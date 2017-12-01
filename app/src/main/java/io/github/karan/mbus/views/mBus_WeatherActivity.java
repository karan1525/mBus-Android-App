package io.github.karan.mbus.views;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import io.github.karan.mbus.R;
import io.github.karan.mbus.controllers.WeatherController;

public class mBus_WeatherActivity extends AppCompatActivity {

    private TextView cityField;
    private TextView detailsField;
    private TextView currentTemperatureField;
    private TextView humidity_field;
    private TextView pressure_field;
    private TextView weatherIcon;
    private TextView updatedField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__weather);

        Typeface weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/weathericons-regular-webfont.ttf");

        cityField = findViewById(R.id.city_field);
        updatedField = findViewById(R.id.updated_field);
        detailsField = findViewById(R.id.details_field);
        currentTemperatureField = findViewById(R.id.current_temperature_field);
        humidity_field = findViewById(R.id.humidity_field);
        pressure_field = findViewById(R.id.pressure_field);
        weatherIcon = findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);

        WeatherController.placeIdTask asyncTask =
                new WeatherController.placeIdTask(new WeatherController.AsyncResponse() {

            public void processFinish(
                    String weather_city, String weather_description, String weather_temperature,
                    String weather_humidity, String weather_pressure, String weather_updatedOn,
                    String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                String humidity = "Humidity: "+ weather_humidity;
                humidity_field.setText(humidity);
                String pressure = "Pressure: " + weather_pressure;
                pressure_field.setText(pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });
        asyncTask.execute("37.3351420", "-121.8812760"); //  asyncTask.execute("Latitude", "Longitude")

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, mBus_HomeActivity.class);
        startActivity(intent);
        finish();
    }
}