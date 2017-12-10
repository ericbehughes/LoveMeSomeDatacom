package android.lovemesomedatacom;

import android.content.Context;
import android.lovemesomedatacom.adapters.CoursesAdapter;
import android.lovemesomedatacom.entities.Course;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


public class ClassCancelationsFragment extends Fragment {
    CoursesAdapter coursesAdapter;

    ArrayList<Course> courseList = new ArrayList<>();
    private ListView coursesListView;
    private ProgressBar mProgressBar;
    private final static String url = "https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml";
    private boolean isClassDataDownloaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(),"oncreate call",Toast.LENGTH_SHORT).show();

        if (isClassDataDownloaded == false){
            new GetCancelledClasses(this,url).execute();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        View v = inflater.inflate(R.layout.fragment_class_cancelations, parent, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        if (isClassDataDownloaded){
            mProgressBar.setVisibility(View.GONE);
        }
        else{
            mProgressBar.setVisibility(View.VISIBLE);
        }

        coursesListView = (ListView) v.findViewById(R.id.lvItems);
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
                listener.onClassCancellationSelected(selectedCourse); // (3) Communicate with Activity using Listener
            }
        });
    }

    private OnItemSelectedListener listener;

    public void populateCanceletedCourses(ArrayList<Course> list){
        coursesAdapter = new CoursesAdapter(getActivity(), list);
        coursesListView.setAdapter(coursesAdapter);
        //coursesAdapter.clear();
        coursesAdapter.addAll(list);
        coursesAdapter.notifyDataSetChanged();
        hideProgressBar();
    }



    //--OnItemSelectedListener listener;
    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {      // context instanceof YourActivity
            this.listener = (OnItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ClassCancelationsFragment.OnItemSelectedListener");
        }
    }

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
        // This can be any number of events to be sent to the activity

        void onClassCancellationSelected(Course courseSelected);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getActivity(), "ClassCancelationsFragment onActivityCreated", Toast.LENGTH_LONG);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

    }


}