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
 * The FindFriendsActivity is responsible for showing the user, defined by its email and password,
 * a list of friends collected from an API call to our heroku website at https://friendfinder08.herokuapp.com.
 * It will display a list of friends if the user has register any friend on the website, or nothing
 * if no friends are found. Each friend will be displayed in a TextView that when clicked, will
 * notify the user where a particular friend is. If it is Saturday/Sunday or the hour is not between
 * the hours specified by the API, a Toast will show up indicating that the Whereabouts service is not
 * available.
 *
 * @author Sebastian Ramirez
 */

public class FindFriendsActivity extends MenuActivity {
    //TAG
    private final String TAG = this.getClass().getSimpleName();
    //user's friends
    private List<Friend> allFriends;

    private ListView friendListView;
    //private fields to deal with http request
    private ConnectivityInfo cInfo;
    private HttpURLConnection conn;
    private InputStream inputStream;

    //strings for url and displaying alert
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

    /**
     * The getData method is called right before the onCreate method finishes. It is responsible for
     * grabbing a list of Friend objects returned as JSON from an API call to our heroku website. It
     * checks for connectivity before performing any action. If there is connectvity, it calls the
     * execute method of the FindAllFriendsTask AsyncTask to run in the background. If there is no
     * connectivity, an appropriate message is shown.
     *
     */
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
     * The fireWheereFriendFragment method makes sure to create an appropriate AlertDialog to each
     * inflated TextView according to the appropiate friend. When a TextView is clicked in the UI
     * it will retrive the specific friend and show an AlertDialog with the particular friend's
     * whereabouts.
     */
    public AdapterView.OnItemClickListener fireWhereFriendFragment = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (cInfo.isOnline()) {
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
            } else {
                Toast.makeText(FindFriendsActivity.this, R.string.connection_error, Toast.LENGTH_LONG).show();
            }

        }
    };

    //Start of FindFriends related methods and AsyncTask class stub

    /**
     * The FindAllFriendsTask stub class is an AsyncTask that will perform network operations in the
     * background. It is used for making an API call to heroku to find the user's list of friends
     * specified by the email and password saved in the settings.
     */
    private class FindAllFriendsTask extends AsyncTask<String, Void, List<Friend>> {

        /**
         * The overridden doInBackGround method receives a single url and calls the downloadUrlFriends
         * method to grab the JSON response from the API call and convert that response into Friend
         * objects. If an IOException occurs, it returns null.
         *
         * @param urls the call to the api
         * @return a list of Friend objects converted from JSON
         */
        @Override
        protected List<Friend> doInBackground(String... urls) {
            try {
                return downloadUrlFriends(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * The overridden onPostExecute method grabs the result of the doInBackGround method and sets
         * the FindFriendAdapter object with the resulting list of friends. It also sets an onItemClickListener
         * event to every item in the list.
         *
         * @param result The Friend list coming from doInBackGround
         */
        @Override
        protected void onPostExecute(List<Friend> result) {
            super.onPostExecute(result);
            allFriends = result;
            FindFriendAdapter friendAdapter = new FindFriendAdapter(FindFriendsActivity.this, R.layout.friends_list, allFriends);
            friendListView.setAdapter(friendAdapter);
            friendListView.setOnItemClickListener(fireWhereFriendFragment);
        }
    }

    /**
     * The downloadUrlFriends method performs an HTTP GET request with the specified URL. It tries to
     * get a response from the URL. If the HTTP code is not 200, it returns an empty array to denote
     * that something went wrong. If it is 200, it calls the readJsonStream method.
     *
     * @param myUrl the url to perform the api call
     * @return a list containing friends or null if the code was not 200
     * @throws IOException
     */
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

    /**
     * The readJsonStreamFriends uses try with resources to read an array incoming from the web API.
     * It calls the readFriendsArray method to deal with every entry in the array.
     * @param in The InputStream coming in
     * @return  a List of Friend objects from the readFriendsArray method
     * @throws IOException
     */
    private List<Friend> readJsonStreamFriends(InputStream in) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"))) {
            return readFriendsArray(reader);
        }
    }

    /**
     * The readFriendsArray method is responsible for parsing through a JSON array and creating friend
     * objects from each object in the JSON array.
     *
     * @param reader The JsonReader object coming from the readJsonStreamFriends method
     * @return a List of Friend objects
     * @throws IOException
     */
    private List<Friend> readFriendsArray(JsonReader reader) throws IOException {
        List<Friend> allFriends = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            allFriends.add(readFriend(reader));
        }
        reader.endArray();
        return allFriends;
    }

    /**
     * The readFriend method is in charge of reading an instance of a JSON object and recreating a
     * Friend object from it.
     *
     * @param reader the JsonReader object coming from the readFriendsArray method
     * @return a Friend object that is the result of parsing through a JSON object
     * @throws IOException
     */
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


    //Start of WhereIsFriend related methods and AsyncTask class stub

    /**
     * The WhereIsFriendTask stub class is an AsyncTask that will perform network operations in the
     * background. It is used for making an API call to heroku to find whether an specific Friend of the
     * user is currently in class or not.
     */
    private class WhereIsFriendTask extends AsyncTask<String, Void, String[]> {

        /**
         * The overridden doInBackground method takes a string containing the URL for the API call and
         * the name of the particular friend in the TextView that launches this AsyncTask. It return
         * a String array coming from the downloadUrlWhere method. If the array is empty, it will simply
         * return an empty array coming from the method. If the array is not empty, it will attach the
         * Friends name to the String array to be returned for display purposes.
         *
         * @param urls
         * @return
         */
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
        /**
         * The overridden onPostExecute method grabs the result of the doInBackGround method and
         * displays an appropriate AlertDialog according to the information coming from the API
         *
         * @param result The Friend list coming from doInBackGround
         */
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
    /**
     * The downloadUrlFriends method performs an HTTP GET request with the specified URL. It tries to
     * get a response from the URL. If the HTTP code is not 200, it returns an empty array to denote
     * that something went wrong. If it is 200, it calls the readJsonStream method.
     *
     * @param myUrl the url to perform the api call
     * @return a list containing friends or null if the code was not 200
     * @throws IOException
     */
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
