package android.lovemesomedatacom;

import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WhosFreeTask extends AsyncTask<ArrayList<Friend>, Void, ArrayList<Friend>> {

    private static final String TAG = "WhosFreeTask";
    private WhosFreeActivity activity;
    private String url;

    public WhosFreeTask(WhosFreeActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "OnPreExecute invoked");
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(ArrayList<Friend>... params) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            //ArrayList<Friend> list = parseXML(myParser);
            getFriendsWhoAreFree("");
            inputStream.close();
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
        activity.openEmailForFriend();
    }


    public List<Friend> getFriendsWhoAreFree(String s) {
        Log.d(TAG, s);
        try{
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
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

            JSONObject obj = new JSONObject(sb.toString());
            String uv = obj.get("value").toString();
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

