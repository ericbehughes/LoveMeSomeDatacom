package android.lovemesomedatacom;

import android.lovemesomedatacom.adapters.FriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 12/9/2017.
 */

public class FindFriends extends MenuActivity {
    private final String TAG = this.getClass().getSimpleName();
    private final String EMAIL = "seb@example.com";
    private final String PASSWORD = "123lol123";

    private ConnectivityInfo cInfo;
    private List<Friend> allFriends;
    private ListView friendListView;

    private HttpURLConnection conn;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle instanceBundle) {
        super.onCreate(instanceBundle);
        setContentView(R.layout.activity_find_friends);
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
            new GetAPIResponse().execute("https://friendfinder08.herokuapp.com/api/api/allfriends?email=" + EMAIL + "&password=" + PASSWORD);
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
        protected void onPostExecute(List<Friend> result){
            super.onPostExecute(result);
            allFriends = result;
            for(Friend f: allFriends){
                Log.d(TAG, f.getFirstName());
            }
            FriendAdapter friendAdapter = new FriendAdapter(FindFriends.this, R.layout.friends_list, allFriends);
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

//            int response = conn.getResponseCode();
//            Log.d(TAG, "Server returned: " + response + " aborting read.");
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
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readFriendsArray(reader);
        } finally {
            reader.close();
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
            if (name.equals("firstName")) {
                firstName = reader.nextString();
            } else if (name.equals("lastName")) {
                lastName = reader.nextString();
            } else if (name.equals("email")) {
                email = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d(TAG, "FIRST_NAME: " + firstName);
        Log.d(TAG, "LAST_NAME: " + lastName);
        return new Friend(firstName, lastName, email);
    }
}
