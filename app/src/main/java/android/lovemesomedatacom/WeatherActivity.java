package android.lovemesomedatacom;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherActivity extends MenuActivity {

    private static final String TAG = "WeatherActivity";
    private static final String URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    EditText input;
    Spinner spinner;
    Button weatherBtn;
    TextView temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate Invoked");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        findViews();

    }

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
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = input.getText().toString();
                String iso = spinner.getSelectedItem().toString();
                String query = URL + city + "," + iso + "&mode=xml&appid=080b8de151ba3865a7b5e255f448f10f";
                Log.d(TAG, query);
                new WeatherActivityTask(WeatherActivity.this , query).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        return true;
    }

    public void callBackData(ArrayList<Weather> result, double uv) {
        StringBuilder builder = new StringBuilder();
        for(Weather weather : result){
            builder.append(weather.start + " " + weather.end).append("\n")
                    .append("Temperature: " + weather.temperature)
                    .append("\n").append("Pressure: " +weather.pressure)
                    .append("\n").append("Humidity: " + weather.humidity).append("\n\n");
        }
        temperature.setText(builder.toString());
    }
}
