package android.lovemesomedatacom;

/**
 * Created by ehugh on 11/24/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by ehugh on 11/24/2017.
 */

public class NotesAdapter extends BaseAdapter {
    private static final String TAG = "NotesAdapter";
    private Context context;
    LayoutInflater inflater;
    ArrayList<Note> notes;


    public NotesAdapter(Activity mainActivity, ArrayList<Note> notes) {

        this.context = mainActivity;
        this.notes = notes;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addAll(ArrayList<Note> list){
        this.notes = list;
    }

    private class ViewHolder {
        TextView tv1;
        TextView tv2;
    }

    @Override
    public View getView(final int position, View v, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.item_note, null);


        holder.tv1 = (TextView) rowView.findViewById(R.id.note_title);
        holder.tv2 = (TextView) rowView.findViewById(R.id.note_text);
        String text = notes.get(position).getText().toString();
        if (text.length() > 40){
            text = text.substring(0, 40) + "...";
        }


        holder.tv1.setText(notes.get(position).getTitle());
        holder.tv2.setText(text);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("inside", "inside on click");

                Intent i = new Intent(context, NoteDetailsActivity.class);
                i.putExtra("noteTitle", notes.get(position).getTitle());
                i.putExtra("noteText", notes.get(position).getText());
                context.startActivity(i);
            }
        });

        return rowView;
    }


}

