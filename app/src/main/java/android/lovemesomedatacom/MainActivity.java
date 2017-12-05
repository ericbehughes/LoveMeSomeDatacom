package android.lovemesomedatacom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends MenuActivity {

    private static final String TAG = "MainActivity";

    private SharedPreferences prefs;
    private String firstName;

    private TextView tvClassCancellations;
    private TextView tvFindTeacherTV;
    private TextView tvAddToCalendar;
    private TextView tvNotes;
    private TextView tvWeatherTV;
    private TextView tvCurrentTemperature;
    private TextView tvAcademicCalendar;
    private ImageView dawsonLogo;
    private ImageView teamLogo;

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
        dawsonLogo = (ImageView)findViewById(R.id.imgDawsonLogo);
        teamLogo = (ImageView)findViewById(R.id.imgTeamLogo);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        //Initial and only authentication of the app, used to access the database
        mAuth.signInWithEmailAndPassword(FIREBASE_USER, FIREBASE_PASSWORD);

        // load views to be used
        findViews();

        // load icons for each text view
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        tvFindTeacherTV.setTypeface(font);
        tvClassCancellations.setTypeface(font);
        tvAddToCalendar.setTypeface(font);
        tvNotes.setTypeface(font);
        tvWeatherTV.setTypeface(font);
        tvAcademicCalendar.setTypeface(font);
        tvCurrentTemperature.setTypeface(font);
        prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);

        firstName = prefs.getString(SharedPreferencesKey.FIRST_NAME.toString(), "default_first_name");

        prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);
        if (prefs == null){

        }
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
        Log.d(TAG, "AcademicCalendarActivity");
        startActivity(i);
    }

    public void getLocationForTemperatureClick(View view) {

        Intent i = new Intent(this, TemperatureLoaction.class);
        Log.d(TAG, "AcademicCalendarActivity");
        startActivity(i);
    }

}
