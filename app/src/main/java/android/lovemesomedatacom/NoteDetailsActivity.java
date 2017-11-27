package android.lovemesomedatacom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;

public class NoteDetailsActivity extends AppCompatActivity {

    private TextView tvNoteDetailsTitle;
    private TextView tvNoteDetailsText;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-11-24 19:05:17 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvNoteDetailsTitle = (TextView)findViewById( R.id.etNoteDetailsTitle );
        tvNoteDetailsText = (TextView)findViewById( R.id.etNoteDetailsText );
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        findViews();

        Intent intent = getIntent();

        tvNoteDetailsTitle.setText(intent.getStringExtra("noteTitle"));
        tvNoteDetailsText.setText(intent.getStringExtra("noteText"));



    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("NoteDetails", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        super.onStop();
    }
}
