package android.lovemesomedatacom;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CalendarActivity extends MenuActivity implements TimePickerFragment.OnCompleteListener {

    private static final String TAG = CalendarActivity.class.getSimpleName();
    Calendar beginTime = Calendar.getInstance();

    private TextView tvEventName;
    private TextView etEventName;
    private TextView tvStartTime;
    private TextView tvStartTimeValue;
    private TextView tvEndTime;
    private TextView tvEndTimeValue;
    private TextView tvDate;
    private TextView tvDateValue;
    private int currentTimeElement;
    private long pickedDateMilis;
    private final String CALENDAR_PERMISSION = Manifest.permission.WRITE_CALENDAR;
    private final int PERMISSION_CODE = 1;
    private boolean isPermissionGranted;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-11-24 19:48:54 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvEventName = (TextView) findViewById(R.id.tvEventName);
        etEventName = (TextView) findViewById(R.id.etEventName);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvStartTimeValue = (TextView) findViewById(R.id.tvStartTimeValue);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvEndTimeValue = (TextView) findViewById(R.id.tvEndTimeValue);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDateValue = (TextView) findViewById(R.id.tvDateValue);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        findViews();
        this.setTitle(R.string.add_to_calendar_activity_title);
        final Calendar myCalendar = Calendar.getInstance();

        if (ContextCompat.checkSelfPermission(this,
                CALENDAR_PERMISSION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    CALENDAR_PERMISSION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{CALENDAR_PERMISSION},
                        2);

            }
        }

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(Calendar.HOUR_OF_DAY, 0);
                myCalendar.set(Calendar.MINUTE, 0);
                myCalendar.set(Calendar.SECOND, 0);
                myCalendar.set(Calendar.MILLISECOND, 0);
                pickedDateMilis = myCalendar.getTimeInMillis();
                Log.d(TAG, "CURRENT_DATE_ANON_CLASS: " + pickedDateMilis);
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                tvDateValue.setText(sdf.format(myCalendar.getTime()));
            }
        };

        tvDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CalendarActivity.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }
//    }


    public void showTimePickerDialog(View v) {
        currentTimeElement = v.getId();
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void addToCalendar(View view) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                CALENDAR_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            ContentResolver contentResolver = getContentResolver();
            long calID = 3;
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, pickedDateMilis);
            values.put(CalendarContract.Events.DTEND, pickedDateMilis + 60000);
            values.put(CalendarContract.Events.TITLE, tvEventName.getText().toString());
            values.put(CalendarContract.Events.CALENDAR_ID, calID);
            TimeZone tz = TimeZone.getDefault();
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
            Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
        } else {
            Toast.makeText(this, R.string.permission_toast, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onComplete(String time) {
        switch (currentTimeElement) {

            case R.id.tvStartTimeValue:
                Log.d(TAG, "startTimeValue was clicked");
                tvStartTimeValue.setText(time);
                break;
            case R.id.tvEndTimeValue:
                Log.d(TAG, "endTImeValue was clicked");
                tvEndTimeValue.setText(time);
                break;

        }
    }
}