package android.lovemesomedatacom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

public class SettingsActivity extends MenuActivity {

    private final String TAG = getResources().getString(R.string.settings_activity_tag);
    private SharedPreferences prefs;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String timeStamp;
    private boolean isSaved;

    private TextView tvFirstName;
    private EditText etFirstName;
    private TextView tvLastName;
    private EditText etLastName;
    private TextView tvEmail;
    private EditText etEmail;
    private TextView tvPassword;
    private EditText etPassword;
    private TextView tvTimeStamp;
    private TextView etTimeStamp;



    /**
     * private method to load view objects
     */
    private void findViews() {
        tvFirstName = (TextView)findViewById( R.id.tvFirstName );
        etFirstName = (EditText)findViewById( R.id.etFirstName );
        tvLastName = (TextView)findViewById( R.id.tvLastName );
        etLastName = (EditText)findViewById( R.id.etLastName );
        tvEmail = (TextView)findViewById( R.id.tvEmail );
        etEmail = (EditText)findViewById( R.id.etEmail );
        tvPassword = (TextView)findViewById( R.id.tvPassword );
        etPassword = (EditText)findViewById( R.id.etPassword );
        tvTimeStamp = (TextView)findViewById( R.id.tvTimeStamp );
        etTimeStamp = (TextView)findViewById( R.id.etTimeStampValue );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_settings);
        this.setTitle(R.string.settings_calendar_activity_title);

        isSaved = true;
        Log.d(TAG, "Calendar.getInstance.getTime.ToString()" + Calendar.getInstance().getTime().toString());
        findViews();

        /**
         * load saved preferences if user has already submitted their info before
         */
        prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);
        if (prefs != null){
            firstName = prefs.getString(SharedPreferencesKey.FIRST_NAME.toString(), "");
            etFirstName.setText(firstName);
            lastName = prefs.getString(SharedPreferencesKey.LAST_NAME.toString(), "");
            etLastName.setText(lastName);
            email = prefs.getString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), "");
            etEmail.setText(email);
            password = prefs.getString(SharedPreferencesKey.PASSWWORD.toString(), "");
            etPassword.setText(password);
            Log.d(TAG, "Calendar.getInstance.getTime.ToString()" + Calendar.getInstance().getTime().toString());
            timeStamp = prefs.getString(SharedPreferencesKey.DATE_STAMP.toString(), Calendar.getInstance().getTime().toString());
            Log.d(TAG, "Timestamp from shared preferences" + timeStamp);
            etTimeStamp.setText(timeStamp);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        // get all users new info before screen closes
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email= etEmail.getText().toString();
        password = etPassword.getText().toString();
        timeStamp = etTimeStamp.getText().toString();
        SharedPreferences.Editor editor = prefs.edit();
        if (isSaved){
            Log.d(TAG, "isSaved == true" + timeStamp);
            // save users info to shared preferences
            editor.putString(SharedPreferencesKey.FIRST_NAME.toString(), firstName);
            editor.putString(SharedPreferencesKey.LAST_NAME.toString(), lastName);
            editor.putString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), email);
            editor.putString(SharedPreferencesKey.PASSWWORD.toString(), password);
            editor.putString(SharedPreferencesKey.DATE_STAMP.toString(), timeStamp);

            editor.commit();
        }


    }


    /**
     * overriden to add + button to the toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * custom button in the menu bar and creates a small dialog for the user
     * to enter a note.
     *
     * inserts into the DB and calls updateUI once again
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String settings_saved = getResources().getString(R.string.settings_saved_toast);
        switch (item.getItemId()) {
            case R.id.action_save_settings:
                Log.d(TAG, "saving settings from onOptionsItemSelected");
                Toast.makeText(this,settings_saved , Toast.LENGTH_SHORT).show();
                isSaved = true;
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                email= etEmail.getText().toString();
                password = etPassword.getText().toString();
                timeStamp = Calendar.getInstance().getTime().toString();
                etTimeStamp.setText(timeStamp);
                break;


        }
        return super.onOptionsItemSelected(item);

    }


    /**
     * custom back button alert dialog that turns isDiscard to true or false
     * before shared preferences are saved
     */
    @Override
    public void onBackPressed()
    {
        isSaved = isSettingsChanged();
        Log.d(TAG, "are settings saved onBackPressed " + isSaved);
        if(!isSaved){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.discard_pop_up_dialog)
                    .setTitle(R.string.confirm_discard_string);
            String yes = getResources().getString(R.string.yes_confirmation);
            String no = getResources().getString(R.string.no_confirmation);
            builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    SettingsActivity.this.finish();
                }
            });
            builder.setNegativeButton(no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            SettingsActivity.this.finish();
        }


    }

    /**
     * compare if new values are different than the updated values if they are
     * then saved is false otherwise true
     * @return
     */
    private boolean isSettingsChanged(){

        if (!firstName.equalsIgnoreCase(etFirstName.getText().toString())){
            return false;
        }
        else if (!lastName.equalsIgnoreCase(etLastName.getText().toString())){
            return false;
        }
        else if (!email.equalsIgnoreCase(etEmail.getText().toString())){
            return false;
        }
        else if (!password.equalsIgnoreCase(etPassword.getText().toString())){
            return false;
        }
        return true;
    }

}
