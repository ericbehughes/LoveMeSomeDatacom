package android.lovemesomedatacom.business;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1513733 on 11/24/2017.
 */

public class XMLParser {
    private static final String ns = null;
    private static final String TAG = "TESTPARSER";

    public static List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the channel tag
                Log.d(TAG, name);

                switch (event) {

                    case XmlPullParser.TEXT:
                        Log.d(TAG, parser.getText());
                        break;
                }

                if (parser.getName().equals("item")) {
                    Log.d(TAG, parser.toString());
                } else {
                    skip(parser);
                }
                event = parser.next();
                //Log.d(TAG, "parser.getAttributes" + parser.getAttributeValue(null, "value"));
                Log.d(TAG, "parser.Next() " + event);
            }

            //return readFeed(parser);

        } finally {
            in.close();
            return null;
        }

    }


    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


    private static List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List classes = new ArrayList();

        //parser.require(XmlPullParser.START_TAG, ns,"channel");

        return classes;
    }
}
