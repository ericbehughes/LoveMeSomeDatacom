package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.lovemesomedatacom.FindTeacherActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.entities.Course;
import android.lovemesomedatacom.entities.Friend;
import android.net.Uri;
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

    public FriendAdapter(Activity contextActivity, ArrayList<Friend> friends){
        this.context =  contextActivity;
        this.friends = friends;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if(this.friends != null) {
            return this.friends.size();
        }
        return 0;
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

    public ArrayList<Friend> getList(){
        return new ArrayList<Friend>(friends);
    }

    public void clear(){
        this.friends.clear();
    }

    private class ViewHolder {
        TextView tv1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.friend_list_item,null);

        holder.tv1 = (TextView) rowView.findViewById(R.id.friend);

        holder.tv1.setText(friends.get(position).toString());

        holder.tv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                String[] recipients = {friends.get(position).getEmail()};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.from) + context.getString(R.string.app_name));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }
}
