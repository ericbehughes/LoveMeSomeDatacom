package android.lovemesomedatacom;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    Calendar beginTime = Calendar.getInstance();

    private TextView tvEventName;
    private TextView etEventName;
    private TextView tvStartTime;
    private TextView tvStartTimeValue;
    private TextView tvEndTime;
    private TextView tvEndTimeValue;
    private TextView tvDate;
    private TextView tvDateValue;


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


        /* create calndar object */
        final Calendar myCalendar = Calendar.getInstance();
/* find textview*/

/* and copy the fallowing code*/
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                tvStartTimeValue.setText(sdf.format(myCalendar.getTime()));

            }

        };


        tvStartTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CalendarActivity.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

}









