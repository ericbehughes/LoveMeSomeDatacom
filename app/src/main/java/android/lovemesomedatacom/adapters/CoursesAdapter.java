package android.lovemesomedatacom.adapters;

import android.app.Activity;
import android.content.Context;
import android.lovemesomedatacom.Course;
import android.lovemesomedatacom.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 1513733 on 11/27/2017.
 */

public class CoursesAdapter extends BaseAdapter {

    private static final String TAG = "CoursesAdapter";
    private Context context;
    LayoutInflater inflater;
    ArrayList<Course> courses;

    public CoursesAdapter(Activity classCancelActivity, ArrayList<Course> courses){
        this.context = classCancelActivity;
        this.courses = courses;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return this.courses.size();
    }

    @Override
    public Object getItem(int i) {
        return courses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addAll(ArrayList<Course> list){
        this.courses = list;
    }
    public void clear(){
        this.courses.clear();
    }

    private class ViewHolder {
        TextView tv1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        CoursesAdapter.ViewHolder holder = new CoursesAdapter.ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.class_cancelation_list_item, null);


        holder.tv1 = (TextView) rowView.findViewById(R.id.course_name);

        holder.tv1.setText(courses.get(position).toString());

//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.d("inside", "inside on click");
//
//                Intent i = new Intent(context, ClassCancelationDetailFragment.class);
//                i.putExtra("course", courses.get(position).toString());
//                i.putExtra("courseDetails", courses.get(position).cancellationDetails());
//                context.startActivity(i);
//            }
//        });

        return rowView;
    }
}
