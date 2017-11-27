package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class FindTeacherActivity extends AppCompatActivity {

    private static final String TAG = "FIND_TEACHER_ACTIVITY";

    //Firebase
    private DatabaseReference mDatabaseRef;

    //Views
    private SearchView firstNameSearchView;
    private SearchView lastNameSearchView;
    private RadioButton likeRadio;
    private Toast infoToast;
    private Query teacherQuery;
    private List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_teacher);

        this.teacherList = new ArrayList<>();

        this.mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        this.teacherQuery = mDatabaseRef.orderByChild("full_name");


        instantiateViews();

    }

    private void fireActivity() {
        int results = this.teacherList.size();
        Log.d(TAG, "TEACHER_LIST_SIZE: " + results);
        switch (results) {
            case 0:
                this.infoToast = Toast.makeText(this, "No results found!", Toast.LENGTH_LONG);
                this.infoToast.show();
                break;
            case 1:
                fireTeacherContactActivity();
                break;
            default:
                fireChooseTeacherActivity();
                break;
        }

    }

    private void fireChooseTeacherActivity() {
        Intent chooseTeacherIntent = new Intent(FindTeacherActivity.this,
                ChooseTeacherActivity.class);
        chooseTeacherIntent.putParcelableArrayListExtra("TEACHERS", (ArrayList) this.teacherList);
        startActivity(chooseTeacherIntent);
    }

    private void fireTeacherContactActivity() {
        Intent teacherContentIntent = new Intent(FindTeacherActivity.this,
                TeacherContactActivity.class);
        teacherContentIntent.putExtra("TEACHER", this.teacherList.get(0));
        startActivity(teacherContentIntent);
    }

    private void instantiateViews() {
        this.firstNameSearchView = findViewById(R.id.firstNameSearch);
        this.lastNameSearchView = findViewById(R.id.lastNameSearch);
        this.likeRadio = findViewById(R.id.likeRadio);
        this.infoToast = new Toast(this);
    }

    public void onSearch(View view) {
        String firstNameQuery = this.firstNameSearchView.getQuery().toString().toLowerCase();
        Log.d(TAG, "firstNameQuery: " + firstNameQuery);
        String lastNameQuery = this.lastNameSearchView.getQuery().toString().toLowerCase();
        Log.d(TAG, "lastNameQuery: " + lastNameQuery);
        validateSearch(firstNameQuery, lastNameQuery);
    }


    private void executeQuery(final String firstName, final String lastName) {
        Log.d(TAG, "QUERY_INVOKED");
        this.teacherList.clear();
        teacherQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    filterResults(firstName, lastName, ds);
                }
                fireActivity();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MAIN ACTIVITY", "Failed to read value.", error.toException());
            }
        });

    }

    private void filterResults(String firstName, String lastName, DataSnapshot ds) {
        Teacher teacher = ds.getValue(Teacher.class);
        String teachersFirst = teacher.getFirst_name().toLowerCase();
        String teachersLast = teacher.getLast_name().toLowerCase();
        boolean firstEmpty = firstName.isEmpty();
        boolean lastEmpty = lastName.isEmpty();
        if (likeRadio.isChecked()) {
            boolean firstStart = firstEmpty ? false : teachersFirst.startsWith(firstName);
            boolean lastStart = lastEmpty ? false : teachersLast.startsWith(lastName);
            if (lastEmpty && firstStart)
                teacherList.add(teacher);
            else if (firstEmpty && lastStart) {
                teacherList.add(teacher);
            } else {
                if (firstStart && lastStart)
                    teacherList.add(teacher);
            }
        } else {
            boolean firstEqual = teachersFirst.equals(firstName);
            boolean lastEqual = teachersLast.equals(lastName);
            if (lastEmpty && firstEqual)
                teacherList.add(teacher);
            else if (firstEmpty && lastEqual) {
                teacherList.add(teacher);
            } else {
                if (firstEqual && lastEqual)
                    teacherList.add(teacher);
            }
        }
    }

    private void validateSearch(String firstName, String lastName) {
        if (firstName.isEmpty() && lastName.isEmpty()) {
            this.infoToast = Toast.makeText(this, "Please enter at least one search", Toast.LENGTH_LONG);
            infoToast.show();
        } else {
            executeQuery(firstName, lastName);
        }

    }
}