package android.lovemesomedatacom.classcancelation;

import android.content.res.Configuration;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.business.MenuActivity;
import android.lovemesomedatacom.entities.Course;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * ClassCancellationActivity is host activity for the ClassCancellationsFragment and
 * ClassCancellationDetailFragment. Fragments are displayed based on orientation.
 *
 * @author Rhai
 */
public class ClassCancellationActivity extends MenuActivity implements ClassCancellationsFragment.OnItemSelectedListener {

    private final String TAG = "ClassCancelActivity";
    private ClassCancellationsFragment firstFragment;
    private ClassCancellationDetailFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_cancellation);

        // creates and stores the both fragments
        // on for the list(ClassCancellationsFragment)
        // and the other for details (ClassCancellationDetailFragment)
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        firstFragment = new ClassCancellationsFragment();

        Log.d(TAG, getResources().getConfiguration().orientation + "");

        //Tells how to display the fragments based on orientation
        //if portrait one fragment is displayed at a time
        // landscape both fragments side by side.
        if (savedInstanceState == null) {
            // Instance of first fragment
            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            if(firstFragment.isAdded())
            {
                return; //or return false/true, based on where you are calling from
            }
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        } else {
            // reloads the existing fragments if there is saved instance of them
            if(getSupportFragmentManager().getFragment(savedInstanceState,"classCancelFragment")!= null)
                firstFragment = (ClassCancellationsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"classCancelFragment");


            if(getSupportFragmentManager().getFragment(savedInstanceState,"classCancelDetailFragment") != null)
                secondFragment = (ClassCancellationDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState,"classCancelDetailFragment");

            if(firstFragment.isAdded())
            {
                return; //or return false/true, based on where you are calling from
            }
            Log.d(TAG,"swapping fragments");
            ft.replace(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            secondFragment = new ClassCancellationDetailFragment();
            Bundle args = new Bundle();
            //args.putInt("position", 0);
            secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            if(secondFragment.isAdded())
            {
                return; //or return false/true, based on where you are calling from
            }
            ft2.add(R.id.flContainer2, secondFragment);                               // add    Fragment
            ft2.commit();                                                            // commit FragmentTransaction
        }
    }

    /**
     * onClassCancellationSelected displays the details of a selected course
     * in the ClassCancellatiosFragment. Details are displayed depending on
     * orientation.
     * @param courseSelected selected course
     */
    @Override
    public void onClassCancellationSelected(Course courseSelected) {

        // Load ClassCancellationDetail Fragment
        secondFragment = new ClassCancellationDetailFragment();

        Bundle args = new Bundle();
        args.putString("course_name", courseSelected.getCourseName());
        args.putString("course_description",courseSelected.getDescription());
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, secondFragment) // replace flContainer
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }

    }

    /**
     * Overriden onSaveInstanceState saves the state of the two fragments
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        if(firstFragment !=null)
            getSupportFragmentManager().putFragment(outState, "classCancelFragment",firstFragment);
        if(secondFragment != null)
            getSupportFragmentManager().putFragment(outState,"classCancelDetailFragment",secondFragment);
    }

}
