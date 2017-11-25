package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.lovemesomedatacom.TeacherContactActivity;
import android.lovemesomedatacom.entities.Teacher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.HashSet;
import java.util.List;

import android.lovemesomedatacom.R;


public class TeacherAdapter extends ArrayAdapter<Teacher>
{
    private Activity context;
    private int resource;
    private List<Teacher> teachers;
    private StorageReference mStorageRef;

    public TeacherAdapter(Activity context, int resource, List<Teacher> teachers)
    {
        super(context, resource, teachers);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);

        TextView tvName = (TextView) v.findViewById(R.id.teacher);

//        Log.d("CUSTOMADAPTER", "showImage: " + listImage.get(position).getImage());

        return v;
    }

}
