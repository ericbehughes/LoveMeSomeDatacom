package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.lovemesomedatacom.R;
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
 * The FriendAdapter is a custom adapter that adapts a list of friends to a
 * particular layout consisting of ListView. It overrides the getView method to display the
 * Friend object using the Friend toStirng() to populate the TextView.
 *
 * @author Rhai
 */

public class FriendAdapter extends BaseAdapter{


    // Stores instance of calling activity
    private Context context;
    LayoutInflater inflater;
    ArrayList<Friend> friends;

    /**
     * Constructor that takes the contextActivity and a list of friends
     * used to populate the list the context.
     *
     * @param contextActivity calling activity
     * @param friends list of friends
     */
    public FriendAdapter(Activity contextActivity, ArrayList<Friend> friends){
        this.context =  contextActivity;
        this.friends = friends;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * getCount returns number of items in list
     * @return
     */
    @Override
    public int getCount() {
        if(this.friends != null) {
            return this.friends.size();
        }
        return 0;
    }

    /**
     * getItem returns list item at given position
     * @param i position in the list
     * @return
     */
    @Override
    public Object getItem(int i) {
        return friends.get(i);
    }

    /**
     * getItemId returns the id of the item at position
     *
     * @param i position in list
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Sets the internal list of friends with a give one
     * @param list list of friends
     */
    public void addAll(ArrayList<Friend> list){
        this.friends = list;
    }

    /**
     * getList returns the list of friends as a new list
     * @return ArrayList<Friend> list of friends
     */
    public ArrayList<Friend> getList(){
        return new ArrayList<Friend>(friends);
    }

    /**
     * ViewHolder - internal private class used to store
     * the TextViews in the ListView.
     */
    private class ViewHolder {
        TextView tv1;
    }

    /**
     * Overriden getView returns a View that displays the data at a given position
     * @param position
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.friend_list_item,null);

        holder.tv1 = (TextView) rowView.findViewById(R.id.friend);

        holder.tv1.setText(friends.get(position).toString());

        // Sets click listen on a friend item
        // Listen opens a email when a friend is clicked
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
