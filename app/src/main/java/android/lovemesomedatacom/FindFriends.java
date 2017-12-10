package android.lovemesomedatacom;

import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
    private final String PASSWORD = "123lol13";

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
            new GetAPIResponse().execute("https://hostname/api/api/allfriends?email=" + EMAIL + "&password=" + PASSWORD);
        } else {
            Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();
        }
    }

    public void processJSONResponse(String resp) throws IllegalStateException,
            IOException, JSONException, NoSuchAlgorithmException {

        JSONArray array = new JSONArray(resp);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jasonObj = array.getJSONObject(i);
            allFriends.add(new Friend());
            if (jasonObj.has("firstName")) {
                allFriends.get(i).setFirstName(jasonObj.getString("firstName"));
            }
            if (array.getJSONObject(i).has("lastName")) {
                allFriends.get(i).setLastName(jasonObj.getString("lastName"));
            }
            if (array.getJSONObject(i).has("email")) {
                allFriends.get(i).setEmail(jasonObj.getString("email"));
            }
        }
    }

    private class GetAPIResponse extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            try {
                downloadUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void downloadUrl(String myurl) throws IOException {

        inputStream = null;

        try {
            URL url = new URL(myurl);
            this.conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d(TAG, "Server returned: " + response + " aborting read.");
            inputStream = conn.getInputStream();
            String jsonText = inputStream.toString();
            processJSONResponse(jsonText);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
}
