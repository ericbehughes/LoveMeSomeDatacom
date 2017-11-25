package android.lovemesomedatacom.adapters;

import android.app.Activity;
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

import java.util.List;

import android.lovemesomedatacom.R;

/**
 *
 * Custom adapter which will display the list view with an image followed by text.
 *
 * @author Alessandro Ciotola
 * @author Sebastian Ramirez
 * @version 2017/11/04
 *
 */
public class TeacherAdapter extends ArrayAdapter<Teacher>
{
    private Activity context;
    private int resource;
    private List<Teacher> listImage;
    private StorageReference mStorageRef;

    public TeacherAdapter(Activity context, int resource, List<Teacher> objects, StorageReference mStorageRef)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.mStorageRef = mStorageRef;
        listImage = objects;
    }

    /**
     * The getView method will get a View that displays the data at the specified position in the data set.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);

        //TextView tvName = (TextView) v.findViewById(R.id.catNames);
        //ImageView img = (ImageView) v.findViewById(R.id.catImages);

//        tvName.setText(listImage.get(position).getName());
//        Glide.with(context)
//                .using(new FirebaseImageLoader())
//                .load(mStorageRef.child(listImage.get(position).getImage()))
//                .into(img);
//
//        Log.d("CUSTOMADAPTER", "showImage: " + listImage.get(position).getImage());

        return v;
    }

    /**
     * The add element method will add an item to the list for the adapter to contain all items.
     *
     * @param element
     */
    public void addElement(Teacher element)
    {
        Log.d("CUSTOM_ADAPTER", "addElement: " + element.getFull_name());
        listImage.add(element);
    }
}
