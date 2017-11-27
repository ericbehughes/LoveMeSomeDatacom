package android.lovemesomedatacom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;

public class SettingsActivity extends MenuActivity {

    private SharedPreferences prefs;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String timeStamp;

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
        // load views to be used
        findViews();

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
            timeStamp = prefs.getString(SharedPreferencesKey.DATE_STAMP.toString(), Calendar.getInstance().getTime().toString());
            etTimeStamp.setText(timeStamp);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email= etEmail.getText().toString();
        password = etPassword.getText().toString();
        timeStamp = etTimeStamp.getText().toString();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPreferencesKey.FIRST_NAME.toString(), firstName);
        editor.putString(SharedPreferencesKey.LAST_NAME.toString(), lastName);
        editor.putString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), email);
        editor.putString(SharedPreferencesKey.PASSWWORD.toString(), password);
        editor.putString(SharedPreferencesKey.DATE_STAMP.toString(), timeStamp);

        editor.commit();

    }


    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.discard_pop_up_dialog)
                .setTitle(R.string.confirm_discard_string);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            SettingsActivity.this.finish();
        }
    });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }



}
