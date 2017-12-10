package android.lovemesomedatacom;

import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.util.Log;
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

    }


    public List<Friend> getFriendsWhoAreFree(URL url) {
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

            JSONObject obj = new JSONObject(sb.toString());


            String friends = obj.toString();
            Log.d(TAG, friends);
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
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

