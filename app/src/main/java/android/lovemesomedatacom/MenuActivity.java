package android.lovemesomedatacom;



        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ListView;
        import android.widget.Toast;
        import android.content.SharedPreferences;

//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;


/**
 * Created by pepe on 11/4/2017.
 */

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "Menu";
    private static final int MENU_ABOUT = 0;

    private SharedPreferences prefs;

    int index;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // public abstract MenuItem add (int groupId, int itemId, int order, CharSequence title)
//        menu.ad aboutMenuItem = menu.addSubMenu(R.string.menu_item_about);
        menu.add(Menu.NONE, MENU_ABOUT, Menu.NONE, R.string.menu_item_about);

        return super.onCreateOptionsMenu(menu);
        //prefs = getSharedPreferences(SharedPreferencesKey.MAINAPP.toString(), Context.MODE_PRIVATE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENU_ABOUT:

                Intent settingsIntent = new Intent(this, SettingsActivity.class);

                Log.d(TAG, "Setttings click");
                startActivity(settingsIntent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }




}

