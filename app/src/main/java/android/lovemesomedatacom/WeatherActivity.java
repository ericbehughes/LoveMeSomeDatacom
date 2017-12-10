package android.lovemesomedatacom;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherActivity extends MenuActivity {

    private static final String TAG = "WeatherActivity";
    private static final String ForecastURL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    EditText input;
    Spinner spinner;
    Button weatherBtn;
    TextView temperature;

    /**
     * Overridden onCreate method, which is responsible for
     * assigning the correct content view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate Invoked");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        findViews();
        this.setTitle(R.string.weather_activity_title);

    }

    /**
     * Private helper method, which is responsible for retrieving
     * all the required views, attaching event listeners and defining
     * default value if required.
     */
    private void findViews(){
        input = (EditText)findViewById(R.id.inputCity);
        input.setText("Montreal");
        temperature = (TextView)findViewById(R.id.temperature);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.iso_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        weatherBtn = (Button)findViewById(R.id.weatherBtn);
        //On the button click, an Async task is launched
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temperature.setText("");
                String city = input.getText().toString();
                String iso = spinner.getSelectedItem().toString();
                String forecastQuery = ForecastURL + city + "," + iso + "&mode=xml&units=metric&appid=080b8de151ba3865a7b5e255f448f10f";
                Log.d(TAG, forecastQuery);
                new WeatherActivityTask(WeatherActivity.this , forecastQuery).execute();
            }
        });
    }

    /**
     * Overridden onCreateOptionsMenu from the Menu Activity
     * @param menu
     * @return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * Overridden onOptionsItemSelected from the Menu Activity
     * @param item
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        return true;
    }

    /**
     * This method is responsible for updating the UI, with the information
     * retrieved from the API call.
     * @param result
     * @param uv
     */
    public void displayForecast(ArrayList<Weather> result, String uv) {
        StringBuilder builder = new StringBuilder();
        for(Weather weather : result){
            builder.append(weather.getStart().replace("T", " ") + " - " + weather.getEnd().replace("T", " "))
                    .append("\n").append("Temperature: " + weather.getTemperature())
                    .append("\n").append("Description: " + weather.getSymbol())
                    .append("\n").append("Precipitation: " + weather.getPrecipitation())
                    .append("\n").append("Clouds: " + weather.getClouds())
                    .append("\n").append("Wind Speed: " + weather.getWindSpeed())
                    .append("\n").append("Wind Direction: " + weather.getWindDirection())
                    .append("\n").append("Pressure: " +weather.getPressure())
                    .append("\n").append("Humidity: " + weather.getHumidity()).append("\n\n");
            temperature.setText(temperature.getText() + builder.toString());
            builder = new StringBuilder();
        }
        temperature.setText(temperature.getText() + "UV Index: " + uv);
    }

}