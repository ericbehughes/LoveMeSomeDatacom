package android.lovemesomedatacom;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rhai on 26/11/2017.
 */

public class GetCancelledClasses extends AsyncTask<String[], Void,Course[]>  {

        private ClassCancelationActivity activity;
        private String url;
        private XmlPullParserFactory xmlFactoryObject;
        private ProgressDialog pDialog;

        public GetCancelledClasses(ClassCancelationActivity activity, String url){
            this.activity = activity;
            this.url = url;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setTitle("Get from xml");
            pDialog.setMessage("Loading");
            pDialog.show();
        }

        @Override
        protected Course[] doInBackground(String[]... strings) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.addRequestProperty("User-Agent","chrome");
                connection.connect();
                InputStream stream = connection.getInputStream();

                xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myParser = xmlFactoryObject.newPullParser();

                myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                myParser.setInput(stream, null);
                Course[] result = parseXML(myParser);
                stream.close();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AsyncTask", "exception");
                return null;
            }
        }

        public Course[] parseXML(XmlPullParser myParser) {

            //int event;
            //String text = null;
            Course[] result = new Course[50];

            try {
                int itemC = 0;
                int eventType = myParser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    //start of entire xml file
                    String name = myParser.getName();

                    if(name != null) {
                        if(name.equals("item") && eventType == XmlPullParser.START_TAG){
                            Boolean inItem = true;
                            Course course = new Course();
                            System.out.println("Start tag " + name);
                            while(inItem) {
                                name = myParser.getName();
                                if (name != null) {
                                    String text="";
                                    if (name.equals("title")) {
                                        text = tagText(myParser);
                                        course.setTitle(text);
                                    } else if (name.equals("description")) {
                                        text = tagText(myParser);
                                        course.setDescription(text);
                                    } else if (name.equals("course")) {
                                        text = tagText(myParser);
                                        course.setCourseName(text);
                                    } else if (name.equals("teacher")) {
                                        text = tagText(myParser);
                                        course.setTeacherName(text);
                                    } else if (name.equals("datecancelled")) {
                                        text = tagText(myParser);
                                        course.setDateCancelled(text);
                                    }
                                }
                                if(eventType == XmlPullParser.END_TAG){
                                    if(name == null){
                                        name = "";
                                    }

                                    if(name.equals("item"))
                                        inItem=false;
                                }
                                eventType = myParser.next();
                            }
                            result[itemC] = course;
                            if(course.getTitle() != null){
                                itemC++;
                            }
                            System.out.println("End tag " + myParser.getName());

                        }
                    }
                    eventType = myParser.next();
                }
                System.out.println("End document");

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return result;
        }

        private String tagText(XmlPullParser parser) throws XmlPullParserException, IOException {
            int event = parser.getEventType();
            String text = "";
            while(event != XmlPullParser.END_TAG){
                if(event == XmlPullParser.START_TAG) {
                    System.out.println("Start tag " + parser.getName());
                }
                else if(event == XmlPullParser.END_TAG) {
                    System.out.println("End tag "+parser.getName());
                }
                else if(event == XmlPullParser.TEXT) {
                    System.out.println("Text(tagText) "+parser.getText());
                    text = parser.getText().trim();
                }
                event = parser.next();
            }
            return text;
        }

        @Override
        protected void onPostExecute(Course[] result){
            //call back data to main thread
            pDialog.dismiss();
            new ClassCancelationModel(result);
            //activity.callBackData(result);
        }
}
