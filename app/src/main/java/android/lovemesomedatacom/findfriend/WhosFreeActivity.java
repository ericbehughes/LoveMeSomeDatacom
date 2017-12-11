package android.lovemesomedatacom.findfriend;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
<<<<<<< HEAD:app/src/main/java/android/lovemesomedatacom/WhosFreeActivity.java
=======
import android.lovemesomedatacom.business.MenuActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.business.SharedPreferencesKey;
import android.lovemesomedatacom.calendar.TimePickerFragment;
import android.lovemesomedatacom.calendar.CalendarActivity;
import android.content.pm.PackageManager;
import android.icu.util.TimeZone;
>>>>>>> 19eafd42b3ada0bde40d7aa75b4d76fefcc5bbc1:app/src/main/java/android/lovemesomedatacom/findfriend/WhosFreeActivity.java
import android.lovemesomedatacom.adapters.FriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
<<<<<<< HEAD:app/src/main/java/android/lovemesomedatacom/WhosFreeActivity.java
import android.support.v7.app.AlertDialog;
=======
>>>>>>> 19eafd42b3ada0bde40d7aa75b4d76fefcc5bbc1:app/src/main/java/android/lovemesomedatacom/findfriend/WhosFreeActivity.java
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

/**
 * WhosFreeActivity gives user's friends who are on break
 * at the given time. The activity uses an async task that
 * retrieves the friends by making an api call to friendfinder
 * and populates a list view.
 */

public class WhosFreeActivity extends MenuActivity implements TimePickerFragment.OnCompleteListener {

<<<<<<< HEAD:app/src/main/java/android/lovemesomedatacom/WhosFreeActivity.java
    private static final String TAG = android.lovemesomedatacom.CalendarActivity.class.getSimpleName();
=======
    private static final String TAG = CalendarActivity.class.getSimpleName();
    Calendar beginTime = Calendar.getInstance();
>>>>>>> 19eafd42b3ada0bde40d7aa75b4d76fefcc5bbc1:app/src/main/java/android/lovemesomedatacom/findfriend/WhosFreeActivity.java

    private Spinner spinnerDay;
    private SharedPreferences prefs;

    private TextView whosFreeStartTimeTV;
    private FriendAdapter friendAdapter;
    private ListView whosFreeListView;

    private TextView whosFreeEndTimeTV;
    private Button whosFreeFindBtn;
    private int currentTimeElement;


    /**
     * findViews gets all necessary views from the layout
     */
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

            //If list was saved in instancem it repopulates listview with instance saved
            ArrayList<Friend> friends = savedInstanceState.getParcelableArrayList("State_List");
            listFriends(friends);

        }

        // find button listener that starts task to get friends
        whosFreeFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Getting Friends");
                prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);
                if(prefs != null) {
                    String email = prefs.getString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), "");
                    String password = prefs.getString(SharedPreferencesKey.PASSWORD.toString(), "");
                    String day = spinnerDay.getSelectedItem().toString();
                    day = translateDay(day);
                    String startTime = whosFreeStartTimeTV.getText().toString();
                    String endTime = whosFreeEndTimeTV.getText().toString();

                    String url = "http://friendfinder08.herokuapp.com/api/api/breakfriends?";
                    String whosFreeQuery = url + "email=" + email + "&" + "password=" + password +
                            "&" + "day=" + day + "&" + "start=" + startTime + "&end=" + endTime;

                    Log.d(TAG,"whosfreequery: "+whosFreeQuery);
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

    /**
     * Overriden onSaveInstanceState saves the state of the listview and time fields
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "inside save instance state");

        if(this.friendAdapter != null)
            outState.putParcelableArrayList("State_List",friendAdapter.getList());

        outState.putString("startTime", whosFreeStartTimeTV.getText().toString() );
        outState.putString("endTime", whosFreeEndTimeTV.getText().toString() );


    }

    @Override
    public void onComplete(String time) {
        // logs currently selected time TextView
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

    /**
     * listFriends is used by the async task to call back to the main thread
     * and set ListView.
     *
     * @param friends list of friends
     */
    public void listFriends(ArrayList<Friend> friends){
        if(friends != null) {
            if (friends.get(0).getFirstName().equals("Error")) {
                showAlert(friends.get(0).getFirstName() + " " + friends.get(0).getLastName(),
                        friends.get(0).getEmail());
            }else if(friends.size() == 0) {
                showAlert(getString(R.string.no_avail),getString(R.string.friend_busy));
            }else {
                friendAdapter = new FriendAdapter(this, friends);
                this.whosFreeListView.setAdapter(friendAdapter);
                friendAdapter.addAll(friends);
                friendAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * showAlert displays an alert with the appropriate message and title
     *
     * @param title
     * @param msg
     */
    private void showAlert(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg)
                .setTitle(title);

        builder.setPositiveButton(R.string.ok, null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    /**
     * translateDay takes in the name of the day and returns
     * its numeric representation
     * @param day
     * @return
     */
    private String translateDay(String day){

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