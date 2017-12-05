package android.lovemesomedatacom;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.lovemesomedatacom.db.NotesDBHelper;
import android.lovemesomedatacom.db.NotesTable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NoteDetailsActivity extends MenuActivity {

    private TextView tvNoteDetailsTitle;
    private TextView tvNoteDetailsText;

    private NotesDBHelper mHelper;

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
     * overriden to add + button the toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * custom button in the menu bar and creates a small dialog for the user
     * to enter a note.
     *
     * inserts into the DB and calls updateUI once again
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save_settings:
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

                updateNote();

                break;


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void updateNote(){
        mHelper = new NotesDBHelper(this);
        String note_title = tvNoteDetailsTitle.getText().toString();
        String note_text = tvNoteDetailsText.getText().toString();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String selection = NotesTable.NotesEntry.COL_NOTES_TITLE+ " = ?";

        ContentValues cv = new ContentValues();
        cv.put(NotesTable.NotesEntry.COL_NOTES_TITLE, note_title);
        cv.put(NotesTable.NotesEntry.COL_NOTES_TEXT,note_text);
        db.update(NotesTable.NotesEntry.TABLE, cv, selection, new String[] {String.valueOf(note_title)});

        db.close();
    }

}
