package android.lovemesomedatacom;

import android.lovemesomedatacom.adapters.TeacherAdapter;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class FindTeacherActivity extends AppCompatActivity {

    private static final String TAG = "FindTeacherActivity";

    //Firebase
    private DatabaseReference mDatabaseRef;
    private TeacherAdapter teacherAdapter;

    //Views
    private SearchView firstNameSearchView;
    private SearchView lastNameSearchView;
    private Button searchButton;
    private RadioGroup radioSearchGroup;
    private RadioButton likeRadio;
    private RadioButton exactRadio;
    private Toast infoToast;
    private Set<Teacher> teacherSet;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_teacher);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        this.teacherSet = new HashSet<>();
        //this.teacherAdapter = new TeacherAdapter(this, R.layout.teacher_list,teacherList mStorageRef);
        instantiateViews();
    }

    private void instantiateViews() {
        this.firstNameSearchView = findViewById(R.id.firstNameSearch);
        this.lastNameSearchView = findViewById(R.id.lastNameSearch);
        this.searchButton = findViewById(R.id.searchTeacherButton);
        this.radioSearchGroup = findViewById(R.id.radioSearchGroup);
        this.likeRadio = findViewById(R.id.likeRadio);
        this.exactRadio = findViewById(R.id.exactRadio);
        this.infoToast = new Toast(this);
    }

    public void onSearch(View view) {
        String firstNameQuery = this.firstNameSearchView.getQuery().toString().toLowerCase();
        Log.d(TAG, "firstNameQuery: " + firstNameQuery);
        String lastNameQuery = this.lastNameSearchView.getQuery().toString().toLowerCase();
        Log.d(TAG, "lastNameQuery: " + lastNameQuery);
        if (validateSearch(firstNameQuery, lastNameQuery)) {
            Log.d(TAG, "VALIDATION PASSED");
            boolean radioButton = likeRadio.isChecked();
            executeQuery(radioButton,firstNameQuery, lastNameQuery);
        }

    }


    private void executeQuery(final boolean radioButton, final String firstName, final String lastName) {
        Log.d(TAG, "QUERY_EXACT INVOKED");
        Query teachers = mDatabaseRef.orderByChild("full_name");
        teachers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Teacher teacher = ds.getValue(Teacher.class);
                    String[] fullName = teacher.getFull_name().split(" ");
                    String teachersFirst = fullName[0].toLowerCase();
                    String teachersLast = fullName[1].toLowerCase();
                    boolean firstNotEmpty = !firstName.isEmpty();
                    boolean lastNotEmpty = !lastName.isEmpty();
                    if (radioButton) {
                        if (teachersFirst.startsWith(firstName) && firstNotEmpty) {
                            Log.d(TAG, "Teacher name: " + teacher.getFull_name());
                            teacherSet.add(teacher);
                        }
                        if (teachersLast.startsWith(lastName) && lastNotEmpty) {
                            Log.d(TAG, "Teacher name: " + teacher.getFull_name());
                            teacherSet.add(teacher);
                        }

                    } else {
                        if (teachersFirst.equalsIgnoreCase(firstName) && firstNotEmpty) {
                            Log.d(TAG, "Teacher name: " + teacher.getFull_name());
                            teacherSet.add(teacher);
                        }
                        if (teachersLast.equalsIgnoreCase(lastName) && lastNotEmpty) {
                            Log.d(TAG, "Teacher name: " + teacher.getFull_name());
                            teacherSet.add(teacher);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MAIN ACTIVITY", "Failed to read value.", error.toException());
            }
        });
    }

    private boolean validateSearch(String firstName, String lastName) {
        if (firstName.isEmpty() && lastName.isEmpty()) {
            this.infoToast = Toast.makeText(this, "Please enter at least one search", Toast.LENGTH_LONG);
            infoToast.show();
            return false;
        }
        return true;

    }
}
