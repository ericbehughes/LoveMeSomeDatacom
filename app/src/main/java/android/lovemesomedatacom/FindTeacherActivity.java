package android.lovemesomedatacom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class FindTeacherActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        //Reference to the storage
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //Reference to the auth dependency
        mAuth = FirebaseAuth.getInstance();
        //Initial and only authentication of the app, used to access the database
        mAuth.signInWithEmailAndPassword("sramirezdawson2017@gmail.com", "catsLoveFood");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("categories");

        //myAdapter = new CustomAdapter(this, R.layout.list_layout, imgList, mStorageRef);
        mDatabaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Category img = ds.getValue(Category.class);
                    categories.add(img);
                    myAdapter.addElement(img);
                }

                lv.setAdapter(myAdapter);
                lv.setOnItemClickListener(showCategoryContents);
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                // Failed to read value
                Log.w("MAIN ACTIVITY", "Failed to read value.", error.toException());
            }
        });
    }
}
