package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.lovemesomedatacom.FindTeacherActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.entities.Course;
import android.lovemesomedatacom.entities.Friend;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rhai on 09/12/2017.
 */

public class FriendAdapter extends BaseAdapter{


    private static final String TAG = "FriendAdapter";
    private Context context;
    LayoutInflater inflater;
    ArrayList<Friend> friends;

    public FriendAdapter(Activity whosfreeListActivity, ArrayList<Friend> friends){
        this.context = whosfreeListActivity;
        this.friends = friends;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return this.friends.size();
    }

    @Override
    public Object getItem(int i) {
        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addAll(ArrayList<Friend> list){
        this.friends = list;
    }
    public void clear(){
        this.friends.clear();
    }

    private class ViewHolder {
        TextView tv1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        FriendAdapter.ViewHolder holder = new FriendAdapter.ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_whos_free_list,null);

        holder.tv1 = (TextView) rowView.findViewById(R.id.friend);


        return rowView;
    }
}
