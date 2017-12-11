package android.lovemesomedatacom.classcancelation;

import android.lovemesomedatacom.entities.Course;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Asynctask used to get cancelled classes from
 * https://www.dawsoncollege.qc.ca/registrar/online-services/class-cancellations/
 * and update the calling activity's list ui.
 *
 * @author Rhai
 */

public class GetCancelledClasses extends AsyncTask<String, Integer,ArrayList<Course>>  {

    private ClassCancellationsFragment activity;
    private String url;
    private XmlPullParserFactory xmlFactoryObject;

    /**
     * Construct that takes the calling activity and url used to
     * make a connection
     * @param activity calling activity later used to make changes its
     *                 ui
     * @param url location where data is retrieved from
     */
    public GetCancelledClasses(ClassCancellationsFragment activity, String url){
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    /**
     * Overriden doInBackground makes a connection to the given url.
     * Parses the xml repsonse from the site and returns a list of
     * courses.
     *
     * @param strings
     * @return ArrayList<cCourse> list of courses
     */
    @Override
    protected ArrayList<Course> doInBackground(String... strings) {
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

            //XML parser object used to parse the xml retrieved from the site
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(stream, null);
            ArrayList<Course> result = parseXML(myParser);
            stream.close();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncTask", "exception");
            return null;
        }
    }

    /**
     * parseXML parses the xml retrieved from the site and returns
     * a list of course objects.
     *
     * @param myParser
     * @return
     */
    public ArrayList<Course> parseXML(XmlPullParser myParser) {

        ArrayList<Course> result = new ArrayList<>();

        try {
            int eventType = myParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //start of entire xml file
                String name = myParser.getName();

                //checks tag name
                if(name != null) {
                    //if the tag is an item it goes into the tag
                    if(name.equals("item") && eventType == XmlPullParser.START_TAG){
                        Boolean inItem = true;
                        Course course = new Course();
                        System.out.println("Start tag " + name);

                        // Loop used to create a single course object from each item tag
                        // inItem true means that the parse is still within the current item tag
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

                            // checks if parser is at end tag
                            if(eventType == XmlPullParser.END_TAG){
                                // changes to an empty string
                                if(name == null){
                                    name = "";
                                }

                                // checks if the end tag is for item
                                if(name.equals("item"))
                                    inItem=false;
                            }
                            eventType = myParser.next();
                        }
                        result.add(course);
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

        try{
            Thread.sleep(5000);
        } catch(InterruptedException ie){
            System.out.println(ie.getMessage());
        }

        return result;
    }

    /**
     * tagText used to retrieve the text from an xml tag
     *
     * @param parser
     * @return String text with the current tag
     * @throws XmlPullParserException
     * @throws IOException
     */
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

    /**
     * Override onPostExecute updates the calling activity's
     * ui with the resulting list. This is done through the
     * activity's populateCancelledCourses.
     * @param result
     */
    @Override
    protected void onPostExecute(ArrayList<Course> result){
        System.out.println("Course result size: "+result.size());
        //call back data to main thread to update its ui
        activity.populateCancelledCourses(result);
    }
}