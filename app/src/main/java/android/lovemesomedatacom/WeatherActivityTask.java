package android.lovemesomedatacom;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class WeatherActivityTask extends AsyncTask<ArrayList<Weather>, Void, ArrayList<Weather>> {

    private static final String TAG = "WeatherActivityTask";
    private WeatherActivity activity;
    private String url, uv;
    private XmlPullParserFactory xmlFactoryObject;
    private double lat, lng;

    /**
     * Two parameter constructor
     * @param activity
     * @param url
     */
    public WeatherActivityTask(WeatherActivity activity, String url){
        this.activity = activity;
        this.url = url;
    }

    /**
     * Overridden onPreExecute method
     */
    @Override
    protected void onPreExecute() {
        Log.d(TAG, "OnPreExecute invoked");
        super.onPreExecute();
    }

    /**
     * This is responsible for making the call to the Open Weather Map API,
     * which returns an XML response that needs to be parsed.
     * @param params
     * @return list
     */
    @Override
    protected ArrayList doInBackground(ArrayList<Weather>... params) {
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

            ArrayList<Weather> list = parseXML(myParser);
            inputStream.close();
            return list;

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Overridden onPostExecute, which is responsible to publish the
     * changes on the UI.
     * @param list
     */
    @Override
    protected void onPostExecute(ArrayList<Weather> list){
        Log.d(TAG, "OnPostExecute invoked");
        activity.displayForecast(list, uv);
    }

    /**
     * Helper parsing method, which takes in an XML formatted text
     * and returns an ArrayList
     * @param myParser
     * @return
     */
    public ArrayList<Weather> parseXML(XmlPullParser myParser) {
        ArrayList<Weather> list = new ArrayList<>();
        Date currentTime = roundToNextHour();
        String[] formattedTime = currentTime.toString().split(" ");
        Log.d(TAG, formattedTime[3]);
        Weather current = new Weather();
        boolean trigger = false;
        int event;

        try{
            event = myParser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT){
                String name = null;
                switch(event){
                    case XmlPullParser.START_TAG:
                        name = myParser.getName();

                        if (trigger){
                            if(name.equals("location")){
                                lat = Double.parseDouble(myParser.getAttributeValue(null, "latitude"));
                                lng = Double.parseDouble(myParser.getAttributeValue(null, "longitude"));
                                uv = getUVIndex("http://api.openweathermap.org/data/2.5/uvi?appid=080b8de151ba3865a7b5e255f448f10f&lat="+lat+"&lon=" + lng);
                                trigger = false;
                            }
                        }

                        if(name.equals("location")){
                            trigger = true;
                        }

                        if(name.equals("time")){
                            String[] formatted = myParser.getAttributeValue(null, "from").split("T");
                            if(formatted[1].equals(formattedTime[3])){
                                current = new Weather();
                                list.add(current);
                                current.setStart(myParser.getAttributeValue(null, "from"));
                                current.setEnd(myParser.getAttributeValue(null, "to"));
                            }
                        } else if (current != null) {
                            if (name.equals("temperature")) {
                                current.setTemperature(myParser.getAttributeValue(null,"value") + " " +
                                        myParser.getAttributeValue(null, "unit"));
                            }
                            if (name.equals("pressure")) {
                                current.setPressure(myParser.getAttributeValue(null,"value") + " " +
                                        myParser.getAttributeValue(null, "unit"));
                            }
                            if (name.equals("humidity")) {
                                current.setHumidity(myParser.getAttributeValue(null,"value") + " " +
                                        myParser.getAttributeValue(null, "unit"));
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

    /**
     * This method is responsible for making the call in order to
     * retrieve the UV index value
     * @param s
     * @return uv
     */
    public String getUVIndex(String s) {
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
            return uv;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Private helper method, in order to convert the date
     * and return the expected format
     * @return c
     */
    private Date roundToNextHour() {
        Calendar c = new GregorianCalendar();
        c.setTime(Calendar.getInstance().getTime());
        int hour = c.get(Calendar.HOUR);
        switch(hour){
            case 1:
            case 2:
            case 3:
                c.set(Calendar.HOUR, 3);
                break;
            case 4:
            case 5:
            case 6:
                c.set(Calendar.HOUR, 6);
                break;
            case 7:
            case 8:
            case 9:
                c.set(Calendar.HOUR, 9);
                break;
            case 10:
            case 11:
            case 12:
                c.set(Calendar.HOUR, 12);
                break;
            case 13:
            case 14:
            case 15:
                c.set(Calendar.HOUR, 15);
                break;
            case 16:
            case 17:
            case 18:
                c.set(Calendar.HOUR, 18);
                break;
            case 19:
            case 20:
            case 21:
                c.set(Calendar.HOUR, 21);
                break;
            case 22:
            case 23:
            case 0:
                c.set(Calendar.HOUR, 0);
                break;
        }
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

}
