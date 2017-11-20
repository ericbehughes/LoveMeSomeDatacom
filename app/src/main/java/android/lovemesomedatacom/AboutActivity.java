package android.lovemesomedatacom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Rebeu on 2017-11-20.
 */

public class AboutActivity extends MenuActivity {

    private static final String TAG = "About";
    TextView courseID, memberNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //If user is authenticated then call the LoadItems method here

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        return true;
    }

    private void loadItems(){

        courseID = (TextView)findViewById(R.id.courseID);
        courseID.setOnClickListener(openURL);

        Log.d(TAG, "OpenURL on Click Listener Added");

        memberNames = (TextView)findViewById(R.id.memberNames);
        memberNames.setOnClickListener(showDialog);

        Log.d(TAG, "showDialog on Click Listener Added");
    }

    private View.OnClickListener showDialog = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
            builder.setMessage(R.string.about_message)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    private View.OnClickListener openURL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dawsoncollege.qc.ca/computer-science-technology/"));
            startActivity(linkIntent);
        }
    };

}
