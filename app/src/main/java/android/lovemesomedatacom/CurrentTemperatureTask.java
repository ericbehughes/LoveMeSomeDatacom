package android.lovemesomedatacom;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentTemperatureTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "CurrentTemperatureTask";
    private MainActivity activity;
    private String url;

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "OnPreExecute invoked");
        super.onPreExecute();
    }

    /**
     * Two parameter constructor
     * @param activity
     * @param url
     */
    public CurrentTemperatureTask(MainActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    /**
     * This method is responsible for retrieving the current temperature
     * @param params
     * @return result
     */
    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL(this.url);
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
            Log.d(TAG, obj.toString());
            String result = String.valueOf(obj.getJSONObject("main").get("temp"));
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is responsible for informing the UI about
     * the updates
     * @param result
     */
    @Override
    protected void onPostExecute(String result){
        Log.d(TAG, "OnPostExecute invoked");
        activity.updateCurrentTemperature(result);
    }
}
