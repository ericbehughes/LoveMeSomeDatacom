package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.adapters.TeacherAdapter;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

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

public class WhosFreeListActivity extends MenuActivity {

    private static final String TAG = "WhosFreeListActivity";

    //List required by the TeacherAdapter, coming from the FindTeacherActivity
    private List<Teacher> whosFreeList;

    //ListView in activity_choose_teacher.xml
    private ListView whosFreeListView;
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
        setContentView(R.layout.activity_whos_free_list);
        this.whosFreeListView = findViewById(R.id.whosFreeList);
        this.intent = getIntent();
        this.whosFreeList = this.intent.getParcelableArrayListExtra("FRIENDS");
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
        TeacherAdapter teacherAdapter = new TeacherAdapter(this, R.layout.teacher_list, this.whosFreeList);
        this.whosFreeListView.setAdapter(teacherAdapter);
        this.whosFreeListView.setOnItemClickListener(fireEmailFriendWhoIsFree);

    }


    /**
     * The fireEmailFriendWhoIsFree method makes sure to create an appropriate intent to each
     * inflated ListView according to the appropiate teacher. When a ListView is clicked in the UI
     * it will retrive the specific teacher and fire the TeacherContactActivity.
     */
    public AdapterView.OnItemClickListener fireEmailFriendWhoIsFree = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    };
}
