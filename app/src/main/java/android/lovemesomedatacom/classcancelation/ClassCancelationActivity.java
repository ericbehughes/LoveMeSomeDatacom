package android.lovemesomedatacom.classcancelation;

import android.content.res.Configuration;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.entities.Course;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ClassCancelationActivity extends AppCompatActivity  implements ClassCancelationsFragment.OnItemSelectedListener {

    private final String TAG = "ClassCancelActivity";
    private ClassCancelationsFragment firstFragment;
    private ClassCancelationDetailFragment secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_cancellation);

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        firstFragment = new ClassCancelationsFragment();

        Log.d("DEBUG", getResources().getConfiguration().orientation + "");

        if (savedInstanceState == null) {
            // Instance of first fragment
            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        } else {
            if(getSupportFragmentManager().getFragment(savedInstanceState,"classCancelFragment")!= null)
                firstFragment = (ClassCancelationsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"classCancelFragment");


            if(getSupportFragmentManager().getFragment(savedInstanceState,"classCancelDetailFragment") != null)
                secondFragment = (ClassCancelationDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState,"classCancelDetailFragment");

            // if this is not done, the menu fragment ended up duplicate
            //  on rotate back to landscape, only when the menu activity was
            //  last active
            ft.replace(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            secondFragment = new ClassCancelationDetailFragment();
            Bundle args = new Bundle();
            //args.putInt("position", 0);
            secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft2.add(R.id.flContainer2, secondFragment);                               // add    Fragment
            ft2.commit();                                                            // commit FragmentTransaction
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClassCancellationSelected(Course courseSelected) {

        // Load Pizza Detail Fragment
        secondFragment = new ClassCancelationDetailFragment();

        Bundle args = new Bundle();
        args.putString("course_name", courseSelected.getCourseName());
        args.putString("course_description",courseSelected.getDescription().toString());
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer2, secondFragment) // replace flContainer
                    //.addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        if(firstFragment !=null)
            getSupportFragmentManager().putFragment(outState, "classCancelFragment",firstFragment);
        if(secondFragment != null)
            getSupportFragmentManager().putFragment(outState,"classCancelDetailFragment",secondFragment);
    }

}
