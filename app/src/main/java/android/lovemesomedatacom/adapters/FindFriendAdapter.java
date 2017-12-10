package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.content.Context;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.entities.Friend;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sebastian on 12/9/2017.
 */

public class FindFriendAdapter extends ArrayAdapter<Friend> {
    private Activity context;
    private int resource;
    private List<Friend> friends;

    public FindFriendAdapter(@NonNull Activity context, @LayoutRes int resource, List<Friend> friends) {
        super(context, resource, friends);
        this.context = context;
        this.resource = resource;
        this.friends = friends;
    }

    /**
     * The overridden getView method will get a View that displays the data at the specified position in the data set.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View v = inflater.inflate(resource, null);

        TextView tvFirstName = v.findViewById(R.id.firstName);
        tvFirstName.setText(friends.get(position).getFirstName());
        TextView tvLastName = v.findViewById(R.id.lastName);
        tvLastName.setText(friends.get(position).getLastName());
        TextView tvEmail = v.findViewById(R.id.email);
        tvEmail.setText(friends.get(position).getEmail());

        return v;
    }
}
