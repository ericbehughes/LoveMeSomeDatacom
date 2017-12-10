package android.lovemesomedatacom;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by jahid on 12/10/15.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "TimePickerFragment";
    private OnCompleteListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        long milliseconds = c.get(Calendar.MILLISECOND);
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        long milliseconds = TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hourOfDay)
                + TimeUnit.MINUTES.toSeconds(minute));


        //cal.set(Calendar.HOUR_OF_DAY, hourOfDay );
        //Log.d(TAG, "text view + " + tv1);
        Log.d(TAG, "view.getHour + " + hourOfDay);
        Log.d(TAG, "view.getMinute + " + minute);
        String str = hourOfDay + ":" + minute;
        this.mListener.onComplete(str);

       // tv1.setText(hourOfDay + ":" + minute);

    }



    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }
}
