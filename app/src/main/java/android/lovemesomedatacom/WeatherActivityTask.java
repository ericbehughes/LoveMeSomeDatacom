package android.lovemesomedatacom;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivityTask extends AsyncTask<String[], Void, String[]> {

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
    protected String[] doInBackground(String[]... strings) {
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

            String[] result = parseXML(myParser);
            inputStream.close();
            return result;

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String[] result){
        activity.callBackData(result);
    }

    public String[] parseXML(XmlPullParser myParser) {
        String text = null;
        int event;
        String[] result = new String[10];

        try{
            event = myParser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT){

                switch(event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        String name = myParser.getName();
                        if(name.equals("temperature")){
                            result[0] = myParser.getAttributeValue(null,"value");
                        }
                        //Continue for other tags...
                        break;
                }
                event = myParser.next();
            }
            return result;
        }
        catch(Exception e){
            return null;
        }
    }
}
