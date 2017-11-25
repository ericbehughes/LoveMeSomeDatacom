package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.MenuActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.adapters.TeacherAdapter;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class ChooseTeacherActivity extends MenuActivity {

    private static final String TAG = "CHOOSE_TEACHER_ACTIVITY";


    private List<Teacher> teacherList;
    private TeacherAdapter myAdapter;
    private ListView teachersListView;
    private Intent intent;




    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_choose_teacher);
        this.teachersListView = findViewById(R.id.teacherList);
        this.intent = getIntent();
        this.teacherList = this.intent.getParcelableArrayListExtra("TEACHERS");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Set<Teacher> teacherSet = new HashSet<>(this.teacherList);
        List<Teacher> unique = new ArrayList<>(teacherSet);
        TeacherAdapter teacherAdapter = new TeacherAdapter(this, R.layout.teacher_list, unique);
        teachersListView.setAdapter(teacherAdapter);
    }
}
