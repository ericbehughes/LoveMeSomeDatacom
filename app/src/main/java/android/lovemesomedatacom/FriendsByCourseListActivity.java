package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.adapters.FriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
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

public class FriendsByCourseListActivity extends MenuActivity {

    private static final String TAG = "FriendsByCourseListActivity";

    
    private List<Teacher> whosFreeList;


    private ListView whosFreeListView;
    private FriendAdapter friendAdapter;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_friends_by_course_list);
        this.whosFreeListView = findViewById(R.id.whosFreeList);
        this.intent = getIntent();
        String query = this.intent.getStringExtra("query");

        new FriendsByCourseTask(FriendsByCourseListActivity.this, query).execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //this.whosFreeListView.setOnItemClickListener(fireEmailFriendWhoIsFree);

    }

    public AdapterView.OnItemClickListener fireEmailFriendWhoIsFree = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    };

    public void listOfFriends(ArrayList<Friend> friends){
        if(friends != null) {
            friendAdapter = new FriendAdapter(this, friends);
            this.whosFreeListView.setAdapter(friendAdapter);
            friendAdapter.addAll(friends);
            friendAdapter.notifyDataSetChanged();
        }
    }
}
