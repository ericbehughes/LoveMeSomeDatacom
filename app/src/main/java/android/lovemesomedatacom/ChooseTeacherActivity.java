package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.adapters.TeacherAdapter;
import android.lovemesomedatacom.entities.Teacher;
import android.lovemesomedatacom.entities.TeacherNameComparator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The ChooseTeacherActivity is responsible for displaying a list of more than one teacher, according
 * to how many teachers were found by the FindTeacherActivity. This activity makes use of the custom
 * adapter TeacherAdapter to inflate a series of ListViews for every teacher passed to it through
 * the putExtra method. This activity is also responsible for adding on click listeners to each list
 * view through the fireEmailFriendWhoIsFree method in order to fire the TeacherContact activity
 * for the appropiate teacher whenever a list view is clicked.
 *
 * @author Sebastian Ramirez
 */

public class ChooseTeacherActivity extends MenuActivity {

    private static final String TAG = "CHOOSE_TEACHER_ACTIVITY";

    //List required by the TeacherAdapter, coming from the FindTeacherActivity
    private List<Teacher> teacherList;
    //List that references teacherList, made unique and sorted.
    private List<Teacher> uniqueTeachers;
    //ListView in activity_choose_teacher.xml
    private ListView teachersListView;
    //Intent from FindTeacherActivity
    private Intent intent;

    /**
     * The onCreate is fired when the Activity is started. It takes a Bundle as parameter in order
     * to preserve data between activities or when the viewport changes. It inflates the specified layout
     * resource and initializes several attributes required by public and private methods that need initialization.
     *
     * @param savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_choose_teacher);
        this.teachersListView = findViewById(R.id.teacherList);
        this.intent = getIntent();
        this.teacherList = this.intent.getParcelableArrayListExtra("TEACHERS");
    }

    /**
     * The overriden onResume method calls the uniqueList method in order to sort and deliver unique
     * results to the TeacherAdapter. It creates a new instance of the TeacherAdapter and passes the
     * appropriate layout and list to it. It sets the TeacherAdapter to the single ListView in this
     * activity's layout and sets an OnItemClickListener to each item in the ListView.
     */
    @Override
    protected void onResume() {
        super.onResume();
        uniqueList();
        TeacherAdapter teacherAdapter = new TeacherAdapter(this, R.layout.teacher_list, this.uniqueTeachers);
        this.teachersListView.setAdapter(teacherAdapter);
        this.teachersListView.setOnItemClickListener(fireTeacherContactActivity);

    }

    /**
     * The uniqueList method makes sure the list of teachers recieved from the intent is unique and
     * ordered. It creates a Set object from the teachers list, this makes sure there are no duplicates.
     * Then it creates a List back from the set, this is necessary because the super method of the
     * adapter does not accept Set objects. Finally, it sorts the teachers list using the custom
     * TeacherNameComparator, which compares two Teacher objects according to their full name fields.
     */
    private void uniqueList() {
        Set<Teacher> teacherSet = new HashSet<>(this.teacherList);
        this.uniqueTeachers = new ArrayList<>(teacherSet);
        Collections.sort(this.uniqueTeachers, new TeacherNameComparator());
    }

    /**
     * The fireTeacherContactActivity method makes sure to create an appropriate intent to each
     * inflated ListView according to the appropiate teacher. When a ListView is clicked in the UI
     * it will retrive the specific teacher and fire the TeacherContactActivity.
     */
    public AdapterView.OnItemClickListener fireTeacherContactActivity = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(ChooseTeacherActivity.this, TeacherContactActivity.class);
            for (int i = 0; i < uniqueTeachers.size(); i++) {
                if (position == i) {
                    intent.putExtra("TEACHER", uniqueTeachers.get(i));
                    startActivity(intent);
                }
            }
        }
    };
}
