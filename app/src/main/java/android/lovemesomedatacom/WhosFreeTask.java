package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WhosFreeTask extends AsyncTask<String, Void, ArrayList<Friend>> {

    private static final String TAG = "WhosFreeTask";
    private WhosFreeListActivity activity;
    private String url;

    public WhosFreeTask(WhosFreeListActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "OnPreExecute invoked");
        super.onPreExecute();

    }

    @Override
    protected ArrayList<Friend> doInBackground(String... params) {
        try {

            URL url = new URL(this.url);

            getFriendsWhoAreFree(url);

            return null;

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Friend> list){
        Log.d(TAG, "OnPostExecute invoked");

        activity.listOfFriends(list);

    }


    public ArrayList<Friend> getFriendsWhoAreFree(URL url) {
        try{

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            JSONArray arrayFriends = new JSONArray(sb.toString());
            ArrayList<Friend> friends = makeFriendsList(arrayFriends);

            Log.d(TAG, arrayFriends.toString());
            return friends;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Friend> makeFriendsList(JSONArray friends){
        ArrayList<Friend> friendsList = new ArrayList<>();
        for(int i=0; i< friends.length();i++){
            try {
                JSONObject friend = friends.getJSONObject(i);
                if(friend.has("error")){
                    Friend friendItem = new Friend("Error",friend.getString("error"),"");
                    friendsList.add(friendItem);
                    return friendsList;
                }
                Friend friendItem = new Friend(friend.getString("firstName"),friend.getString("lastName"),friend.getString("email"));
                friendsList.add(friendItem);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return friendsList;
    }


    private String encodeURIComponent(String s) {
        String result;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

}

