package android.lovemesomedatacom;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NetworkActivity extends AppCompatActivity {

    private static final String TAG = "NETWORKACTIVITY";
    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static final String URLString = "https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml";

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;
    public static String sPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        loadPage();
    }

    // Uses AsyncTask to download the XML feed from stackoverflow.com.
    public void loadPage() {

//        if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
//            new DownloadXmlTask().execute(URLString);
//        }
//        else if ((sPref.equals(WIFI)) && (wifiConnected)) {
//            new DownloadXmlTask().execute(URLString);
//        } else {
//            // show error
//        }

        new DownloadXmlTask().execute(URLString);
    }

    // Implementation of AsyncTask used to download XML feed from stackoverflow.com.
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return getResources().getString(R.string.xml_error);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG,"NetworkActivity onPost: "+result);
            //setContentView(R.layout.main);
//            // Displays the HTML string in the UI via a WebView
//            WebView myWebView = (WebView) findViewById(R.id.webview);
//            myWebView.loadData(result, "text/html", null);
        }
    }

    // Uploads XML from stackoverflow.com, parses it, and combines it with
// HTML markup. Returns HTML string.
    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        TestXMLParser stackOverflowXmlParser = new TestXMLParser();
        List entries = null;
        String title = null;
        String url = null;
        String summary = null;
        Calendar rightNow = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");

        // Checks whether the user set the preference to include summary text
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean pref = sharedPrefs.getBoolean("summaryPref", false);

        StringBuilder htmlString = new StringBuilder();
        htmlString.append("<h3>" + getResources().getString(R.string.page_title) + "</h3>");
        htmlString.append("<em>" + getResources().getString(R.string.updated) + " " +
                formatter.format(rightNow.getTime()) + "</em>");

        try {
            stream = downloadUrl(urlString);
            entries = TestXMLParser.parse(stream);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        // StackOverflowXmlParser returns a List (called "entries") of Entry objects.
        // Each Entry object represents a single post in the XML feed.
        // This section processes the entries list to combine each entry with HTML markup.
        // Each entry is displayed in the UI as a link that optionally includes
        // a text summary.
//        for (Entry entry : entries) {
//            htmlString.append("<p><a href='");
//            htmlString.append(entry.link);
//            htmlString.append("'>" + entry.title + "</a></p>");
//            // If the user set the preference to include summary text,
//            // adds it to the display.
//            if (pref) {
//                htmlString.append(entry.summary);
//            }
//        }
        return htmlString.toString();
    }

    // Given a string representation of a URLString, sets up a connection and gets
// an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }


}
