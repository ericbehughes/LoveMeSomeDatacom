package android.lovemesomedatacom;

import android.app.Activity;
import android.content.Context;
import android.lovemesomedatacom.adapters.CoursesAdapter;
import android.lovemesomedatacom.entities.Course;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * ClassCancellationsFragment is fragment used to display the list of cancelled courses
 *
 * @author Rhai
 */
public class ClassCancellationsFragment extends Fragment {
    CoursesAdapter coursesAdapter;

    ArrayList<Course> courseList = new ArrayList<>();
    private ListView coursesListView;
    private ProgressBar mProgressBar;
    private Activity hostActivity;
    // url used to get cancelled courses
    private final static String url = "https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml";

    // used to hide progress bar after task if finished or show the bar
    private boolean isClassDataDownloaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(),"oncreate call",Toast.LENGTH_SHORT).show();
        //Only makes the task call the first time the fragment is launched.
        if (isClassDataDownloaded == false){
            new GetCancelledClasses(this,url).execute();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        View v = inflater.inflate(R.layout.fragment_class_cancelations, parent, false);
        mProgressBar = v.findViewById(R.id.progress_bar);

        if (isClassDataDownloaded){
            mProgressBar.setVisibility(View.GONE);
        }
        else{
            mProgressBar.setVisibility(View.VISIBLE);
        }

        coursesListView = v.findViewById(R.id.lvItems);

        //Used to test list since there are no more cancelled classes in the rss feed (9-12-17)
//        ArrayList<Course> l = new ArrayList<>();
//        Course c = new Course();
//        c.setTeacherName("Bob m");
//        c.setTitle("101-916-DW 1");
//        c.setCourseName("Painting");
//        c.setDescription("dfdf");
//        l.add(c);
//        populateCancelledCourses(l);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ListView lvItems = (ListView) view.findViewById(R.id.lvItems);
        lvItems.setAdapter(coursesAdapter) ;

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course selectedCourse = (Course) coursesAdapter.getItem(position);
                listener.onClassCancellationSelected(selectedCourse);
            }
        });
    }

    private OnItemSelectedListener listener;

    public void populateCancelledCourses(ArrayList<Course> list){
        hideProgressBar();
        if(list.size() == 0){
            showAlert(getString(R.string.no_cancell),getString(R.string.no_cancell_msg));
        }else {
            coursesAdapter = new CoursesAdapter(hostActivity, list);
            coursesListView.setAdapter(coursesAdapter);
            coursesAdapter.addAll(list);
            coursesAdapter.notifyDataSetChanged();
        }

    }



    //--OnItemSelectedListener listener;
    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            this.listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ClassCancellationsFragment.OnItemSelectedListener");
        }
        this.hostActivity = getActivity();
    }

    /**
     * hideProgressBar stops the progress bar after the
     * async task completes its task
     */
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        isClassDataDownloaded = true;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mProgressBar.setVisibility(View.GONE);
    }

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {

        void onClassCancellationSelected(Course courseSelected);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getActivity(), "ClassCancellationsFragment onActivityCreated", Toast.LENGTH_LONG);

        if(savedInstanceState != null){
            // If there is an instane of list in the state, the listview is repopulated
            // with that instance
            ArrayList<Course> courses = savedInstanceState.getParcelableArrayList("State_List");
            populateCancelledCourses(courses);
            isClassDataDownloaded = savedInstanceState.getBoolean("COURSESDOWNLOADED");
        }
    }

    /**
     * Overriden onSaveInstanceState saves the state of the listview and boolean
     * that tells if list is already downloaded
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        if(coursesAdapter != null)
            outState.putParcelableArrayList("State_List",coursesAdapter.getList());
        outState.putBoolean("COURSESDOWNLOADED",isClassDataDownloaded);

    }

    /**
     * showAlert displays an alert with the appropriate message and title
     *
     * @param title
     * @param msg
     */
    private void showAlert(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(hostActivity);

        builder.setMessage(msg)
                .setTitle(title);

        builder.setPositiveButton(R.string.ok, null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }


}