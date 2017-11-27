package android.lovemesomedatacom;

import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ClassCancelationActivity extends AppCompatActivity  implements ClassCancelationsFragment.OnItemSelectedListener {
    
    private final String TAG = "ClassCancelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_cancellation);

        ClassCancelationsFragment firstFragment;
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
            // if this is not done, the menu fragment ended up duplicate
            //  on rotate back to landscape, only when the menu activity was
            //  last active
            ft.replace(R.id.flContainer, firstFragment);                                // add    Fragment
            ft.commit();                                                            // commit FragmentTransaction
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            ClassCancelationsFragment secondFragment = new ClassCancelationsFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft2.add(R.id.flContainer, secondFragment);                               // add    Fragment
            ft2.commit();                                                            // commit FragmentTransaction
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "main Activity onStop ", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClassCancellationSelected(Course courseSelected) {

        // Load Pizza Detail Fragment
        ClassCancelationDetailFragment secondFragment = new ClassCancelationDetailFragment();

        Bundle args = new Bundle();
        args.putString("course_name", courseSelected.getCourseName());
        args.putString("course_description",courseSelected.getDescription().toString());
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, secondFragment) // replace flContainer
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

}
