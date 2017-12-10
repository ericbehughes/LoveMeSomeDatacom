package android.lovemesomedatacom.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.lovemesomedatacom.academiccalendar.AcademicCalendar;
import android.lovemesomedatacom.classcancelation.ClassCancelationActivity;
import android.lovemesomedatacom.weather.CurrentTemperatureTask;
import android.lovemesomedatacom.business.GPSManager;
import android.lovemesomedatacom.business.MenuActivity;
import android.lovemesomedatacom.business.NetworkFactoryActivity;
import android.lovemesomedatacom.notes.NotesActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.settings.SettingsActivity;
import android.lovemesomedatacom.business.SharedPreferencesKey;
import android.lovemesomedatacom.weather.WeatherActivity;
import android.lovemesomedatacom.about.AboutActivity;
import android.lovemesomedatacom.calendar.CalendarActivity;
import android.lovemesomedatacom.findfriend.FindFriendsActivity;
import android.lovemesomedatacom.findfriend.WhosFreeActivity;
import android.lovemesomedatacom.findteacher.FindTeacherActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends MenuActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences prefs;
    private String firstName;

    private TextView tvClassCancellations;
    private TextView tvFindTeacherTV;
    private TextView tvAddToCalendar;
    private TextView tvNotes;
    private TextView tvWeatherTV;
    private TextView tvCurrentTemperature;
    private TextView tvAcademicCalendar;
    private TextView tvWhosFree;
    private TextView tvFindFriends;
    private ImageView dawsonLogo;
    private ImageView teamLogo;


    private GPSManager gps;
    private String URL;

    //Firebase
    private static final String FIREBASE_USER = "test1@example.com";
    private static final String FIREBASE_PASSWORD = "123123";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


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
        tvFindFriends = findViewById(R.id.tvFindFriends);
        tvWhosFree = findViewById(R.id.tvWhosFree);
        dawsonLogo = (ImageView)findViewById(R.id.imgDawsonLogo);
        teamLogo = (ImageView)findViewById(R.id.imgTeamLogo);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "testing class tag");

        mAuth = FirebaseAuth.getInstance();
        //Initial and only authentication of the app, used to access the database
        mAuth.signInWithEmailAndPassword(FIREBASE_USER, FIREBASE_PASSWORD);

        // load views to be used
        findViews();
        getLocationForTemperatureClick();

        // load icons for each text view
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        tvFindTeacherTV.setTypeface(font);
        tvClassCancellations.setTypeface(font);
        tvAddToCalendar.setTypeface(font);
        tvNotes.setTypeface(font);
        tvWeatherTV.setTypeface(font);
        tvAcademicCalendar.setTypeface(font);
        tvCurrentTemperature.setTypeface(font);
        tvWhosFree.setTypeface(font);
        tvFindFriends.setTypeface(font);

        prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);
        prefs.edit().clear();
        if (prefs.getAll().size() == 0 || prefs == null){
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            Log.d(TAG, "Settings not detected");
            startActivity(settingIntent);
        }

        firstName = prefs.getString(SharedPreferencesKey.FIRST_NAME.toString(), "default_first_name");

        dawsonLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dawsoncollege.qc.ca/computer-science-technology/"));
                startActivity(linkIntent);
            }
        });

        teamLogo.setOnClickListener(new View.OnClickListener() {

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

        new CurrentTemperatureTask(MainActivity.this, URL).execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        //Initial and only authentication of the app, used to access the Firebase database
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

    public void findTeacherClick(View view) {
        Intent teacherIntent = new Intent(this, FindTeacherActivity.class);
        Log.d(TAG, "FindTeacherActivity clicked");
        startActivity(teacherIntent);
    }


    public void classCancellationClick(View view) {

        Intent aboutIntent = new Intent(this, ClassCancelationActivity.class);

        Log.d(TAG, "classCancellationClick");
        startActivity(aboutIntent);
    }

    public void doStuff(View view) {
        Intent i = new Intent(this, NetworkFactoryActivity.class);

        Log.d(TAG, "NetworkFactoryActivity");
        startActivity(i);
    }

    public void showNotesClick(View view) {
        Intent i = new Intent(this, NotesActivity.class);

        Log.d(TAG, "NotesActivity");
        startActivity(i);
    }

    public void showCalendarView(View view) {
        Intent i = new Intent(this, CalendarActivity.class);

        Log.d(TAG, "CalendarActivity");
        startActivity(i);
    }

    public void showAcademicCalendarClick(View view) {
        Intent i = new Intent(this, AcademicCalendar.class);

        Log.d(TAG, "CalendarActivity");
        startActivity(i);
    }


    public void showFindFriends(View view){
        Intent intent = new Intent(this, FindFriendsActivity.class);
        startActivity(intent);
    }


    public void showWhosFree(View view) {
        Intent i = new Intent(this, WhosFreeActivity.class);

        Log.d(TAG, "WhosFreeActivity");
        startActivity(i);
    }

    public void getLocationForTemperatureClick() {

        int REQUEST_CODE_PERMISSION = 2;
        String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }

            gps = new GPSManager(MainActivity.this);

            // check if GPS enabled
            if(gps.canGetLocation()){

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                URL = "http://api.openweathermap.org/data/2.5/weather?appid=080b8de151ba3865a7b5e255f448f10f&units=metric&lat="+latitude+"&lon="+longitude;

            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsible for updating the UI with the response
     * from the CurrentTemperature async Task
     * @param result
     */
    public void updateCurrentTemperature(String result) {
        tvCurrentTemperature.setText(tvCurrentTemperature.getText() + " " + result);
    }
}
