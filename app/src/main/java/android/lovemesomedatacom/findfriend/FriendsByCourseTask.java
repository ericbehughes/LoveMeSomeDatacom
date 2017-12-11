package android.lovemesomedatacom.findfriend;

import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * AsyncTask used by FriendsByCourseLisrActivity that retrieves all of
 * the current users friend, who take a particular course. When long clicking
 * a course name in the ClassCancelation, it opens the FriendsByCourseListActivity
 * , which in turns runs this task. The task makes a http get request to the
 * friendfinder api and is responsed with a JSON array of friend objects. The task
 * parses this and returns a list of friends to the ui.
 *
 * @authoer Rhai
 */

public class FriendsByCourseTask extends AsyncTask<String, Void, ArrayList<Friend>> {

    private static final String TAG = "FriendsByCourseTask";
    private FriendsByCourseListActivity activity;
    private String url;

    /**
     * Constructor used to passing the calling activity and specific url make a
     * request to the friendfinder api.
     *
     * @param activity used to update calling activity's list ui
     * @param url
     */
    public FriendsByCourseTask(FriendsByCourseListActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "OnPreExecute invoked");
        super.onPreExecute();

    }

    /**
     * Overriden doInBackground returns a list of friends after
     * retrieved from the api.
     *
     * @param params
     * @return
     */
    @Override
    protected ArrayList<Friend> doInBackground(String... params) {
        try {

            URL url = new URL(this.url);

            return getFriendsWhoAreFree(url);

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Overruden onPostExecute is used to set
     * the calling activity's list ui using its
     * public listFriends method
     *
     * @param list arraylist of friend objects
     */
    @Override
    protected void onPostExecute(ArrayList<Friend> list){
        Log.d(TAG, "OnPostExecute invoked");

        activity.listFriends(list);

    }


    /**
     * getFriendsWhoAreFree method is used to make the http request to
     * the friendfinder api. After the api responds it parse the JSON
     * objects and returns a list of friends. The list returned also
     * depends on the response code, where an ok response returns a
     * regular of friends. The bad request and unauthorized responses
     * return a list with a friend object that represents the error.
     *
     * @param url
     * @return list of friends
     */
    private ArrayList<Friend> getFriendsWhoAreFree(URL url) {
        try {
            // Makes the get request to the api
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();


            int response = connection.getResponseCode();
            // Depending on the response code an appropriate list is returned
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream stream = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                StringBuilder sb = new StringBuilder();

                // gets the return friends from the api
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                // JSONArray used to hold the JSON objects returned from the api
                JSONArray arrayFriends = new JSONArray(sb.toString());

                ArrayList<Friend> friends = makeFriendsList(arrayFriends);

                Log.d(TAG, arrayFriends.toString());
                return friends;
            } else if (response == HttpURLConnection.HTTP_UNAUTHORIZED) {
                InputStream stream = connection.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                // creates a single JSON object since only the error object
                // is returned
                JSONObject error = new JSONObject(sb.toString());

                // Uses a friend object to represent the error so that it can easily be passed
                // back to the calling activity to be represented appropriately
                Friend error401 = new Friend(activity.getString(R.string.error),"401",error.getString("error"));

                ArrayList<Friend> errorF = new ArrayList<>();
                errorF.add(error401);
                return errorF;
            } else if(response == HttpURLConnection.HTTP_BAD_REQUEST){
                InputStream stream = connection.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                JSONObject error = new JSONObject(sb.toString());
                Friend error400 = new Friend(activity.getString(R.string.error),"400",error.getString("error"));

                ArrayList<Friend> errorF= new ArrayList<>();
                errorF.add(error400);
                return errorF;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Method for task threw exception: "+e.getMessage());
            return null;
        }

        return null;
    }

    /**
     * makeFriendsList parses the JSONArray of friends into an
     * ArrayList of friends
     * @param friends
     * @return ArrayList<Friend> list of friends
     */
    private ArrayList<Friend> makeFriendsList(JSONArray friends){
        ArrayList<Friend> friendsList = new ArrayList<>();
        for(int i=0; i< friends.length();i++){
            try {
                // gets each friend as a JSON object from the array
                JSONObject friend = friends.getJSONObject(i);

                // Creates a friend object with the JSON object and adds it to the list
                Friend friendItem = new Friend(friend.getString("firstName"),friend.getString("lastName"),friend.getString("email"));
                friendsList.add(friendItem);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return friendsList;
    }

}
