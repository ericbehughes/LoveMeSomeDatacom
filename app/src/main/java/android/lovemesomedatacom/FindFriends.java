package android.lovemesomedatacom;

import android.content.SharedPreferences;
import android.lovemesomedatacom.adapters.FindFriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sebastian Ramirez
 */

public class FindFriends extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private ConnectivityInfo cInfo;
    private List<Friend> allFriends;
    private ListView friendListView;

    private HttpURLConnection conn;
    private InputStream inputStream;
    private String email;
    private String password;
    private String URL;

    @Override
    protected void onCreate(Bundle instanceBundle) {
        super.onCreate(instanceBundle);
        setContentView(R.layout.activity_find_friends);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //could not retrieve the shared prefs, had to hardcode an email and a password
        this.email = preferences.getString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), "seb@example.com");
        this.password = preferences.getString(SharedPreferencesKey.PASSWORD.toString(), "123lol123");
        if (this.email != null && this.password != null) {
            this.URL = "https://friendfinder08.herokuapp.com/api/api/allfriends?email=" + email + "&password=" + password;
        }
        Log.d(TAG, URL);
        this.friendListView = findViewById(R.id.friendList);
        this.cInfo = new ConnectivityInfo(this);
        this.allFriends = new ArrayList<>();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getData() {
        if (cInfo.isOnline()) {
            Log.d(TAG, "IS_ONLINE");
            new GetAPIResponse().execute(URL);
        } else {
            Log.d(TAG, "IS_OFFLINE");
            Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();
        }
    }

    private class GetAPIResponse extends AsyncTask<String, Void, List<Friend>> {

        @Override
        protected List<Friend> doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Friend> result) {
            super.onPostExecute(result);
            allFriends = result;
            for (Friend f : allFriends) {
                Log.d(TAG, f.getFirstName());
            }
            FindFriendAdapter friendAdapter = new FindFriendAdapter(FindFriends.this, R.layout.friends_list, allFriends);
            friendListView.setAdapter(friendAdapter);
        }
    }

    private List<Friend> downloadUrl(String myurl) throws IOException {

        inputStream = null;

        try {
            URL url = new URL(myurl);
            this.conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.connect();
            inputStream = conn.getInputStream();
            return readJsonStream(inputStream);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignore) {
                }
                if (conn != null)
                    try {
                        conn.disconnect();
                    } catch (IllegalStateException ignore) {
                    }
            }
        }

    }

    private List<Friend> readJsonStream(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"))) {
            return readFriendsArray(reader);
        }
    }

    private List<Friend> readFriendsArray(JsonReader reader) throws IOException {
        List<Friend> allFriends = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            allFriends.add(readFriend(reader));
        }
        reader.endArray();
        return allFriends;
    }

    private Friend readFriend(JsonReader reader) throws IOException {
        String firstName = null;
        String lastName = null;
        String email = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "firstName":
                    firstName = reader.nextString();
                    break;
                case "lastName":
                    lastName = reader.nextString();
                    break;
                case "email":
                    email = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        Log.d(TAG, "FIRST_NAME: " + firstName);
        Log.d(TAG, "LAST_NAME: " + lastName);
        return new Friend(firstName, lastName, email);
    }


}
