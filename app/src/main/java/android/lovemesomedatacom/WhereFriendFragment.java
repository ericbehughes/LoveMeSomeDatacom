package android.lovemesomedatacom;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

public class WhereFriendFragment extends Fragment implements AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_where_friend, container, false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Dialog dialog = new Dialog(this.getContext());
        // Include dialog.xml file
        dialog.setContentView(R.layout.fragment_where_friend); // layout of your dialog
        // Set dialog title
        dialog.setTitle("Where is");

        // set values for custom dialog components - text, image and button
        // similar add statements for other details
        dialog.show();
    }
}