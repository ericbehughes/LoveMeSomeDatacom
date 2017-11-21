package android.lovemesomedatacom;



import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;

//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "Menu";

    private SharedPreferences prefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.MENU_ABOUT:
                handleAbout();
                return true;
            case R.id.MENU_DAWSON:
                handleDawson();
                return true;
            case R.id.MENU_SETTING:
                handleSetting();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method, which handles launching the intent related to the
     * About Activity.
     */
    private void handleAbout() {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        Log.d(TAG, "Menu About Clicked");
        startActivity(aboutIntent);
    }

    /**
     * Helper method, which handles launching the intent responsible
     * for opening the Dawson Computer Science Web page.
     */
    private void handleDawson() {
        Intent dawsonIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dawsoncollege.qc.ca/computer-science-technology/"));
        Log.d(TAG, "Menu Dawson Clicked");
        startActivity(dawsonIntent);
    }

    /**
     * Helper method, which handles launching the intent related to the
     * Setting Activity.
     */
    private void handleSetting(){
        Intent settingIntent = new Intent(this, SettingsActivity.class);
        Log.d(TAG, "Menu Settings Clicked");
    }




}

