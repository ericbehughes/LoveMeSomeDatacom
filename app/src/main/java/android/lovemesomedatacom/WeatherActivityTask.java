package android.lovemesomedatacom;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeatherActivityTask extends AsyncTask<ArrayList<Weather>, Void, ArrayList<Weather>> {

    private static final String TAG = "WeatherActivityTask";
    private WeatherActivity activity;
    private String url;
    private XmlPullParserFactory xmlFactoryObject;

    public WeatherActivityTask(WeatherActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(ArrayList<Weather>... list) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(inputStream, null);

            ArrayList<Weather> result = parseXML(myParser);
            inputStream.close();
            return result;

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> result){
        Log.d(TAG, "OnPostExecute invoked");
        for(Weather weather : result){
            Log.d(TAG, weather.temperature + "DEGREE");
        }
        activity.callBackData(result);
    }

    public ArrayList<Weather> parseXML(XmlPullParser myParser) {
        ArrayList<Weather> list = new ArrayList<>();
        Weather current = null;
        int event;

        try{
            event = myParser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT){
                String name = null;
                switch(event){
                    case XmlPullParser.START_TAG:
                        name = myParser.getName();

                        if(name.equals("time")){
                            current = new Weather();
                            list.add(current);
                        } else if (current != null){
                            if(name.equals("temperature")){
                                current.temperature = myParser.nextText();
                            }
                            if(name.equals("pressure")){
                                current.pressure = myParser.nextText();
                            }
                            if(name.equals("humidity")){
                                current.humidity = myParser.nextText();
                            }
                        }

                        break;
                }

                event = myParser.next();
            }
            return list;
        }
        catch(Exception e){
            return null;
        }
    }
}
