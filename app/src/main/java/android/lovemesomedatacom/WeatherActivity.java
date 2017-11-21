package android.lovemesomedatacom;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class WeatherActivity extends MenuActivity {

    private static final String TAG = "WeatherActivity";
    EditText input;
    Spinner spinner;
    Button weatherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        findViews();
    }

    private void getUVIndex(String city, String iso) {

    }

    private void getForecast(String city, String iso) {
        String url = "api.openweathermap.org/data/2.5/forecast?q=";
        String query = city + "," + iso + "&mode=xml";

    }

    private void findViews(){
        input = (EditText)findViewById(R.id.inputCity);
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
                getUVIndex(city,iso);
                getForecast(city,iso);
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
}
