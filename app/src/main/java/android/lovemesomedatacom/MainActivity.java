package android.lovemesomedatacom;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends MenuActivity {

    private static final String TAG = "MainActivity";


    private TextView tvClassCancellations;
    private TextView tvFindTeacherTV;
    private TextView tvAddToCalendar;
    private TextView tvNotes;
    private TextView tvWeatherTV;
    private TextView tvCurrentTemperature;
    private TextView tvAcademicCalendar;
    private ImageView dawsonLogo;
    private ImageView teamLogo;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-11-17 15:55:54 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvClassCancellations = (TextView)findViewById( R.id.tvClassCancellations );
        tvFindTeacherTV = (TextView)findViewById( R.id.tvFindTeacherTV );
        tvAddToCalendar = (TextView)findViewById( R.id.tvAddToCalendar );
        tvNotes = (TextView)findViewById( R.id.tvNotes );
        tvWeatherTV = (TextView)findViewById( R.id.tvWeatherTV );
        tvAcademicCalendar = (TextView)findViewById( R.id.tvAcademicCalendar );
        tvCurrentTemperature = (TextView)findViewById( R.id.tvCurrentTemperature );
        dawsonLogo = (ImageView)findViewById(R.id.dawsonLogo);
        teamLogo = (ImageView)findViewById(R.id.teamLogo);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // load views to be used
        findViews();

        // load icons for each text view
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        tvFindTeacherTV.setTypeface(font);
        tvClassCancellations.setTypeface(font);
        tvAddToCalendar.setTypeface(font);
        tvNotes.setTypeface(font);
        tvWeatherTV.setTypeface(font);
        tvAcademicCalendar.setTypeface(font);
        tvCurrentTemperature.setTypeface(font);

        dawsonLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dawsoncollege.qc.ca/computer-science-technology/"));
                startActivity(linkIntent);
            }
        });

        teamLogo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

        tvWeatherTV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(i);
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


    public void classCancellationClick(View view) {

        Intent aboutIntent = new Intent(this, ClassCancelationActivity.class);

        Log.d(TAG, "classCancellationClick");
        startActivity(aboutIntent);
    }
}
