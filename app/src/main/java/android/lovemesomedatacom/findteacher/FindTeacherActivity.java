package android.lovemesomedatacom.findteacher;

import android.content.Intent;
import android.lovemesomedatacom.business.MenuActivity;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
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
 * The FindTeacherActivity is responsible for querying the Firebase database and retrieving
 * a list of teachers according to input taken from the user. It will fire the ChooseTeacherActivity
 * if the query returns more than one teacher, TeacherContactActivity if only one teacher is found
 * or display an appropriate message if no teachers are found.
 *
 * @author Sebastian Ramirez
 */

public class FindTeacherActivity extends MenuActivity {

    private static final String TAG = "FIND_TEACHER_ACTIVITY";

    //Firebase
    private DatabaseReference mDatabaseRef;

    //Views
    private SearchView firstNameSearchView;
    private SearchView lastNameSearchView;
    private String firstNameSearch;
    private String lastNameSearch;
    private RadioButton likeRadio;
    private Toast infoToast;
    private Query teacherQuery;
    private List<Teacher> teacherList;
    private Intent intent;

    /**
     * The onCreate is fired when the Activity is started. It takes a Bundle as parameter in order
     * to preserve data between activities or when the viewport changes. It inflates the specified layout
     * resource and initializes several attributes required by public and private methods that need initialization.
     * onCreate will also initialize all the views that need to be changed through code by invoking
     * the instantiateViews method.
     *
     * @param savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_find_teacher);
        this.setTitle(R.string.find_my_teacher_activity_title);
        this.teacherList = new ArrayList<>();
        //Root reference to the Firebase Database
        this.mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        //Query that will sort the items in the database by full_name, this is done for faster
        //querying since the dataset is indexed by full_name
        this.teacherQuery = mDatabaseRef.orderByChild("full_name");


        intent = getIntent();

        //Instantiation of the views
        instantiateViews();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstance){
        Log.d(TAG, "onRestoreInstanceState called");
        if(savedInstance!=null){
            this.firstNameSearch = savedInstance.getString("FIRST_NAME_SEARCH");
            Log.d(TAG, "FIRST_NAME_FIELD: "+this.firstNameSearch);
            this.lastNameSearch = savedInstance.getString("LAST_NAME_SEARCH");
            Log.d(TAG, "LAST_NAME_FIELD: "+this.lastNameSearch);
            this.firstNameSearchView.setQuery(this.firstNameSearch, false);
            this.lastNameSearchView.setQuery(this.lastNameSearch, false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstance){
        super.onSaveInstanceState(savedInstance);
        Log.d(TAG, "onSaveInstanceState called");
        savedInstance.putString("FIRST_NAME_SEARCH", this.firstNameSearchView.getQuery().toString());
        savedInstance.putString("LAST_NAME_SEARCH", this.lastNameSearchView.getQuery().toString());
    }

    /**
     * The instantiateViews method is a simple method that will instantiate all the views that need
     * to be accessed and/or changed by the activity and its methods.
     */
    private void instantiateViews() {
        this.firstNameSearchView = findViewById(R.id.firstNameSearch);
        this.lastNameSearchView = findViewById(R.id.lastNameSearch);
        this.likeRadio = findViewById(R.id.likeRadio);
        this.infoToast = new Toast(this);
        if(intent.hasExtra("teacher")){
            setViews();
        }
    }

    /**
     * setViews configures the search criteria to look for a single teacher.
     * That teacher's name is received, when the teacher's name in the cancellation
     * list is tapped.
     */

    private void setViews(){
        String teacher_name = intent.getStringExtra("teacher");
        Log.d(TAG, "Teacher name from intent: "+teacher_name);
        int space = teacher_name.trim().indexOf(" ");
        String firstname = teacher_name.substring(0,space);
        String lastname = teacher_name.substring(space+1);
        this.firstNameSearchView.setIconified(false);
        this.lastNameSearchView.setIconified(false);
        this.firstNameSearchView.setQuery(firstname,false);
        this.lastNameSearchView.setQuery(lastname,false);
        ((RadioButton)findViewById(R.id.exactRadio)).setChecked(true);
    }

