package android.lovemesomedatacom;

import android.content.DialogInterface;
import android.content.Intent;
import android.lovemesomedatacom.adapters.FriendAdapter;
import android.lovemesomedatacom.entities.Friend;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class FriendsByCourseListActivity extends MenuActivity {

    private static final String TAG = "FriendsByCourseListActivity";

    
    private List<Teacher> whosFreeList;


    private ListView courseFriendsListView;
    private FriendAdapter friendAdapter;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_friends_by_course_list);
        this.courseFriendsListView = findViewById(R.id.whosFreeList);
        if(savedInstance != null){

            ArrayList<Friend> friends = savedInstance.getParcelableArrayList("State_List");
            listOfFriends(friends);
        }else {
            this.intent = getIntent();
            String query = this.intent.getStringExtra("query");

            new FriendsByCourseTask(FriendsByCourseListActivity.this, query).execute();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //this.courseFriendsListView.setOnItemClickListener(fireEmailFriendWhoIsFree);

    }

    public AdapterView.OnItemClickListener fireEmailFriendWhoIsFree = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(this.friendAdapter != null)
            outState.putParcelableArrayList("State_List",friendAdapter.getList());

    }


    public void listOfFriends(ArrayList<Friend> friends){
        if(friends != null) {
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

    private void showAlert(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg)
                .setTitle(title);

        builder.setPositiveButton(R.string.ok,null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
