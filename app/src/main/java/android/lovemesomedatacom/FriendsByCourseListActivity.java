package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.adapters.FriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * FriendsByCourseListActivity is used to display users friends on take a particular
 * course. Activity uses an async task that gets list friends through an api call to
 * friendfinder.
 *
 * @author Rhai
 */

public class FriendsByCourseListActivity extends MenuActivity {

    private static final String TAG = "FriendsByCourseList";

    private ListView courseFriendsListView;
    private FriendAdapter friendAdapter;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_friends_by_course_list);
        this.courseFriendsListView = findViewById(R.id.whosFreeList);

        // checks if activity state is saved
        // If it is repopulat list with instance saved. If not make call to async task
        if(savedInstance != null){

            ArrayList<Friend> friends = savedInstance.getParcelableArrayList("State_List");
            listFriends(friends);
        }else {
            this.intent = getIntent();
            String query = this.intent.getStringExtra("query");

            new FriendsByCourseTask(FriendsByCourseListActivity.this, query).execute();
        }
    }

    /**
     * Overriden onSaveInstanceState saves the state of the listview
      * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        if(this.friendAdapter != null) {
            Log.d(TAG, "Saving list state");
            outState.putParcelableArrayList("State_List", friendAdapter.getList());
        }

    }

    /**
     * listFriends is used by the async task to call back to the main thread
     * and set ListView.
     *
     * @param friends list of friends
     */
    public void listFriends(ArrayList<Friend> friends){
        if(friends != null) {
            //since response errors are returned as a Friend object
            // check so that alert with message can be shown
            if(friends.get(0).getFirstName().equals("Error")){
                showAlert(friends.get(0).getFirstName() + " " + friends.get(0).getLastName(),
                        friends.get(0).getEmail());
            }else if(friends.size() == 0){
                showAlert(getString(R.string.no_avail),getString(R.string.friend_busy));
            }else {
                this.courseFriendsListView.setAdapter(friendAdapter);
                friendAdapter.addAll(friends);
                friendAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * showAlert displays an alert with the appropriate message and title
     *
     * @param title
     * @param msg
     */
    private void showAlert(String title,String msg){
        Log.d(TAG,"Displaying alert");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg)
                .setTitle(title);

        builder.setPositiveButton(R.string.ok,null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
