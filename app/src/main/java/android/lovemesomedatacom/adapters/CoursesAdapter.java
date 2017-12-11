package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.lovemesomedatacom.FindTeacherActivity;
import android.lovemesomedatacom.SharedPreferencesKey;
import android.lovemesomedatacom.FriendsByCourseListActivity;
import android.lovemesomedatacom.entities.Course;
import android.lovemesomedatacom.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The CourseAdapter is a custom adapter that adapts a list of courses to a
 * particular layout consisting of ListView. It overrides the getView method to display the
 * Course object using two TextView. One to show the course name and the for the teacher.
 *
 * @author Rhai
 */

public class CoursesAdapter extends BaseAdapter {

    // Stores instance of calling activity
    private Context context;
    private SharedPreferences prefs;
    LayoutInflater inflater;
    ArrayList<Course> courses;

    /**
     * Constructor that takes the contextActivity and a list of courses
     * used to populate the list the context.
     *
     * @param classCancelActivity calling activity
     * @param courses list of courses
     */
    public CoursesAdapter(Activity classCancelActivity, ArrayList<Course> courses){
        this.context = classCancelActivity;
        this.courses = courses;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    /**
     * getCount returns number of items in list
     * @return
     */
    @Override
    public int getCount() {
        return this.courses.size();
    }

    /**
     * getItem returns list item at given position
     * @param i position in the list
     * @return
     */
    @Override
    public Object getItem(int i) {
        return courses.get(i);
    }

    /**
     * getItemId returns the id of the item at position
     *
     * @param i position in list
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Sets the internal list of courses with a give one
     * @param list list of courses
     */
    public void addAll(ArrayList<Course> list){
        this.courses = list;
    }


    /**
     * getList returns the list of course as a new list
     * @return ArrayList<Course> list of courses
     */
    public ArrayList<Course> getList(){
        return new ArrayList<Course>(courses);
    }

    /**
     * ViewHolder - internal private class used to store
     * the TextViews in the ListView.
     */
    private class ViewHolder {
        TextView tv1;
        TextView tv2;
    }

    /**
     * Overriden getView returns a View that displays the data at a given position
     * @param position
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        CoursesAdapter.ViewHolder holder = new CoursesAdapter.ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.class_cancelation_list_item, null);


        holder.tv1 = (TextView) rowView.findViewById(R.id.course_name);
        holder.tv2 = (TextView) rowView.findViewById(R.id.teacher_name);


        holder.tv1.setText(courses.get(position).getCourseName());
        holder.tv2.setText(courses.get(position).getTeacherName());

        //Sets on LongClick listener on textview1 containing course name
        // When course name is longclicked it shows a list of friends
        // with that course.
        holder.tv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("COURSESADAPTER","inside on long click");
                prefs = context.getSharedPreferences(SharedPreferencesKey.MAIN_APP.toString(), Context.MODE_PRIVATE);
                if(prefs != null) {

                    // email and password credentials of current user
                    String email = prefs.getString(SharedPreferencesKey.EMAIL_ADDRESS.toString(), "");
                    String password = prefs.getString(SharedPreferencesKey.PASSWORD.toString(), "");
                    String url = "http://friendfinder08.herokuapp.com/api/api/coursefriends?";

                    //course title from the rss contains both the course and section number
                    String title = courses.get(position).getTitle().trim();
                    String courseNum = title.substring(0, title.indexOf(" "));
                    String section = title.substring(title.indexOf(" ") + 1);

                    String friendsForCourseQuery = url + "email=" + email + "&" + "password=" + password +
                            "&coursename=" + courseNum + "&section=" + section;
                    Intent courseFriends = new Intent(context, FriendsByCourseListActivity.class);
                    courseFriends.putExtra("query", friendsForCourseQuery);
                    context.startActivity(courseFriends);
                    return true;
                }
                return false;
            }
        });

        //Sets onclick on textview that displays teacher name

        holder.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("inside", "inside on click");

                // When teacher name is clicked it launches FindTeacherActivity
                Intent teacherContact = new Intent(context, FindTeacherActivity.class);
                teacherContact.putExtra("teacher", courses.get(position).getTeacherName());
                context.startActivity(teacherContact);
            }
        });

        return rowView;
    }


}
