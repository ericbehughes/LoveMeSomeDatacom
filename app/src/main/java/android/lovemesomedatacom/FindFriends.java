package android.lovemesomedatacom;

import android.lovemesomedatacom.entities.Friend;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 12/9/2017.
 */

public class FindFriends extends MenuActivity {

    private ConnectivityInfo cInfo;
    private List<Friend> allFriends;
    private ListView friendListView;

    @Override
    protected void onCreate(Bundle instanceBundle) {
        super.onCreate(instanceBundle);
        setContentView(R.layout.activity_find_friends);
        this.friendListView = findViewById(R.id.friendList);
        this.cInfo = new ConnectivityInfo(this);
        this.allFriends = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }

    private void getData() {
        if (cInfo.isOnline()) {
            new GetAPIResponse();
        } else {
            Toast.makeText(this,R.string.connection_error, Toast.LENGTH_LONG).show();
        }
    }

    public void processJSONResponse(String resp) throws IllegalStateException,
            IOException, JSONException, NoSuchAlgorithmException {

        JSONArray array = new JSONArray(resp);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jasonObj = array.getJSONObject(i);
            allFriends.add(new Friend());
            if (jasonObj.has("firstName")) {
                allFriends.get(i).setFirstName(jasonObj.getString("firstName"));
            }
            if (array.getJSONObject(i).has("lastName")) {
                allFriends.get(i).setLastName(jasonObj.getString("lastName"));
            }
            if (array.getJSONObject(i).has("email")) {
                allFriends.get(i).setEmail(jasonObj.getString("email"));
            }
        }
    }

    private class GetAPIResponse extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
