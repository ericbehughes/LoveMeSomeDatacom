package android.lovemesomedatacom.findfriend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.lovemesomedatacom.business.ConnectivityInfo;
import android.lovemesomedatacom.business.MenuActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.business.SharedPreferencesKey;
import android.lovemesomedatacom.adapters.FindFriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Sebastian Ramirez
 */

public class FindFriendsActivity extends MenuActivity {
    private final String TAG = this.getClass().getSimpleName();

    private ConnectivityInfo cInfo;
    private List<Friend> allFriends;
    private ListView friendListView;

    private HttpURLConnection conn;
    private InputStream inputStream;
    private String email;
    private String password;
    private String URL, whereIs, isIn, section, rightNow;

    private SharedPreferences prefs;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle instanceBundle) {
        super.onCreate(instanceBundle);
        setContentView(R.layout.activity_find_friends);
        prefs = getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);
        this.email = prefs.getString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), "");
        Log.d(TAG, "EMAIL: " + email);
        this.password = prefs.getString(SharedPreferencesKey.PASSWORD.toString(), "");
        Log.d(TAG, "PASSWORD: " + password);
        if (this.email != null && this.password != null) {
            this.URL = "https://friendfinder08.herokuapp.com/api/api/allfriends?email=" + email + "&password=" + password;
        }
        Log.d(TAG, URL);
        whereIs = getResources().getString(R.string.where_friend);
        isIn = getResources().getString(R.string.is_in);
        section = getResources().getString(R.string.section);
        rightNow = getResources().getString(R.string.right_now);
        this.friendListView = findViewById(R.id.friendList);
        this.cInfo = new ConnectivityInfo(this);
        this.allFriends = new ArrayList<>();
        getData();
    }

    private void getData() {
        if (cInfo.isOnline()) {
            Log.d(TAG, "IS_ONLINE");
            new FindAllFriendsTask().execute(URL);
        } else {
            Log.d(TAG, "IS_OFFLINE");
            Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * The fireWheereFriendFragment method makes sure to create an appropriate intent to each
     * inflated ListView according to the appropiate teacher. When a ListView is clicked in the UI
     * it will retrive the specific teacher and fire the TeacherContactActivity.
     */
    public AdapterView.OnItemClickListener fireWhereFriendFragment = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Friend friend;
            for (int i = 0; i < allFriends.size(); i++) {
                if (position == i) {
                    Calendar calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    int hour = calendar.get(Calendar.HOUR_OF_DAY) * 100;
                    int minutes = calendar.get(Calendar.MINUTE);
                    int time = hour + minutes;
                    friend = allFriends.get(i);
                    String url = "https://friendfinder08.herokuapp.com/api/api/whereisfriend?" +
                            "email=" + email + "&password=123lol123&friendemail=" + friend.getEmail() + "&day=" + dayOfWeek + "&time=" + time;
                    Log.d(TAG, "WHERE_URL: " + url);
                    new WhereIsFriendTask().execute(url, friend.getFirstName());
                }
            }
        }
    };

    //Start of FindFriends related methods and AsyncTask class stub
    private class FindAllFriendsTask extends AsyncTask<String, Void, List<Friend>> {

        @Override
        protected List<Friend> doInBackground(String... urls) {
            try {
                return downloadUrlFriends(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Friend> result) {
            super.onPostExecute(result);
            allFriends = result;
            FindFriendAdapter friendAdapter = new FindFriendAdapter(FindFriendsActivity.this, R.layout.friends_list, allFriends);
            friendListView.setAdapter(friendAdapter);
            friendListView.setOnItemClickListener(fireWhereFriendFragment);
        }
    }

    private List<Friend> downloadUrlFriends(String myUrl) throws IOException {

        inputStream = null;

        try {
            URL url = new URL(myUrl);
            this.conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            if (response != HttpURLConnection.HTTP_OK) {
                return new ArrayList<>();
            } else {
                inputStream = conn.getInputStream();
                return readJsonStreamFriends(inputStream);
            }

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

    private List<Friend> readJsonStreamFriends(InputStream in) throws IOException {
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
//        Log.d(TAG, "FIRST_NAME: " + firstName);
//        Log.d(TAG, "LAST_NAME: " + lastName);
        return new Friend(firstName, lastName, email);
    }

    private class WhereIsFriendTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... urls) {
            try {
                Log.d(TAG, urls[0]);
                Log.d(TAG, urls[1]);
                String[] toReturn = downloadUrlWhere(urls[0]);
                if (toReturn.length == 0) {
                    return toReturn;
                } else {
                    toReturn[2] = urls[1];
                    return toReturn;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            String message = "";
            builder = new AlertDialog.Builder(FindFriendsActivity.this);
            Log.d(TAG, "RESULT_LENGTH: " + result.length);
            if (result.length != 0) {
                if (result[0].isEmpty()) {
                    message = "Unknown whereabouts!";
                } else {
                    message = result[2] + " " + isIn + " " + result[0] + " " + section + " " + result[1] + " " + rightNow;
                }
                builder.setTitle(whereIs + " " + result[2])
                        .setMessage(message)
                        .setNeutralButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(FindFriendsActivity.this, R.string.no_friends, Toast.LENGTH_LONG).show();
            }
        }
    }

    private String[] downloadUrlWhere(String myUrl) throws IOException {

        inputStream = null;

        try {
            URL url = new URL(myUrl);
            this.conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "SERVER_RESPONSE: " + response);
            if (response != HttpURLConnection.HTTP_OK) {
                return new String[0];
            } else {
                inputStream = conn.getInputStream();
                return readJsonStreamWhere(inputStream);
            }


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

    private String[] readJsonStreamWhere(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"))) {
            return readArrayWhere(reader);
        }
    }

    private String[] readArrayWhere(JsonReader reader) throws IOException {
        String[] where = new String[3];
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "course":
                    where[0] = reader.nextString();
                    break;
                case "section":
                    where[1] = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return where;
    }


}
