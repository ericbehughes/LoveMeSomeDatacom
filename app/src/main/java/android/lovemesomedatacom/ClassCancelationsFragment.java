package android.lovemesomedatacom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static android.content.ContentValues.TAG;
import static android.lovemesomedatacom.ClassCancelationModel.courses;


public class ClassCancelationsFragment extends Fragment {
    CoursesAdapter coursesAdapter;

    ArrayList<Course> courseList = new ArrayList<>();
    private ListView coursesListView;
    private final static String url = "https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(),"oncreate call",Toast.LENGTH_SHORT).show();
        new GetCancelledClasses(this,url).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        View v = inflater.inflate(R.layout.fragment_class_cancelations, parent, false);
        coursesListView = (ListView) v.findViewById(R.id.lvItems);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ListView lvItems = (ListView) view.findViewById(R.id.lvItems);
        lvItems.setAdapter(coursesAdapter);

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
    }



    //--OnItemSelectedListener listener;
    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Toast.makeText(this, "Called By Fragment A: position - ", Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "ClassCancelationsFragment on Attach", Toast.LENGTH_LONG);
        if (context instanceof OnItemSelectedListener) {      // context instanceof YourActivity
            this.listener = (OnItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ClassCancelationsFragment.OnItemSelectedListener");
        }
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