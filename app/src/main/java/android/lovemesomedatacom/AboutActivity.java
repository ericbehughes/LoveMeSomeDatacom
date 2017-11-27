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

public class AboutActivity extends MenuActivity {

    private static final String TAG = "About";
    TextView courseID, memberNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //If user is authenticated then call the Find Views method here
        findViews();
        this.setTitle(R.string.about_activity_title);
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

    /**
     * The following will populate the views necessary and attach any
     * listener if required.
     */
    private void findViews(){

        courseID = (TextView)findViewById(R.id.courseID);
        courseID.setOnClickListener(openURL);

        Log.d(TAG, "OpenURL on Click Listener Added");

        memberNames = (TextView)findViewById(R.id.memberNames);
        memberNames.setOnClickListener(showDialog);

        Log.d(TAG, "showDialog on Click Listener Added");
    }


    /**
     * The following defines a onClickListener, which is responsible for displaying
     * a dialog containing the information about the team.
     */
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

    /**
     * The following defines a onClickListener, which is responsible for redirecting
     * to the Dawson College Computer Science page.
     */
    private View.OnClickListener openURL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dawsoncollege.qc.ca/computer-science-technology/"));
            startActivity(linkIntent);
        }
    };

}
