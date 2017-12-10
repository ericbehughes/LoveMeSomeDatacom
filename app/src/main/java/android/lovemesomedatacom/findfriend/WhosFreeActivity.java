package android.lovemesomedatacom.findfriend;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.lovemesomedatacom.business.MenuActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.business.SharedPreferencesKey;
import android.lovemesomedatacom.calendar.TimePickerFragment;
import android.lovemesomedatacom.calendar.CalendarActivity;
import android.content.pm.PackageManager;
import android.icu.util.TimeZone;
import android.lovemesomedatacom.adapters.FriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class WhosFreeActivity extends MenuActivity implements TimePickerFragment.OnCompleteListener {

    private static final String TAG = CalendarActivity.class.getSimpleName();
    Calendar beginTime = Calendar.getInstance();

    private TextView tvDay;
    private Spinner spinnerDay;
    private SharedPreferences prefs;

    private TextView whosFreeStartTimeTV;
    private FriendAdapter friendAdapter;
    private ListView whosFreeListView;

    private TextView whosFreeEndTimeTV;
    private Button whosFreeFindBtn;
    private int currentTimeElement;
    private long pickedDateMilis;
    private final String CALENDAR_PERMISSION = Manifest.permission.WRITE_CALENDAR;
    private final int PERMISSION_CODE = 1;
    private boolean isPermissionGranted;


    private void findViews() {

        spinnerDay = (Spinner) findViewById(R.id.whosFreeDaySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.whos_free_day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
        whosFreeStartTimeTV = (TextView) findViewById(R.id.whosFreeStartTimeTV);
        whosFreeEndTimeTV = (TextView) findViewById(R.id.whosFreeEndTimeTV);
        whosFreeFindBtn = findViewById(R.id.whosFreeFindBtn);
        whosFreeListView = findViewById(R.id.whosFreeList);
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        whosFreeStartTimeTV.setText(sdf.format(myCalendar.getTime()));
        whosFreeEndTimeTV.setText(sdf.format(myCalendar.getTime()));




    }


    /**
     * If permission is not given a small pop up is shown to the user
     * to ask for Calendar permission
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whos_free);
        findViews();
        this.setTitle(R.string.whos_free_title);
        if (savedInstanceState != null){

            whosFreeStartTimeTV.setText(savedInstanceState.getString("startTime").toString());
            whosFreeEndTimeTV.setText(savedInstanceState.getString("endTime").toString());

        }
        whosFreeFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);
                if(prefs != null) {
                    String email = prefs.getString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), "");
                    String password = prefs.getString(SharedPreferencesKey.PASSWORD.toString(), "");
                    String day = spinnerDay.getSelectedItem().toString();
                    day = transalteDay(day);
                    String startTime = whosFreeStartTimeTV.getText().toString();
                    String endTime = whosFreeEndTimeTV.getText().toString();
                    //int spliter = startTime.indexOf(':');
                    //startTime = startTime.substring(0,spliter)+startTime.substring(spliter+1);
                    //endTime = endTime.substring(0,spliter)+endTime.substring(spliter+1);
                    String url = "http://friendfinder08.herokuapp.com/api/api/breakfriends?";
                    //email=eric@gmail.com&password=password&day=1&start=1000&end=1700
                    String whosFreeQuery = url + "email=" + email + "&" + "password=" + password +
                            "&" + "day=" + day + "&" + "start=" + startTime + "&end=" + endTime;

                    String query = "http://friendfinder08.herokuapp.com/api/api/breakfriends?email=eric@gmail.com&password=password&day=1&start=1000&end=1700";
                    Log.d(TAG,"whosfreequery: "+whosFreeQuery);
                    //Intent freeList = new Intent(getApplicationContext(),FriendsByCourseListActivity.class);
                    //freeList.putExtra("query",whosFreeQuery);
                    //startActivity(freeList);
                    new WhosFreeTask(WhosFreeActivity.this, whosFreeQuery).execute();
                } else {
                    String checkSettings = "Check user settings" ;
                    Toast.makeText(getApplicationContext(),checkSettings, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * different style compared to the listener and DatePickerDialog
     * showTimePickerDialog instantiates a custom TimePickerFragment
     * @param v
     */
    public void showTimePickerDialog(View v) {
        currentTimeElement = v.getId();
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "inside save instance state");

        outState.putString("startTime", whosFreeStartTimeTV.getText().toString() );
        outState.putString("endTime", whosFreeEndTimeTV.getText().toString() );


    }

    public void openEmailForFriend() {
//        if (ContextCompat.checkSelfPermission(this,
//                CALENDAR_PERMISSION)
//                == PackageManager.PERMISSION_GRANTED) {
//            ContentResolver contentResolver = getContentResolver();
//            long calID = 3;
//            ContentValues values = new ContentValues();
//            values.put(CalendarContract.Events.DTSTART, pickedDateMilis);
//            values.put(CalendarContract.Events.DTEND, pickedDateMilis + 60000);
//            values.put(CalendarContract.Events.CALENDAR_ID, calID);
//            TimeZone tz = TimeZone.getDefault();
//            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
//            Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
//        } else {
//            Toast.makeText(this, R.string.permission_toast, Toast.LENGTH_LONG).show();
//        }

    }

    @Override
    public void onComplete(String time) {
        switch (currentTimeElement) {

            case R.id.whosFreeStartTimeTV:
                Log.d(TAG, "startTimeValue was clicked");
                whosFreeStartTimeTV.setText(time);
                break;
            case R.id.whosFreeEndTimeTV:
                Log.d(TAG, "endTImeValue was clicked");
                whosFreeEndTimeTV.setText(time);
                break;

        }
    }

    public void listOfFriends(ArrayList<Friend> friends){
        if(friends != null) {
            friendAdapter = new FriendAdapter(this, friends);
            this.whosFreeListView.setAdapter(friendAdapter);
            friendAdapter.addAll(friends);
            friendAdapter.notifyDataSetChanged();
        }
    }

    public void findFriendsClick(View view) {



    }

    private String transalteDay(String day){

        switch(day){
            case "Monday":
                day="1";
                break;
            case "Tuesday":
                day="2";
                break;
            case "Wednesday":
                day="3";
                break;
            case "Thursday":
                day="4";
                break;
            case "Friday":
                day="5";
                break;
            default:
                day="-1";
        }

        return day;
    }

}