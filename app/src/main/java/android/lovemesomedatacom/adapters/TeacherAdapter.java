package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.lovemesomedatacom.entities.Teacher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import android.lovemesomedatacom.R;


/**
 * The TeacherAdapter class is a custom adapter that facilitates adapting a list of teachers to a
 * particular layout consisting of ListView. It overrides the getView method to display the
 * Teacher object full name in the TextView.
 *
 * @author Sebastian Ramirez
 */
public class TeacherAdapter extends ArrayAdapter<Teacher> {
    private Activity context;
    private int resource;
    private List<Teacher> teachers;

    /**
     * Non-default constructor that takes the context, resource and a list of teachers and passes it
     * to the super constructor needed to get all the functionality from the ArrayAdapter class.
     *
     * @param context the callin activity
     * @param resource the layout
     * @param teachers a list of teachers
     */
    public TeacherAdapter(Activity context, int resource, List<Teacher> teachers) {
        super(context, resource, teachers);
        this.context = context;
        this.resource = resource;
        this.teachers = teachers;
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

        TextView tvName = v.findViewById(R.id.teacher);
        tvName.setText(teachers.get(position).getFull_name());

        return v;
    }

}
