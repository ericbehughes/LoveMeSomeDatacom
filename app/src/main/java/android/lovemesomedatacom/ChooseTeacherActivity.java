package android.lovemesomedatacom;

import android.lovemesomedatacom.MenuActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.adapters.TeacherAdapter;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class ChooseTeacherActivity extends MenuActivity {

    private static final String TAG = "CHOOSE_TEACHER_ACTIVITY";

    private List<Teacher> teachers;
    private TeacherAdapter myAdapter;
    private ListView teachersListView;




    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_choose_teacher);
    }
}
