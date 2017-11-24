package android.lovemesomedatacom;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

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
    private EditText etTimeStamp;


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
        etTimeStamp = (EditText)findViewById( R.id.etTimeStamp );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_settings);
        // load views to be used
        findViews();
        prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);

        firstName = prefs.getString(SharedPreferencesKey.FIRST_NAME.toString(), "default_first_name");

        Toast.makeText(getApplicationContext(), "shared preferences first name" + firstName, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = prefs.edit();
        firstName = etFirstName.getText().toString();
        editor.putString(SharedPreferencesKey.FIRST_NAME.toString(), firstName);

        editor.commit();

    }


    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Some Sample Text")
                .setTitle(R.string.confirm_discard_string);
        AlertDialog dialog = builder.create();
        dialog.show();

    }



}