    /**
     * The fireActivity method will check for the number of items retrieved and stored by the
     * EventListener. If no items are found, it will display a Toast informing the user that no
     * items were found. If exactly 1 item was found, it will fire the TeacherContactActivity.
     * If more than 1 items are found, it will fire the ChooseTeacherActivity.
     */
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

    /**
     * The fireChooseTeacherActivity method takes care of the initialization of the intent that will fire
     * the ChooseTeacherActivity. It will pass a list of Teacher objects that implement the
     * Parcelable interface as an extra.
     */
    private void fireChooseTeacherActivity() {
        Intent chooseTeacherIntent = new Intent(FindTeacherActivity.this,
                ChooseTeacherActivity.class);
        chooseTeacherIntent.putParcelableArrayListExtra("TEACHERS", (ArrayList) this.teacherList);
        startActivity(chooseTeacherIntent);
    }

    /**
     * The fireEmailFriendWhoIsFree method takes care of the initialization of the intent that will fire
     * the TeacherContactActivity. It will pass a single Teacher object that implement the
     * Parcelable interface as an extra.
     */
    private void fireTeacherContactActivity() {
        Intent teacherContentIntent = new Intent(FindTeacherActivity.this,
                TeacherContactActivity.class);
        teacherContentIntent.putExtra("TEACHER", this.teacherList.get(0));
        startActivity(teacherContentIntent);
    }


    /**
     * The onSearch method is fired whenever the user presses the search button on this activity.
     * It gets the text from both of the SearchViews and passes it to the validateSearch method.
     * It takes as parameter the view that invoked the method.
     *
     * @param view
     */
    public void onSearch(View view) {
        this.firstNameSearch = this.firstNameSearchView.getQuery().toString().toLowerCase();
        Log.d(TAG, "firstNameQuery: " + this.firstNameSearch);
        this.lastNameSearch = this.lastNameSearchView.getQuery().toString().toLowerCase();
        Log.d(TAG, "lastNameQuery: " + this.lastNameSearch);
        validateSearch(this.firstNameSearch, this.lastNameSearch);
    }

    /**
     * The validateSearch method takes as input the text from the SearchViews and validates that
     * at checks if both of them are empty or not. If both are empty, it will display a Toast
     * asking the user to enter at least one search inquiry. If at least one is not empty, it will
     * call the executeQuery method.
     *
     * @param firstName
     * @param lastName
     */
    private void validateSearch(String firstName, String lastName) {
        if (firstName.isEmpty() && lastName.isEmpty()) {
            this.infoToast = Toast.makeText(this, "Please enter at least one search", Toast.LENGTH_LONG);
            infoToast.show();
        } else {
            executeQuery(firstName, lastName);
        }

    }

    /**
     * The executeQuery method will add a ValueEventListener to the Query. Before performing a search
     * on the Query, it will delete all previous items in the Teacher's list in order to not display
     * past items not particular to the new search. The ValueEventListener will search through the
     * Query and retrieve every element as a DataSnapshot and filter the results by calling the
     * filterResults method for every item.
     *
     * @param firstName
     * @param lastName
     */
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

    /**
     * The filterResults method will take as paramenter the input specified by the user, as well
     * as a DataSnapshot object containing a single item from the Query. It converts the DataSnapshot
     * object to a Teacher object, made possible by an appropriate written Teacher object. It converts
     * the teacher's firstName and lastName to lower case for easier filtering of results. If the
     * activity's RadioButton that specifies searching for similar results is checked, it will filter
     * the results appropiately, checking if the teacher's first and last name start with what the user
     * specified. If the activity's RadioButton is not checked, it means the search for exact results
     * RadioButton is checked, the method will check if the teacher's first and last name match exactly
     * the user input. When the teacher's name matches one of the conditions, it is added to the
     * teacher's list.
     *
     * @param firstName
     * @param lastName
     * @param ds
     */
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

    public void showTeacherInfo(String firstName, String lastName){
        this.likeRadio = findViewById(R.id.likeRadio);
        this.likeRadio.setChecked(false);
        executeQuery(firstName,lastName);
    }
}


